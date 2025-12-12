package de.hso.cardgame.model;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BasicTypesJSONSerializationTest {

    @Property
    void suitSerialization(@ForAll Suit suit) {
        var json = suit.toJSON();
        var deserialized = Suit.fromJSON(json);
        assertThat(deserialized).isEqualTo(suit);
    }

    @Property
    void rankSerialization(@ForAll @IntRange(min = 2, max = 14) int value) {
        var rank = new Rank(value);
        var json = rank.toJSON();
        var deserialized = Rank.fromJSON(json);
        assertThat(deserialized).isEqualTo(rank);
    }

    @Property
    void playerSerialization(@ForAll Player player) {
        var json = player.toJSON();
        var deserialized = Player.fromJSON(json);
        assertThat(deserialized).isEqualTo(player);
    }

    @Property
    void cardSerialization(@ForAll Suit suit, @ForAll @IntRange(min = 2, max = 14) int value) {
        var card = new Card(suit, new Rank(value));
        var json = card.toJSON();
        var deserialized = Card.fromJSON(json);
        assertThat(deserialized).isEqualTo(card);
    }
}
