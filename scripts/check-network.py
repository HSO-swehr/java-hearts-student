import subprocess
import random
import time

def waitForAll(procs, timeout: float, pollInterval=0.1):
    exitCodes = [None] * len(procs)
    start = time.time()
    while True:
        for i, proc in enumerate(procs):
            r = proc.poll()
            if r is not None:
                print(f'Process {i} completed with exit code {r}')
                exitCodes[i] = r
                if r != 0:
                    for proc in procs:
                        proc.kill()
                    return exitCodes
        if all([r is not None for r in exitCodes]):
            return exitCodes
        now = time.time()
        if now - start > timeout:
            for proc in procs:
                proc.kill()
            return exitCodes
        time.sleep(pollInterval)

def runFullNetworkGame():
    port = random.randint(20000, 30000)
    server = subprocess.Popen(['./gradlew', 'run', '--console=plain', f'--args={port}'])
    print('Server started')
    procs = [server]
    time.sleep(2)
    print('Starting clients')
    for i in range(4):
        f = open(f'/tmp/client_{i}.out', 'wb', buffering=0)
        p = subprocess.Popen(['./gradlew', 'runClient', '--console=plain', f'--args=localhost {port} AI'],
                                stdout=f)
        print(f'Client {i} started')
        procs.append(p)
    results = waitForAll(procs, 60)
    print(f'results={results}')
    assert all([r == 0 for r in results]), "Some processes failed"
    print("All processes completed successfully")

runFullNetworkGame()
