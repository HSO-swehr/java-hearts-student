# Cardgame in Java

This repository provides the implementation of a simple card game in Java. The
card game supports multiple players, potentially distributed over the network.

*Note:* in case you are working as a student on this project, the repository
does not provide the whole implementation but only parts of it. The comments in
this README still apply. You can add your own code and you might also change existing code.
However, if you find yourself performing large modifications to the existing code,
you might be working against the intended architecture. Better consult the instructor
in such a situation.

## Architecture

The implementation consists of three main parts:

* [Model](src/de/hso/cardgame/model), package `de.hso.cardgame.model`: here you find the
  definition of all classes and records relevant for modelling a card game: cards,
  players, trick, score etc.
* [Central](src/de/hso/cardgame/gamecentral), package `de.hso.cardgame.gamecentral`: here
  you find the central logic for the game. For example, the central logic distributes
  the cards to the players, receives cards from players, checks if such a card is valid
  and so on.
* [Clients](src/de/hso/cardgame/clients/), package `de.hso.cardgame.clients`: here
  you find implementations of various clients. Such a client might be a person sitting
  in front of a computer playing through some user interface, or it might be
  a computer program playing automatically.

Model and clients communicate via **events** and **commands**.

- **Events** are defined in [src/de/hso/cardgame/model/GameEvent.java](). The central game logic
  uses events to notify clients that something has happened during the game. Examples for events
  are: a player played some card, someone has taken a trick, the game is over.
- **Commands** are defined in [src/de/hso/cardgame/model/GameCommand.java](). Clients
  use commands to express a wish that something should happen. Examples for commands are:
  some player wants to start the game, some player wants to play some card.

An example would be the desire to play a card. The player sends this command to the server (including the card they want to play). The server checks whether it is a valid move, updates its model, and then sends the domain events to the other players stating that card X was played by player Y.

The client should hold as little game logic as possible and only the most necessary information. The server should keep track of the game-state and logic.

## Setup

You need the following software installed on your computer:

* Eclipse with the extension "Buildship Gradle Integration" (can be found via "Help" -> "Eclipse Market Place")
* JDK, at least version 21

Import the project in Eclipse with "File" -> "Import" -> "Gradle" -> "Existing Gradle Project" and then choose the java-hearts-student directory. This might take a moment.

## Useful commands

The project uses [gradle](https://gradle.org/) for building, running, and testing the software.
The wrapper script `gradlew` (or `gradlew.bat` on windows) starts gradle, no installation
is necessary. Here are some useful gradle commands:

* Compile code: `./gradlew build`
  * Note that running the tests or running some program automatically does a build.
* Run all tests: `./gradlew test`
  * To re-run the tests even if the code did not change use the options `--rerun-tasks -x compileJava -x compileTestJava`
  * To run only certain tests, use the option `--tests PATTERN`, where `PATTERN` is a pattern matching the name of the tests that should run. Patterns might include wildcards `*`.
* Build javadocs: `./gradlew javadoc`, you then find the documentation at `build/docs/javadoc/index.html`
* Run programs:
  * Local game: `./gradlew runLocalGame --console=plain --args="<player1> <player2> <player3> <player4>"`
    * Replace `<playerN>` with the name of a player. The special name `"AI"` uses a rather stupid
      AI player. (Students have to implement this player first!)
    * All four client run in the same application
    * Server als runs in the application
    * Does not require a network
    * Use this program for play around once you have implemented the game logic. You also
      should write unit tests for the game logic because, as you will notice, playing around
      manually is very tedious.
  * Server: `./gradlew run --console=plain`
    * Runs the server on part 17283.
    * Use `./gradlew run --console=plain --args=<port>` to specify a custom port
    * Requires the main class `de.hso.cardgame.gamecentral.server.Server` to be written by students in newly created file.
  * Network client: `./gradlew runClient --console=plain --args="<server> <playerName>"`
    * Runs a client that connects over the network with `<server>`. In the local network, set
      `<server>` to `localhost`.
    * The player name `"AI"` uses a rather stupid AI player.
      (Students have to implement this player first!)
    * Uses by default port 17283.
    * Use `./gradlew runClient --console=plain --args="<server> <port> <playerName>"` to specify
      a custom port.
    * Requires the main class `de.hso.cardgame.clients.NetClient` to be written by students in newly created file.

## Next steps

If you are a student working on completing this project, I suggest the following steps.

1. Implement the game logic. Tip: Complete the implementation of GameLogic and GameState classes.
   Test with `./gradlew test --tests *GameLogic*`
1. Play by hand using `./gradlew runLocalGame --console=plain --args="Albert Beate Clara Donald"`
1. Complete the implementation of the `PlayerStupidAI` class.
1. Play with AI using `./gradlew runLocalGame --console=plain --args="AI AI AI AI"`
1. Implement JSON serialization. Test with `./gradlew test --tests *JSONSerialization*`
1. Implement the network logic.
1. Start the server and four clients. You can either play the clients by hand or you can use the AI player. See above for how to start the programs.

## Tips for network logic implementation

If you are struggling to get started with the netwrok logic consider the following tips:

1. Playing via network requires 1 console to run the server and four consoles to run the clients (players) using the corresponding gradlew commands
1. The gradlew command runLocalClient, which has been used up to this point, used to start the main() of FullGame. This function will not be started when playing via network. So, the logic contained in FullGame needs to be transferred to Server and NetClient.
1. The server should hold most of the logic, including GameLogic itself. To create a GameLogic object the constructor needs an object implementing EvenConsumer. This may be Server itself, using "this" in the GameLogic constructor.
1. The NetClient needs to know whether it is supposed to be a human or AI player and implement some of the PlayerBehavior logic from FullGame (think about what happens when a GameEvent arrives)
1. Server waits for four NetClients to connect, then starts a Thread for each one to listen to incoming messages.
1. Server sends GameEvents and recieves GameCommands, NetClient works the other way around. Send GameEvents and GameCommands via sockets using JSON data format.
1. Some things might need to be synchronized between Threads.
