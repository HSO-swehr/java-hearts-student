package de.hso.cardgame.model;

import java.util.Optional;
import com.github.andrewoma.dexx.collection.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * A {@link GameEvent} is an event that occurs during a game. The game logic produces
 * events that are then consumed by the game clients.
 *
 * The GameEvent interface is sealed, meaning that all implementing classes are known
 * at compile time. This allows the game logic to use a pattern matching switch statement
 * to process the events.
 */
public sealed interface GameEvent extends JSONSerialization
    permits GameEvent.PlayerRegistered, GameEvent.HandsDealt, GameEvent.PlayerTurn, GameEvent.CardPlayed,
        GameEvent.TrickTaken, GameEvent.GameError, GameEvent.PlayerError, GameEvent.GameOver
{

    /**
     * If this method returns a {@link Player} value, the event is only delivered to this player.
     * Otherwise, all players receive the event. This method must be used for the
     * {@link HandsDealt} event to deliver the cards only to the correct player.
     */
    default Optional<Player> eventTarget() {
        return Optional.empty();
    }

    /**
     * Event: Player {@code player} has registered with name {@code name}.
     * {@code otherPlayers} are the others registered players.
     */
    public static record PlayerRegistered(Player player, String name, Map<Player, String> otherPlayers) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static PlayerRegistered fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: Cards {@code hand} were dealt to player {@code player}.
     * Important: this event must be delivered only to the player that actually receives the cards.
     */
    public static record HandsDealt(Player player, Hand hand) implements GameEvent {
        @Override public Optional<Player> eventTarget() {
            return Optional.of(player);
        }

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static HandsDealt fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: Player {@code player} should now taking their turn.
     */
    public static record PlayerTurn(Player player) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static PlayerTurn fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: Player {@code player} wants to playe card {@code card}.
     */
    public static record CardPlayed(Player player, Card card) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static CardPlayed fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: Player {@code player} has taken the given trick.
     */
    public static record TrickTaken(Player player, Trick trick) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static TrickTaken fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: An error has occurred, no specific player is at fault for this error.
     */
    public static record GameError(String msg) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static GameError fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: An error has occurred, a specific player is at fault for this error.
     */
    public static record PlayerError(Player player, String msg) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static PlayerError fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }

    /**
     * Event: Game is over.
     */
    public static record GameOver(Score score) implements GameEvent {

        @Override
        public JsonElement toJSON() {
            throw new RuntimeException("not implemented");
        }
        
        public static GameOver fromJSON(JsonElement json) {
            throw new RuntimeException("not implemented");
        }
    }
}

