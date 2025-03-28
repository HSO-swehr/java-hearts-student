package de.hso.cardgame.clients;

import java.util.Scanner;

import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Maps;

import de.hso.cardgame.gamecentral.logic.GameLogic;
import de.hso.cardgame.model.Card;
import de.hso.cardgame.model.GameCommand;
import de.hso.cardgame.model.GameEvent.CardPlayed;
import de.hso.cardgame.model.GameEvent.HandsDealt;
import de.hso.cardgame.model.GameEvent.PlayerRegistered;
import de.hso.cardgame.model.GameEvent.PlayerTurn;
import de.hso.cardgame.model.GameEvent.TrickTaken;
import de.hso.cardgame.model.Player;

/**
 * Implementation of a game of hearts with four players.
 *
 * - The game logic and all four players are running inside the same program.
 * - All players interact via the commandline.
 *
 * This is not a realistic setting for playing the game. But it allows playing around
 * with the game logic.
 *
 * The game expects the names of the four players as command line arguments. The special
 * player name "AI" is reserved for the AI player.
 *
 * With gradle, you can run the game like this:
 *
 *
 */
public class FullGame implements GameEventProcessor {

    private Map<Player, String> names;
    private Map<String, PlayerBehavior> players;
    private Scanner in;
    private GameLogic logic;
    private GameEventAdapter eventAdapter;
    private Player nextPlayer;
    private boolean gameOver = false;

    public static void main(String[] args) throws Exception {
        boolean usage = false;
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                usage = true;
            }
        }
        if (args.length != 4 || usage) {
            System.out.println("Usage: ./gradlew runLocalGame --console=plain --args=\"<player1> <player2> <player3> <player4>\"");
            System.out.println("Use the special name \"AI\" for the AI player.");
            if (!usage) {
                System.out.println("Got " + args.length + " arguments, expected 4.");
            }
            System.exit(1);
        }
        FullGame main = new FullGame();
        main.run(args);
    }

    void run(String[] playerNames) {
        this.in = new Scanner(System.in);
        this.eventAdapter = new GameEventAdapter(this);
        this.logic = new GameLogic(this.eventAdapter, true);
        System.out.println("Welcome to a round of hearts!");
        System.out.println("All players and the game logic is running inside the same application");
        // First, register the players
        this.players = Maps.of();
        for (int i = 0; i < Player.values().length; i++) {
            String name = playerNames[i];
            PlayerBehavior pb = null;
            if (name.equals("AI")) {
                pb = new PlayerStupidAI();
            } else {
                pb = new PlayerConsole(name, in);
            }
            name = pb.getName(); // returns a different name for AI players
            if (this.players.containsKey(name)) {
                abort("Duplicate player name: " + name);
            }
            this.players = this.players.put(name, pb);
        }
        for (String name : this.players.keys()) {
            this.logic.processCommand(new GameCommand.RegisterPlayer(name));
        }
        // After we have registered the fourth player, the logic automatically deals the cards
        // and selects the player which should play first.
        // We do some santiy checks
        if (this.nextPlayer == null) {
            abort("Did not receive PlayerTurn event after all four players have registered");
        }
        if (this.names == null) {
            abort("Did not receive PlayerRegistered event after all four players have registered");
        }
        if (this.names.size() != 4) {
            abort("Did not receive PlayerRegistered event for all four players");
        }
        // The we play
        while (!this.gameOver) {
            var currentPlayer = nextPlayer;
            var card = getCardForPlayer(nextPlayer);
            this.logic.processCommand(new GameCommand.PlayCard(currentPlayer, card));
        }
    }

    private PlayerBehavior getPlayerBehavior(Player p) {
        return this.players.get(this.names.get(p));
    }

    private Card getCardForPlayer(Player p) {
        return getPlayerBehavior(p).playCard();
    }

    private void print(Player p, String message) {
        System.out.println("[" + this.names.get(p) + "] " + message);
    }

    private void abort(String message) {
        System.out.println("ERROR: " + message);
        System.out.println("Aborting");
        System.exit(1);
    }

    @Override
    public void updatePlayers(PlayerRegistered pr) {
        if (this.names == null) {
            this.names = Maps.of();
        }
        this.names = names.put(pr.player(), pr.name());
        print(pr.player(), "joined the game under name " + pr.name());
    }

    @Override
    public void setCards(HandsDealt hd) {
        getPlayerBehavior(hd.player()).startGame(hd.player(), hd.hand());
    }

    @Override
    public void updatePlayerTurn(PlayerTurn pt) {
        nextPlayer = pt.player();
    }

    @Override
    public void updateCardPlayed(CardPlayed cp) {
        for (PlayerBehavior pb : this.players.values()) {
            pb.cardPlayed(cp.player(), cp.card());
        }
        print(cp.player(), "played " + cp.card().toString());
    }

    @Override
    public void onTrickTaken(TrickTaken tt) {
        for (PlayerBehavior pb : this.players.values()) {
            pb.trickTaken(tt.player());
        }
        print(tt.player(), "takes the trick.");
    }

    @Override
    public void endOfGame(String msg) {
        this.gameOver = true;
        System.out.println("End of game: " + msg);
        System.exit(0);
    }
}
