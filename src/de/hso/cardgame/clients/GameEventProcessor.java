package de.hso.cardgame.clients;

import de.hso.cardgame.model.GameEvent.CardPlayed;
import de.hso.cardgame.model.GameEvent.HandsDealt;
import de.hso.cardgame.model.GameEvent.PlayerRegistered;
import de.hso.cardgame.model.GameEvent.PlayerTurn;
import de.hso.cardgame.model.GameEvent.TrickTaken;

/**
 * Interface for processing game events.
 */
public interface GameEventProcessor {

    /**
     * Called when all players are registered and the game is about to start.
     */
    void updatePlayers(PlayerRegistered pr);

    /**
     * Called after the cards have been dealt to the players.
     */
    void setCards(HandsDealt hd);

    /**
     * Called when it is the turn of a player to play a card. The implementation
     */
    void updatePlayerTurn(PlayerTurn pt);

    /**
     * Called when some player has played a card.
     */
    void updateCardPlayed(CardPlayed cp);

    /**
     * Called when a trick has ended and has been taken by a player.
     */
    void onTrickTaken(TrickTaken tt);

    /**
     * Called when the game is over (either because all tricks have been played or because
     * of an error).
     */
    void endOfGame(String msg);
}
