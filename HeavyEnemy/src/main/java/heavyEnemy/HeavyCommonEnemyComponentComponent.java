package heavyEnemy;

import enemy.CommonEnemyComponent;
import javafx.geometry.Point2D;


import java.util.List;

public class HeavyCommonEnemyComponentComponent extends CommonEnemyComponent {
    public HeavyCommonEnemyComponentComponent(List<Point2D> wayPoints){
        super(wayPoints);
        this.speed = 100;
        this.maxHealth = 5;
    }

}
