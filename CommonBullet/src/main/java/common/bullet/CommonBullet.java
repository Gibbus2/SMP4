package common.bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class CommonBullet extends Component {
    private double speed;
    private double damage;
    private Entity target;

    public CommonBullet(double speed, double damage, Entity target) {
        this.speed = speed;
        this.damage = damage;
        this.target = target;
    }
    @Override
    public void onUpdate(double tpf) {
        //rotate towards target
        Point2D p1 = getEntity().getPosition();
        Point2D p2 = target.getPosition();
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
        double radians = Math.toRadians(getEntity().getRotation());
        double y = speed * Math.sin(radians) * tpf;
        double x = speed * Math.cos(radians) * tpf;
        getEntity().setPosition(getEntity().getPosition().add(x,y));
    }
    public double getSpeed() {
        return speed;
    }
    public double getDamage() {
        return damage;
    }
    public Entity getTarget() {
        return target;
    }
}
