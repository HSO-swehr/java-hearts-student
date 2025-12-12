package de.hso.cardgame.model;

import net.jqwik.api.*;
import com.github.andrewoma.dexx.collection.Map;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.List;
import com.github.andrewoma.dexx.collection.Maps;
import com.github.andrewoma.dexx.collection.Pair;
import com.github.andrewoma.dexx.collection.LinkedLists;
import com.github.andrewoma.dexx.collection.HashSet;

public interface TestDataGenerators {

    @Provide
    public static Arbitrary<Map<Player, Hand>> playerHandMaps() {
        return Arbitraries.maps(
            Arbitraries.of(Player.values()),
            cardSets()
        ).map(map -> {
                var builder = Maps.<Player, Hand>builder();
                map.forEach((k, v) -> builder.add(new Pair<>(k, new Hand(v))));
                return builder.build();
        });
    }

    @Provide
    public static Arbitrary<Map<Player, String>> playerStringMaps() {
        return Arbitraries.maps(
            Arbitraries.of(Player.values()),
            Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(10)
        ).map(map -> {
            var builder = Maps.<Player, String>builder();
            map.forEach((k, v) -> builder.add(new Pair<>(k, v)));
            return builder.build();
        });
    }

    @Provide
    public static Arbitrary<Map<Player, Integer>> playerScoreMaps() {
        return Arbitraries.maps(
            Arbitraries.of(Player.values()),
            Arbitraries.integers().between(0, 100)
        ).map(map -> {
            var builder = Maps.<Player, Integer>builder();
            map.forEach((k, v) -> builder.add(new Pair<>(k, v)));
            return builder.build();
        });
    }

    @Provide
    public static Arbitrary<List<PlayerCard>> playerCards() {
        return Arbitraries.integers().between(0, 4)
            .flatMap(size ->
                Arbitraries.of(Player.values())
                    .list()
                    .ofSize(size)
                    .flatMap(players ->
                        Arbitraries.of(Suit.values())
                            .list()
                            .ofSize(size)
                            .flatMap(suits ->
                                Arbitraries.integers().between(2, 14)
                                    .list()
                                    .ofSize(size)
                                    .map(values -> {
                                        var builder = LinkedLists.<PlayerCard>builder();
                                        for (int i = 0; i < size; i++) {
                                            builder.add(new PlayerCard(players.get(i), new Card(suits.get(i), new Rank(values.get(i)))));
                                        }
                                        return builder.build();
                                    })
                            )
                    )
            );
    }

    @Provide
    public static Arbitrary<Set<Card>> cardSets() {
        return Arbitraries.integers().between(0, 13)
            .flatMap(size ->
                Arbitraries.of(Suit.values())
                    .list()
                    .ofSize(size)
                    .flatMap(suits ->
                        Arbitraries.integers().between(2, 14)
                            .list()
                            .ofSize(size)
                            .map(values -> {
                                var set = HashSet.<Card>empty();
                                for (int i = 0; i < size; i++) {
                                    set = set.add(new Card(suits.get(i), new Rank(values.get(i))));
                                }
                                return set;
                            })
                    )
            );
    }
}
