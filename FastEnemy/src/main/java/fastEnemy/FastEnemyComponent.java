package fastEnemy;

import enemy.CommonEnemyComponent;
import javafx.geometry.Point2D;

import java.util.List;

public class FastEnemyComponent extends CommonEnemyComponent {
    public FastEnemyComponent(List<Point2D> wayPoints){
        super(wayPoints);
        this.speed = 300;
        this.maxHealth = 1;
    }
}
