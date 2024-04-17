package enemy.data;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import common.services.PMSComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import common.data.EntityType;

import java.util.ArrayList;
import java.util.List;


public class PointMovementSystemComponent extends Component implements PMSComponentSPI {
    int currentWayPoint = 0;
    private List<Point2D> wayPoints;


    //How to get waypoints from FXGL object from TMX file
    //polyline.getPoints returns an array of the points in the polyline

    public void incrementWayPoint() {
        currentWayPoint++;
    }

    public PointMovementSystemComponent(){
        Polyline polyline = FXGL.getGameWorld().getEntitiesByType(EntityType.WAYPOINT).getFirst().getObject("polyline");
        this.wayPoints = new ArrayList<>();
        for(int i = 0; i < polyline.getPoints().size(); i+=2){
            wayPoints.add(new Point2D(polyline.getPoints().get(i), polyline.getPoints().get(i+1)));
        }
    }



    @Override
    public void onUpdate(double tpf) {
        Point2D target = wayPoints.get(currentWayPoint);
        System.out.println("Speed cuh: "+entity.getComponent(EnemyComponent.class).getDs());
        Point2D direction = target.subtract(entity.getPosition()).normalize();
        Point2D velocity = direction.multiply(entity.getComponent(EnemyComponent.class).getDs() * tpf);
        entity.translate(velocity);
//        entity.getComponent(EnemyComponent.class).getState().changeState(entity.getComponent(EnemyComponent.class).getSLOWED()); //this should be how to set state

        if(entity.getPosition().distance(target) < 3){
            incrementWayPoint();
        }
    }

    public void wayPointSystem(Entity entity) {

    }

    @Override
    public PMSComponentSPI createPMSComponent() {
        return new PointMovementSystemComponent();
    }
}

