package de.hso.cardgame.clients;

import java.util.Optional;
import java.util.Random;

import de.hso.cardgame.model.Card;
import de.hso.cardgame.model.Hand;
import de.hso.cardgame.model.Player;
import de.hso.cardgame.model.Trick;
import de.hso.cardgame.model.Suit;

/**
 * A very stupid AI that plays the first card of the leading suit it finds in its hand.
 * If it has no card of the leading suit, it plays an arbitrary card.
 */
public class PlayerStupidAI implements PlayerBehavior {

    private Hand hand;
    private Player p;
    private String name;
    private Trick currentTrick = Trick.empty;

    public PlayerStupidAI() {
        var i = new Random().nextInt(100000);
        this.name = "AI" + i;
    }

    @Override
    public void startGame(Player p, Hand hand) {
        this.p = p;
        this.hand = hand;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void cardPlayed(Player p, Card card) {
        this.currentTrick = this.currentTrick.addCard(p, card);
    }

    @Override
    public Card playCard() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void trickTaken(Player p) {
        this.currentTrick = Trick.empty;
    }

}
