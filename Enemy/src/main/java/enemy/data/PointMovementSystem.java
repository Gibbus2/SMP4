package enemy.data;

import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.util.Duration;


public class PointMovementSystem {
    int currentWayPoint = 0;

    public void moveEntityToX(Entity entity, double destX, int speed) {
        double distance = Math.abs(destX - entity.getPosition().getX());
        double duration = distance / speed;
        new AnimationBuilder()
                .duration(Duration.seconds(duration))
                .interpolator(Interpolator.LINEAR)
                .onFinished(()->{
                    incrementWayPoint();
                    ghettoWayPointSystem(entity);
                })
                .translate(entity)
                .from(entity.getPosition())
                .to(new Point2D(destX, entity.getPosition().getY()))
                .buildAndPlay(FXGL.getGameScene());
    }

    public void moveEntityToY(Entity entity, double destY, int speed) {
        double distance = Math.abs(destY - entity.getPosition().getY());
        double duration = distance / speed;
        new AnimationBuilder()
                .duration(Duration.seconds(duration))
                .interpolator(Interpolator.LINEAR)
                .onFinished(()->{
                    incrementWayPoint();
                    ghettoWayPointSystem(entity);
                })
                .translate(entity)
                .from(entity.getPosition())
                .to(new Point2D(entity.getPosition().getX(), destY))
                .buildAndPlay(FXGL.getGameScene());
    }

    public void incrementWayPoint() {
        currentWayPoint++;
    }

    public void ghettoWayPointSystem(Entity entity) {
        switch (currentWayPoint) {
            case 0:
                moveEntityToX(entity, 295, 100);
                break;
            case 1:
                moveEntityToY(entity, 590, 100);
                break;
            case 2:
                moveEntityToX(entity, 1000, 100);
                break;
        }
    }
}
