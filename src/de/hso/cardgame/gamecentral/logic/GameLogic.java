package de.hso.cardgame.gamecentral.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Maps;
import com.github.andrewoma.dexx.collection.Pair;
import com.github.andrewoma.dexx.collection.Sets;

import de.hso.cardgame.model.*;
import de.hso.cardgame.model.GameCommand.DealHands;
import de.hso.cardgame.model.GameCommand.PlayCard;
import de.hso.cardgame.model.GameCommand.RegisterPlayer;
import de.hso.cardgame.util.Logging;

/**
 * The GameLogic class implements the core logic of the game.
 * It accepts {@link GameCommand}s via its {@code processCommand} method and produces {@link GameEvent}s as a result.
 * The GameEvents produced are then passed to the {@link EventConsumer} that the GameLogic gets
 * in the constructor. The GameLogic class is stateful and maintains the current state of the game.
 *
 * The most important method of GameLogic class is the processCommand method.
 */
public class GameLogic {


    /**
     * Create a new GameLogic instance.  Events produced are passed to the {@link EventConsumer}.
     * The parameter {@code autoDealHands} determines if hands should be dealt automatically
     * when the fourth player registers. In production mode, this should be set to true. For tests,
     * it can be set to false to specify the cards on the hands of each player automatically.
     */
    public GameLogic(EventConsumer eventConsumer, boolean autoDealHands) {
            throw new RuntimeException("not implemented");
    }

    /**
     * For a given {@link Trick}, return the {@link Player} that takes the trick.
     * Exposed for testing purpose.
     */
    public static Player whoTakesTrick(Trick trick) {
             throw new RuntimeException("not implemented");
    }

    /**
     * Checks if player {@code p} can play card {@code c} in the current game state {@code state}.
     * Exposed for testing purpose.
     */
    public static boolean isPlayValid(Player p, Card c, GameState state) {
             throw new RuntimeException("not implemented");
    }

    /**
     * Process a {@link GameCommand}.  The GameLogic will produce {@link GameEvent}s as a result.
     */
    public void processCommand(GameCommand cmd) {
             throw new RuntimeException("not implemented");
    }



}
