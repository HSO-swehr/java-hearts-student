package de.hso.cardgame.model;

import com.github.andrewoma.dexx.collection.Set;

/**
 * The set of cards that a player got throughout the game by taking a trick. Note: these are
 * not the cards in the player's {@link Hand}.
 */
public record Stack(Set<Card> cards) {
    public Stack {
        if (cards == null) {
            throw new NullPointerException("cards must not be null.");
        }
    }
}
