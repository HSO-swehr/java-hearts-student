package de.hso.cardgame.gamecentral.logic;

import java.util.*;

import de.hso.cardgame.model.GameEvent;

public class EventCollector implements EventConsumer {

    private List<GameEvent> events = new ArrayList<>();
    private boolean dieOnError;

    public EventCollector(boolean dieOnError) {
        this.dieOnError = dieOnError;
    }

    public EventCollector() {
        this.dieOnError = false;
    }

    public List<GameEvent> getEvents() {
        return events;
    }

    @Override
    public void consumeEvent(GameEvent event) {
        switch (event) {
            case GameEvent.GameError err -> {
                throw new RuntimeException("GameError: " + err);
            }
            case GameEvent.PlayerError err -> {
                throw new RuntimeException("PlayerError: " + err);
            }
            default -> {

            }
        }
        events.add(event);
    }

}
