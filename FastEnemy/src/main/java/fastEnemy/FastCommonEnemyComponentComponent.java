package fastEnemy;

import enemy.CommonEnemyComponent;
import javafx.geometry.Point2D;

import java.util.List;

public class FastCommonEnemyComponentComponent extends CommonEnemyComponent {
    public FastCommonEnemyComponentComponent(List<Point2D> wayPoints){
        super(wayPoints);
        this.speed = 300;
        this.maxHealth = 1;
    }
}
