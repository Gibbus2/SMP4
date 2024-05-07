package normalEnemy;

import enemy.Enemy;
import javafx.geometry.Point2D;

import java.util.List;

public class NormalEnemyComponent extends Enemy {
    public NormalEnemyComponent(List<Point2D> wayPoints) {
        super(wayPoints);
        this.speed = 150;
        this.maxHealth = 1;
    }
}
