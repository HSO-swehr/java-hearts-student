package de.hso.cardgame.clients;

import de.hso.cardgame.gamecentral.logic.EventConsumer;
import de.hso.cardgame.model.GameEvent;
import de.hso.cardgame.model.GameEvent.CardPlayed;
import de.hso.cardgame.model.GameEvent.GameError;
import de.hso.cardgame.model.GameEvent.GameOver;
import de.hso.cardgame.model.GameEvent.HandsDealt;
import de.hso.cardgame.model.GameEvent.PlayerError;
import de.hso.cardgame.model.GameEvent.PlayerRegistered;
import de.hso.cardgame.model.GameEvent.PlayerTurn;
import de.hso.cardgame.model.GameEvent.TrickTaken;

/**
 * Adapter class that adapts {@link GameEvent}s to the {@link GameEventProcessor} interface.
 * This class is used to adapt game events to the communication interface of a player.
 */
public class GameEventAdapter implements EventConsumer {

    private final GameEventProcessor proc;

    public GameEventAdapter(GameEventProcessor proc) {
        this.proc = proc;
    }

    @Override
    public void consumeEvent(GameEvent event) {
        switch (event) {
        case PlayerRegistered pr -> {
            proc.updatePlayers(pr);
        }
        case HandsDealt hd -> {
            proc.setCards(hd);
        }
        case PlayerTurn pt -> {
            proc.updatePlayerTurn(pt);
        }
        case CardPlayed cp -> {
            proc.updateCardPlayed(cp);
        }
        case TrickTaken tt -> {
            proc.onTrickTaken(tt);
        }
        case PlayerError pe -> {
            proc.endOfGame("Player Error (" + pe.player() + "): " + pe.msg());
        }
        case GameError ge -> {
            proc.endOfGame("Game Error: " + ge.msg());
        }
        case GameOver go -> {
            proc.endOfGame("Game Over: " + go.score());
        }
        }
    }

}
