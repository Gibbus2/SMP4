package enemy.data;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;


public class PointMovementSystemComponent extends Component {
    int currentWayPoint = 0;
    private Point2D target;
    private double speed;
    //How to get waypoints from FXGL object from TMX file
    Polyline polyline = FXGL.getGameWorld().getEntitiesByType(map.EntityType.WAYPOINT).getFirst().getObject("polyline");
    //polyline.getPoints returns an array of the points in the polyline

    public void moveEntity(Entity entity){
        entity.translateX(entity.getComponent(EnemyComponent.class).getState().ds);
    }
    public void incrementWayPoint() {
        currentWayPoint++;
    }

    public PointMovementSystemComponent(Point2D target){
        this.target = target;
    }

    @Override
    public void onUpdate(double tpf) {
        Point2D direction = target.subtract(entity.getPosition()).normalize();
        Point2D velocity = direction.multiply(speed * tpf);
        entity.translate(velocity);
    }

    public void wayPointSystem(Entity entity) {

    }
}

