package de.hso.cardgame.model;

import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Maps;
import com.github.andrewoma.dexx.collection.Pair;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A representation of the final score of a game. The score for each {@link Player} is stored in a map.
 */
public record Score(Map<Player, Integer> score) implements JSONSerialization {

    public Score {
        if (score == null) {
            throw new NullPointerException("score must not be null.");
        }
    }

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
}
