package de.hso.cardgame.model;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;

import com.github.andrewoma.dexx.collection.Map;
import com.google.gson.JsonElement;

import de.hso.cardgame.gamecentral.logic.GameCommandSerializer;

public class GameCommandJSONSerializationTest implements TestDataGenerators {

    void check(GameCommand cmd, Function<JsonElement, GameCommand> parser) {
        var json = cmd.toJSON();
        var deserialized = parser.apply(json);
        assertThat(deserialized).isEqualTo(cmd);

        var json2 = GameCommandSerializer.toJSON(cmd);
        var deserialized2 = GameCommandSerializer.fromJSON(json2);
        assertThat(deserialized2).isEqualTo(cmd);
    }

    @Property
    void registerPlayerSerialization(@ForAll String name) {
        check(new GameCommand.RegisterPlayer(name), GameCommand.RegisterPlayer::fromJSON);
    }

    @Property
    void playCardSerialization(@ForAll Player player, @ForAll Suit suit, @ForAll @IntRange(min = 2, max = 14) int value) {
        check(new GameCommand.PlayCard(player, new Card(suit, new Rank(value))),
            GameCommand.PlayCard::fromJSON);
    }

    @Property
    void dealHandsSerialization(@ForAll("playerHandMaps") Map<Player, Hand> playerHands) {
        check(new GameCommand.DealHands(playerHands), GameCommand.DealHands::fromJSON);
    }
}
