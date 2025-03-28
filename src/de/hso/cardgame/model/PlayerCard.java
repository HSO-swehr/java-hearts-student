package de.hso.cardgame.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The representation of a card played by some player.
 */
public record PlayerCard(Player player, Card card) implements JSONSerialization {

    public PlayerCard {
        if (player == null) {
            throw new NullPointerException("player must not be null.");
        }
        if (card == null) {
            throw new NullPointerException("card must not be null.");
        }
    }

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
}
