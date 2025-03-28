package de.hso.cardgame.gamecentral.logic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import de.hso.cardgame.model.GameEvent;
import de.hso.cardgame.model.GameEvent.CardPlayed;
import de.hso.cardgame.model.GameEvent.GameError;
import de.hso.cardgame.model.GameEvent.GameOver;
import de.hso.cardgame.model.GameEvent.HandsDealt;
import de.hso.cardgame.model.GameEvent.PlayerError;
import de.hso.cardgame.model.GameEvent.PlayerRegistered;
import de.hso.cardgame.model.GameEvent.PlayerTurn;
import de.hso.cardgame.model.GameEvent.TrickTaken;

/**
 * This class implements methods to serialize and deserialize {@link GameEvent}s to and from JSON.
 * There is a test in {@code test/de/hso/cardgame/model/GameEventJSONSerializationTest.java}
 * that checks if the serialization and deserialization are inverse to each other.
 */
public class GameEventSerializer {

    public static String toJSON(GameEvent event) {
        throw new RuntimeException("not implemented");
    }


    /**
     * Deserializes a JSON string into a {@link GameEvent} object.
     *
     * Throws the unchecked exception {@link JsonSyntaxException} if the JSON string
     * is invalid.
     */
    public static GameEvent fromJSON(String json) {
        throw new RuntimeException("not implemented");
    }
}
