package normalEnemy;

import enemy.CommonEnemyComponent;
import javafx.geometry.Point2D;

import java.util.List;

public class NormalEnemyComponent extends CommonEnemyComponent {
    public NormalEnemyComponent(List<Point2D> wayPoints) {
        super(wayPoints);
        this.speed = 150;
        this.maxHealth = 1;
    }
}
