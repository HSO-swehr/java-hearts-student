package de.hso.cardgame.gamecentral.logic;

import java.util.Optional;
import java.util.logging.Logger;

import com.github.andrewoma.dexx.collection.*;

import de.hso.cardgame.model.*;
import de.hso.cardgame.util.Logging;

/**
 * Represents the state of the game. The game state is transformed by applying events (method
 * {@code applyEvent}). This class is "dumb," meaning transformations are made without checking if
 * the next state is valid. These checks are done in {@link GameLogic}.
 *
 * Note that the GameState is immutable. This means that every modification returns a new
 * {@code GameState} object.
 *
 * Conceptually, the GameState class only exposes the applyEvent method to the outside world.
 * The methods @{link validate} and @{link isValid} are only exposed for performing sanity checks.
 */
public record GameState(
        Map<Player, String> playerNames,
        Map<Player, Hand> playerHands,
        Map<Player, Stack> playerStacks,
        Trick currentTrick,
        Optional<Player> nextPlayer
    ) {

    public static final GameState empty =
            new GameState(Maps.of(), Maps.of(), Maps.of(), Trick.empty, Optional.empty());

    /**
     * Performs some sanity checks on the game state. If the state is invalid, an
     * {@link IllegalStateException} should be thrown. This method is used by
     * {@code test/de/hso/cardgame/gamecentral/server/GameLogicTest.java} to perform tests.
     */
    public void validate() {
        if (!isValid()) {
            Logger log = Logging.getLogger(getClass().getName());
            var msg = "Invalid GameState: " + this;
            log.warning(msg);
            throw new IllegalStateException(msg);
        }
    }


    /**
     * Applies a {@link GameEvent} to the game state. The method returns a new GameState object
     * representing the state after the event has been applied. The method does not check if the
     * event is valid in the current state. This is done in {@link GameLogic}.
     */
    public GameState applyEvent(GameEvent e) {
            throw new RuntimeException("not implemented");
    }

    /**
     * Returns {@code false} if the game state is invalid. Tries to implement as many checks
     * as possible.
     */
    public boolean isValid() {
            throw new RuntimeException("not implemented");
    }

}
