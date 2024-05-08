package heavyEnemy;

import enemy.Enemy;
import javafx.geometry.Point2D;


import java.util.List;

public class HeavyEnemyComponent extends Enemy {
    public HeavyEnemyComponent(List<Point2D> wayPoints){
        super(wayPoints);
        this.speed = 100;
        this.maxHealth = 5;
    }

}
