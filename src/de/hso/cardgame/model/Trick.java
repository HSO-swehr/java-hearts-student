package de.hso.cardgame.model;

import java.util.Optional;

import com.github.andrewoma.dexx.collection.LinkedLists;
import com.github.andrewoma.dexx.collection.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The representation of a trick, i.e., the cards played by the players in a round.
 * The trick is represented as a list of {@link PlayerCard}s because card ordering matters
 * for determining the winner of the trick.
 */
public record Trick(List<PlayerCard> cards) implements JSONSerialization {

    public Trick {
        if (cards == null) {
            throw new NullPointerException("cards must not be null.");
        }
    }

    public static Trick empty = new Trick(LinkedLists.of());

    public Trick addCard(Player p, Card c) {
        return new Trick(this.cards.append(new PlayerCard(p, c)));
    }

    public Optional<Suit> leadingSuit() {
        if (cards.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(cards.get(0).card().suit());
        }
    }

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
    
    public static Trick fromJSON(JsonElement json) {
        throw new RuntimeException("not implemented");
    }
}
