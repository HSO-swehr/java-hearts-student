package de.hso.cardgame.clients;

import java.util.Scanner;

import de.hso.cardgame.model.Card;
import de.hso.cardgame.model.Hand;
import de.hso.cardgame.model.Player;

/**
 * An interactive player that reads input from the console.
 */
public class PlayerConsole implements PlayerBehavior {

    private String name;
    private Hand myHand;
    private Scanner in;

    public PlayerConsole(String name, Scanner in) {
        if (name == null) {
            throw new NullPointerException("name must not be null.");
        }
        this.name = name;
        this.in = in;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void startGame(Player p, Hand hand) {
        this.myHand = hand;
    }

    @Override
    public void cardPlayed(Player p, Card card) {
        // do nothing
    }

    private void print(String msg) {
        System.out.println("[" + name + "] " + msg);
    }

    @Override
    public Card playCard() {
        print("It's your turn. Cards on your hand: " + myHand.cards());
        print("Enter the card to play, e.g. \"Hearts 2\": ");
        String cardString = this.in.nextLine();
        var card = Card.fromString(cardString);
        if (card == null) {
            print("Invalid format for card. Input something like \"Hearts 6\" or \"Spades Ace\"");
            return playCard();
        }
        if (!myHand.cards().contains(card)) {
            print(cardString + " is not in your hand.");
            return playCard();
        }
        return card;
    }

    @Override
    public void trickTaken(Player p) {
        // do nothing
    }

}
