package Enemy.data;

import javafx.event.Event;
import javafx.event.EventType;

public class EnemyReachedEndEvent extends Event {
    public static final EventType<EnemyReachedEndEvent> ANY = new EventType<>(Event.ANY, "ENEMY_REACHED_END");
    public EnemyReachedEndEvent() {
        super(ANY);
    }
}
