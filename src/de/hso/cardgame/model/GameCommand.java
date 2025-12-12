package de.hso.cardgame.model;

import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A {@link GameCommand} represents a wish that something should happen in the game. The clients
 * of the game produce such commands, which are then processed by the game logic.
 *
 * The GameCommand interface is sealed, meaning that all implementing classes are known
 * at compile time. This allows the game logic to use a pattern matching switch statement
 * to process the commands.
 */
public sealed interface GameCommand extends JSONSerialization
    permits GameCommand.DealHands, GameCommand.PlayCard, GameCommand.RegisterPlayer
{

    /**
     * Wish expressed by a player: I would like to be part of the game.
     */
    public static record RegisterPlayer(String name) implements GameCommand {
        public RegisterPlayer {
            if (name == null) {
                throw new NullPointerException("name must not be null.");
            }
        }

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static RegisterPlayer fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Wish expressed by the game logic itself: I would like to get my cards for the next game.
     */
    public static record DealHands(Map<Player, Hand> hands) implements GameCommand {
        public DealHands {
            if (hands == null) {
                throw new NullPointerException("hands must not be null.");
            }
        }

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static DealHands fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Wish expressed by a player: I would like to play the given card.
     */
    public static record PlayCard(Player player, Card card) implements GameCommand {
        public PlayCard {
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
        
        public static PlayCard fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }
}
