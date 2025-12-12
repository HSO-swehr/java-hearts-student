package de.hso.cardgame.model;

import com.github.andrewoma.dexx.collection.LinkedLists;
import com.github.andrewoma.dexx.collection.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * The rank of a card. We use the numbers 2-14 to represent the ranks, such that
 * 2..10 represent numeric ranks and 11..14 represent jack, queen, king and ace.
 */
public record Rank(int value) implements JSONSerialization {

    public static final int MIN_VALUE = 2;
    public static final int MAX_VALUE = 14;

    public static final Rank ACE = new Rank(14);
    public static final Rank KING = new Rank(13);
    public static final Rank QUEEN = new Rank(12);
    public static final Rank JACK = new Rank(11);

    public Rank {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new RuntimeException("Invalid rank value: " + value);
        }
    }

    public static List<Rank> values() {
        var builder = LinkedLists.<Rank>builder();
        for (int i = MIN_VALUE; i <= MAX_VALUE; i++) {
            builder.add(new Rank(i));
        }
        List<Rank> l = builder.build();
        return l;
    }

    @Override public String toString() {
        int r = this.value;
        return switch (r) {
            case 14 -> "Ace";
            case 13 -> "King";
            case 12 -> "Queen";
            case 11 -> "Jack";
            default -> "Rank[" + r + "]";
        };
    }

    public static Rank fromString(String s) {
        try {
            var value = switch (s) {
                case "Ace" -> 14;
                case "King" -> 13;
                case "Queen" -> 12;
                case "Jack" -> 11;
                default -> Integer.valueOf(s);
            };
            return new Rank(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public JsonElement toJSON() {
        throw new RuntimeException("not implemented");
    }
    
    public static Rank fromJSON(JsonElement json) {
        throw new RuntimeException("not implemented");
    }
}
