package de.hso.cardgame.model;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Set;
import com.google.gson.JsonElement;

import de.hso.cardgame.gamecentral.logic.GameEventSerializer;

import com.github.andrewoma.dexx.collection.List;

public class GameEventJSONSerializationTest  implements TestDataGenerators {

    void check(GameEvent cmd, Function<JsonElement, GameEvent> parser) {
        var json = cmd.toJSON();
        var deserialized = parser.apply(json);
        assertThat(deserialized).isEqualTo(cmd);

        var json2 = GameEventSerializer.toJSON(cmd);
        var deserialized2 = GameEventSerializer.fromJSON(json2);
        assertThat(deserialized2).isEqualTo(cmd);
    }

    @Property
    void playerRegisteredSerialization(
            @ForAll Player player,
            @ForAll String name,
            @ForAll("playerStringMaps") Map<Player, String> otherPlayers) {
        check(new GameEvent.PlayerRegistered(player, name, otherPlayers),
                GameEvent.PlayerRegistered::fromJSON);
    }

    @Property
    void handsDealtSerialization(@ForAll Player player, @ForAll("cardSets") Set<Card> cards) {
        var event = new GameEvent.HandsDealt(player, new Hand(cards));
        check(event, GameEvent.HandsDealt::fromJSON);
    }

    @Property
    void playerTurnSerialization(@ForAll Player player) {
        var event = new GameEvent.PlayerTurn(player);
        check(event, GameEvent.PlayerTurn::fromJSON);
    }

    @Property
    void cardPlayedSerialization(@ForAll Player player, @ForAll Suit suit, @ForAll @IntRange(min = 2, max = 14) int value) {
        var event = new GameEvent.CardPlayed(player, new Card(suit, new Rank(value)));
        check(event, GameEvent.CardPlayed::fromJSON);
    }

    @Property
    void trickTakenSerialization(@ForAll Player player, @ForAll("playerCards") List<PlayerCard> cards) {
        var event = new GameEvent.TrickTaken(player, new Trick(cards));
        check(event, GameEvent.TrickTaken::fromJSON);
    }

    @Property
    void gameErrorSerialization(@ForAll String msg) {
        var event = new GameEvent.GameError(msg);
        check(event, GameEvent.GameError::fromJSON);
    }

    @Property
    void playerErrorSerialization(@ForAll Player player, @ForAll String msg) {
        var event = new GameEvent.PlayerError(player, msg);
        check(event, GameEvent.PlayerError::fromJSON);
    }

    @Property
    void gameOverSerialization(@ForAll("playerScoreMaps") Map<Player, Integer> score) {
        var event = new GameEvent.GameOver(new Score(score));
        check(event, GameEvent.GameOver::fromJSON);
    }
}
