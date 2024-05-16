package common.data;

import javafx.event.Event;
import javafx.event.EventType;

public class StartWaveTrigger extends Event {
    public static final EventType<StartWaveTrigger> ANY = new EventType<>(Event.ANY, "START");

    public StartWaveTrigger() {
        super(ANY);
    }
}
