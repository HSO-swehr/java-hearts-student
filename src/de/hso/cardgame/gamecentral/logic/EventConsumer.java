package de.hso.cardgame.gamecentral.logic;

import de.hso.cardgame.model.GameEvent;

/**
 * Interface for a component that receives game events.
 * Typically, the {@link de.hso.cardgame.gamecentral.logic.GameLogic} uses this interface
 * to abstract over possible event consumers.
 * The {@code GameLogic} calls {@link #consumeEvent(GameEvent)} whenever it wants to publish an event.
 */
public interface EventConsumer {
    /**
     * Consumes a game event sent by the game logic.
     *
     * @param event the {@link GameEvent} to be consumed
     */
    void consumeEvent(GameEvent event);
}
