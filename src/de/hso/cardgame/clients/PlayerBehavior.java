package de.hso.cardgame.clients;

import de.hso.cardgame.model.Card;
import de.hso.cardgame.model.Hand;
import de.hso.cardgame.model.Player;

/**
 * Interface for the behavior of a player. This interface can be implemented in various ways.
 * For example:
 *
 * - A player playing on the command line (see {@link PlayerConsole})
 * - A player being driven by artificial intelligence (see {@link PlayerStupidAI})
 */
public interface PlayerBehavior {
    String getName();
    void startGame(Player p, Hand hand);
    void cardPlayed(Player p, Card card);
    Card playCard();
    void trickTaken(Player p);
}
