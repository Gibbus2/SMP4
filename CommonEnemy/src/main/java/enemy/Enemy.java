package enemy;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import health.HealthComponent;
import objectPool.IObjectPool;
import objectPool.PooledObjectComponent;


import java.util.List;

public class Enemy extends Component {

    private double speed;
    private final List<Point2D> wayPoints;
    private int targetWaypoint;
    private int distanceTravelled;
    private Runnable onRemove;

    public Enemy(List<Point2D> wayPoints, double speed){
        this.wayPoints = wayPoints;
        this.speed = speed;
        this.distanceTravelled = 0;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setOnRemove(Runnable onRemove) {
        this.onRemove = onRemove;
    }

    @Override
    public void onUpdate(double tpf) {
        //move in rotation heading
        double radians = Math.toRadians(getEntity().getRotation());
        double y = speed * Math.sin(radians) * tpf;
        double x = speed * Math.cos(radians) * tpf;

        setPos(getPos().add(x,y));

        this.distanceTravelled += (int) (x + y);

        //check if we have passed the waypoint
        double distMoved = getPos().distance(wayPoints.get(targetWaypoint - 1));
        double maxDist = wayPoints.get(targetWaypoint - 1).distance(wayPoints.get(targetWaypoint));
        if( distMoved >= maxDist && targetWaypoint < wayPoints.size() - 1){
            //rotate to next waypoint and reset position to the current waypoint
            rotate(targetWaypoint);
            setPos(wayPoints.get(targetWaypoint));
            targetWaypoint++;
        }
    }

    @Override
    public void onAdded() {
        super.onAdded();
        getEntity().addComponent(new HealthComponent(100));

    }

    public void reset(){
        rotate(0);
        setPos(wayPoints.getFirst());
        this.targetWaypoint = 1;
        this.distanceTravelled = 0;
    }

    public void damage(int dmg){
        HealthComponent health = getEntity().getComponent(HealthComponent.class);
        health.setHealth(health.getHealth() - dmg);
        if(health.isDead()){
            this.onRemove.run();
            if (getEntity().hasComponent(PooledObjectComponent.class)) {
                getEntity().getComponent(PooledObjectComponent.class).returnToPool();
            } else {
                getEntity().removeFromWorld();
            }
        }
    }

    private void rotate(int waypointIndex){
        Point2D p1 = wayPoints.get(waypointIndex);
        Point2D p2 = wayPoints.get(waypointIndex + 1);

        double deltaX = p1.getX() - p2.getX();
        double deltaY = p1.getY() - p2.getY();

        // Calculate the angle in radians using atan2
        double angleRad = Math.atan2(deltaY, deltaX);

        // Convert the angle from radians to degrees and rotate it 180 deg
        double angleDeg = Math.toDegrees(angleRad) - 180;

        if (angleDeg < 0) {
            angleDeg += 360;
        }

        getEntity().setRotation(angleDeg);
    }

    private Point2D getPos(){
        // add offset to get position of center
        return getEntity().getPosition().add(offset());
    }

    private void setPos(Point2D point){
        // subtract offset to move center to origin
        Point2D p = point.subtract(offset());
        getEntity().setPosition(p);
    }

    private Point2D offset(){
        return new Point2D(getEntity().getWidth()/2, getEntity().getHeight()/2);
    }
}
