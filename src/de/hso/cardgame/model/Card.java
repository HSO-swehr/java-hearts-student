package de.hso.cardgame.model;

import com.github.andrewoma.dexx.collection.LinkedLists;
import com.github.andrewoma.dexx.collection.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Represents a card in a deck of cards. For example, Ace of Spades is a card.
 * (Homework: listen to the song "Ace of Spades" by Motörhead. It's a classic. Turn up the volume!)
 */
public record Card(Suit suit, Rank rank) implements JSONSerialization {

    public Card {
        if (suit == null) {
            throw new NullPointerException("suit must not be null.");
        }
        if (rank == null) {
            throw new NullPointerException("rank must not be null.");
        }
    }

    public static List<Card> wholeDeck() {
        Suit[] allSuits = Suit.values();
        List<Rank> allRanks = Rank.values();
        var builder = LinkedLists.<Card>builder();
        for (Suit s : allSuits) {
            for (Rank r : allRanks) {
                builder.add(new Card(s, r));
            }
        }
        return builder.build();
    }

    @Override
    public String toString() {
        return suit.toString() + " " + rank.toString();
    }

    public static Card fromString(String cardString) {
        var parsed = cardString.split(" ");
        if (parsed.length != 2) {
            return null;
        }
        Suit suit;
        try {
            suit = Suit.valueOf(parsed[0]);
        } catch (IllegalArgumentException e) {
            return null;
        }
        Rank rank = Rank.fromString(parsed[1]);
        if (rank == null) {
            return null;
        }
        return new Card(suit, rank);
    }

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
    
    public static Card fromJSON(JsonElement json) {
        throw new RuntimeException("not implemented");
    }
}
