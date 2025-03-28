package de.hso.cardgame.gamecentral.logic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import de.hso.cardgame.model.GameCommand;
import de.hso.cardgame.model.GameCommand.DealHands;
import de.hso.cardgame.model.GameCommand.PlayCard;
import de.hso.cardgame.model.GameCommand.RegisterPlayer;

/**
 * This class implements methods to serialize and deserialize {@link GameCommand}s to and from JSON.
 * There is a test in {@code test/de/hso/cardgame/model/GameCommandJSONSerializationTest.java}
 * that checks if the serialization and deserialization are inverse to each other.
 */
public class GameCommandSerializer {

    public static String toJSON(GameCommand cmd) {
         throw new RuntimeException("not implemented");
    }

    /**
     * Deserializes a JSON string into a {@link GameCommand} object.
     *
     * Throws the unchecked exception {@link JsonSyntaxException} if the JSON string
     * is invalid.
     */
    public static GameCommand fromJSON(String json) {
        throw new RuntimeException("not implemented");

    }
}
