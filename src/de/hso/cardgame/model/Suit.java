package de.hso.cardgame.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * The suit (color of a card).
 */
public enum Suit implements JSONSerialization {
    Diamonds, Hearts, Spades, Clubs;

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }

    public static Suit fromJSON(JsonElement json) {
        throw new RuntimeException("not implemented");
    }
}
