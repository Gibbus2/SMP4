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

    public void moveEntityToX(Entity entity, double destX) {
        new AnimationBuilder()
                .duration(Duration.seconds(10))
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

    public void moveEntityToY(Entity entity, double destY) {
        new AnimationBuilder()
                .duration(Duration.seconds(10))
                .interpolator(Interpolator.LINEAR)
                .onFinished(this::incrementWayPoint)
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
                moveEntityToX(entity, 100);
                break;
            case 1:
                moveEntityToY(entity, 100);
                break;
            case 2:
                moveEntityToX(entity, 50);
                break;
        }
    }
}
