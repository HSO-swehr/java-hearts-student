#!/usr/bin/env python3

# Synchronizes files to the student repo.
# - Get all files under git control.
# - All such non java files are copied to the student repo.
# - For a java file, proceed as follows:
#   * Remove all lines between `// __STUD_DEL_START`` and `// __STUD_DEL_END`.
#   * Re-add lines starting with `// __STUD: ` and remove the prefix.
#   * If no such lines are found, exclude the file unless there is a `// __STUD_SYNC` line.

from shell import *
import argparse
import shutil
import re

TOP_DIR = abspath(pjoin(THIS_DIR, ".."))
cd(TOP_DIR)

STUD_REPO = pjoin(TOP_DIR, "../java-hearts-student")

_debug = False
def debug(s: str):
    if _debug:
        print(f'[DEBUG] {s}')

def fixJavaCode(s: str, path: str='<input>') -> Optional[str]:
    prefix = '// __STUD: '
    r = re.compile(r'(\s*)' + re.escape(prefix))
    lines = []
    foundStudMarker = False
    inside = False
    for line in s.split('\n'):
        if line.strip().startswith('// __STUD_DEL_START'):
            foundStudMarker = True
            if inside:
                raise ValueError(f"Found nested __STUD_DEL_START in {path}")
            inside = True
            continue
        elif line.strip().startswith('// __STUD_DEL_END'):
            foundStudMarker = True
            if not inside:
                raise ValueError(f"Found __STUD_DEL_END in {path} without start marker")
            inside = False
            continue
        elif line.strip().startswith('// __STUD_SYNC'):
            foundStudMarker = True
        elif not inside:
            m = r.match(line)
            if m:
                line = m.group(1) + line[len(m.group(0)):].rstrip()
            lines.append(line.rstrip())
    if inside:
        raise ValueError(f"Missing __STUD_DEL_END in {path}")
    if foundStudMarker:
        return '\n'.join(lines)
    else:
        return None

def test_fixJavaCode():
    s1 = """
class C {
    // __STUD_DEL_START
    private int x;
    public int getX() {
        return x;
    }
    // __STUD_DEL_END
    // __STUD: public int getX() { return 0 }
}
"""
    expected1 = """
class C {
    public int getX() { return 0 }
}
"""
    assert fixJavaCode(s1) == expected1
    s2 = """
class C {
}
"""
    assert fixJavaCode(s2) == None
    assert fixJavaCode('// __STUD_SYNC\n' + s2) == s2

def include(path: str) -> bool | str:
    if not path.endswith('.java'):
        return True
    with open(path, 'r') as f:
        s = fixJavaCode(f.read(), path)
        if s is None:
            return False
        else:
            return s

def copy(f: str, tmpDir: str):
    if not exists(f):
        abort(f'File/directory {f} does not exist. Did it move but was not committed?')
    if not isFile(f):
        return
    target = pjoin(tmpDir, f)
    d = dirname(target)
    mkdirs(d)
    shutil.copy(f, target)

def writeCode(s: str, f: str, tmpDir: str):
    target = pjoin(tmpDir, f)
    d = dirname(target)
    mkdirs(d)
    open(target, 'w').write(s)

def main():
    files = run('git ls-files', captureStdout=splitLines).stdout
    inc: list[str] = []
    ex: list[str] = []
    with tempDir(suffix='hearts-java') as tmpDir:
        for f in files:
            x = include(f)
            if x is False:
                ex.append(f)
            elif x is True:
                debug(f'Copying {f} to {tmpDir}')
                copy(f, tmpDir)
                inc.append(f)
            else:
                debug(f'Patching {f} to {tmpDir}')
                writeCode(x, f, tmpDir)
                inc.append(f)
        print(f"Copied/patched {len(inc)} items, excluded {len(ex)}")
        print(f"Exluded items:")
        for e in ex:
            print(e)
        rsyncExcludes = ['/.git']
        rsyncExOpts = ' '.join([f'--exclude {x}' for x in rsyncExcludes])
        print()
        print(f'Syncing to {STUD_REPO} via rsync ...')
        res = run(f'rsync --checksum --archive --verbose --delete {rsyncExOpts} {tmpDir}/ {STUD_REPO}',
                  captureStdout=splitLines)
        for l in res.stdout:
            l = l.rstrip()
            if not isDir(l):
                print(l)

def parseArgs():
    parser = argparse.ArgumentParser(description=f'Sync student repo')
    parser.add_argument('--debug', action='store_true', default=False,
                        help='Enable debugging output')
    parser.add_argument('--patch-file', metavar='FILE', type=str,
                        help='Process a single file and print the result to stdout')
    return parser.parse_args()

if __name__ == '__main__':
    args = parseArgs()
    _debug = args.debug
    if args.patch_file:
        s = open(args.patch_file, 'r').read()
        s = fixJavaCode(s, args.patch_file)
        if s is None:
            print(f'File {args.patch_file} will not be synced to the student repo')
        else:
            print(s)
    else:
        main()

