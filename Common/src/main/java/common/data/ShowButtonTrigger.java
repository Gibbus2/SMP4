package common.data;

import javafx.event.EventType;
import javafx.event.Event;

public class ShowButtonTrigger extends Event{
    public static final EventType<ShowButtonTrigger> ANY = new EventType<>(Event.ANY, "SHOW");

    public ShowButtonTrigger() {
        super(ANY);
    }
}
