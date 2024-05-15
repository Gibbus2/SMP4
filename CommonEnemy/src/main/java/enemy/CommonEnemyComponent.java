package enemy;

import com.almasb.fxgl.entity.component.Component;
import common.player.PlayerSPI;
import javafx.geometry.Point2D;
import health.HealthComponent;
import objectPool.PooledObjectComponent;


import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CommonEnemyComponent extends Component {

    protected double speed;
    protected int maxHealth;
    private final List<Point2D> wayPoints;
    private int targetWaypoint;
    private int distanceTravelled;
    private Runnable onRemove;

    public CommonEnemyComponent(List<Point2D> wayPoints){
        this.wayPoints = wayPoints;
        this.speed = 100;
        this.distanceTravelled = 0;
        this.maxHealth = 5;
        this.targetWaypoint = 1;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setOnRemove(Runnable onRemove) {
        this.onRemove = onRemove;
    }

    @Override
    public void onUpdate(double tpf) {
        if (wayPoints != null) {
            //move in rotation heading
            double radians = Math.toRadians(getEntity().getRotation());
            double y = speed * Math.sin(radians) * tpf;
            double x = speed * Math.cos(radians) * tpf;

            setPos(getPos().add(x,y));

            this.distanceTravelled += (int) (x + y);

            //check if we have passed the waypoint
            double distMoved = getPos().distance(wayPoints.get(targetWaypoint - 1));
            double maxDist = wayPoints.get(targetWaypoint - 1).distance(wayPoints.get(targetWaypoint));
            if (distMoved >= maxDist && targetWaypoint < wayPoints.size() - 1){
                //rotate to next waypoint and reset position to the current waypoint
                rotate(targetWaypoint);
                setPos(wayPoints.get(targetWaypoint));
                targetWaypoint++;
            }
        }
    }

    @Override
    public void onAdded() {
        super.onAdded();
        getEntity().addComponent(new HealthComponent(this.maxHealth));
    }

    public void reset(){
        rotate(0);
        setPos(wayPoints.getFirst());
        this.targetWaypoint = 1;
        setDistanceTravelled(0);
        getEntity().getComponent(HealthComponent.class).setHealth(this.maxHealth);
    }

    public void setDistanceTravelled(int i) {
        this.distanceTravelled = i;
    }

    public void damage(int dmg, boolean isPlayer){
        HealthComponent health = getEntity().getComponent(HealthComponent.class);
        health.changeHealth(-dmg);
        if(health.isDead()){
            if (getEntity().hasComponent(PooledObjectComponent.class)) {
                getEntity().getComponent(PooledObjectComponent.class).returnToPool();
            } else {
                getEntity().removeFromWorld();
            }

            if (onRemove != null) {
                this.onRemove.run();
            }

            if (!isPlayer) {
                getPlayerSPIs().stream().findFirst().ifPresent((playerSPI -> playerSPI.changeMoney(this.maxHealth)));
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

    public int getHealth() {
        return getEntity().getComponent(HealthComponent.class).getHealth();
    }

    private Collection<? extends PlayerSPI> getPlayerSPIs() {
        System.out.println("Loading PlayerSPI.");
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
