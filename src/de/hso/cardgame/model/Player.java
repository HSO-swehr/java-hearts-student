package de.hso.cardgame.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * An enum abstractly representing the players in the game. The enum does not associate
 * players with names or other attributes. This has to be tracked by the game logic.
 * (Players send the command {@link GameCommand.RegisterPlayer} with their name to the game logic, the game
 * logic later sends out the {@link GameEvent.PlayerRegistered} events for recording the association between
 * players and their names.)
 */
public enum Player implements JSONSerialization {
    P1, P2, P3, P4;

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
    
    public static Player fromJSON(JsonElement json) {
        throw new RuntimeException("not implemented");
    }
}
