package normalEnemy;

import enemy.Enemy;
import javafx.geometry.Point2D;
import objectPool.IObjectPool;
import health.HealthComponent;

import java.util.List;

public class NormalEnemyComponent extends Enemy {
    public NormalEnemyComponent(List<Point2D> wayPoints, double speed, IObjectPool objectPool, String objectPoolName) {
        super(wayPoints, speed, objectPool, objectPoolName);
    }

    @Override
    public void reset(){
        super.reset();
        getEntity().getComponent(HealthComponent.class).setHealth(50);
    }
}
