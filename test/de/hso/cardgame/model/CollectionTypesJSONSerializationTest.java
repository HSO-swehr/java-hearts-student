package de.hso.cardgame.model;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import static org.assertj.core.api.Assertions.assertThat;
import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.List;

public class CollectionTypesJSONSerializationTest implements TestDataGenerators {

    @Property
    void handSerialization(@ForAll("cardSets") Set<Card> cards) {
        var hand = new Hand(cards);
        var json = hand.toJSON();
        var deserialized = Hand.fromJSON(json);
        assertThat(deserialized).isEqualTo(hand);
    }

    @Property
    void playerCardSerialization(@ForAll Player player, @ForAll Suit suit, @ForAll @IntRange(min = 2, max = 14) int value) {
        var playerCard = new PlayerCard(player, new Card(suit, new Rank(value)));
        var json = playerCard.toJSON();
        var deserialized = PlayerCard.fromJSON(json);
        assertThat(deserialized).isEqualTo(playerCard);
    }

    @Property
    void trickSerialization(@ForAll("playerCards") List<PlayerCard> cards) {
        var trick = new Trick(cards);
        var json = trick.toJSON();
        var deserialized = Trick.fromJSON(json);
        assertThat(deserialized).isEqualTo(trick);
    }

    @Property
    void scoreSerialization(@ForAll("playerScoreMaps") Map<Player, Integer> score) {
        var scoreObj = new Score(score);
        var json = scoreObj.toJSON();
        var deserialized = Score.fromJSON(json);
        assertThat(deserialized).isEqualTo(scoreObj);
    }
}
