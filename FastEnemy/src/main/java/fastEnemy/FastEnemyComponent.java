package fastEnemy;

import enemy.Enemy;
import javafx.geometry.Point2D;

import java.util.List;

public class FastEnemyComponent extends Enemy {
    public FastEnemyComponent(List<Point2D> wayPoints){
        super(wayPoints);
        this.speed = 300;
        this.maxHealth = 1;
    }
}
