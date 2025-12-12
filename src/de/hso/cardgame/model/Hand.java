package de.hso.cardgame.model;

import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The set of cards a player holds in their hand.
 */
public record Hand(Set<Card> cards) implements JSONSerialization {

    public Hand {
        if (cards == null) {
            throw new NullPointerException("cards must not be null.");
        }
    }

    public boolean hasCard(Card c) {
        return this.cards.contains(c);
    }

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
    
    public static Hand fromJSON(JsonElement json) {
        throw new RuntimeException("not implemented");
    }
}
