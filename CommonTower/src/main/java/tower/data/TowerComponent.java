package tower.data;

import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import common.data.EntityType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import com.almasb.fxgl.app.*;

import java.util.List;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;


public class TowerComponent extends com.almasb.fxgl.entity.component.Component {
    private int towerDamage;
    private int towerPrice;
    private double towerFirerate;
    private int towerRange;
    private int towerX;
    private int towerY;

    private LocalTimer shootTimer;

    private StateComponent state;

    public TowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY) {
        this.towerDamage = towerDamage;
        this.towerPrice = towerPrice;
        this.towerFirerate = towerFirerate;
        this.towerRange = towerRange;
        this.towerX = towerX;
        this.towerY = towerY;

    }



    public List<Entity> nearestEnemy = getGameWorld().getEntitiesInRange(
            new Rectangle2D(towerX, towerY, towerRange, towerRange));

    @Override
    public void onAdded() {
        state = entity.getComponent(StateComponent.class);
        state.changeState(WAITING);

        shootTimer = newLocalTimer();
        shootTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        if (nearestEnemy != null && !nearestEnemy.isEmpty()) {
            state.changeState(SHOOTING);
        }
        else {
            state.changeState(WAITING);
        }
    }

    private void shoot(Entity enemy) {
        Point2D position = getEntity().getPosition();

    }


    private final EntityState SHOOTING = new EntityState("SHOOTING") {
        @Override
        protected void onUpdate(double tpf) {
            if (shootTimer.elapsed(Duration.seconds(towerFirerate))) {
                getGameWorld()
                        .getClosestEntity(entity, e -> e.isType(EntityType.ENEMY))
                        .ifPresent(nearestEnemy -> {
                            entity.rotateToVector(nearestEnemy.getPosition().subtract(entity.getPosition()));
                            entity.rotateBy(90);

                            shoot(nearestEnemy);
                            shootTimer.capture();
                        });
            }
        }
        @Override
        public void onEntering() {

        }

    };

    private final EntityState WAITING = new EntityState("WAITING") {
        @Override
        protected void onUpdate(double tpf) {

        }
        @Override
        public void onEntering() {

        }
    };



    public int getDamage(){
        return towerDamage;
    }
    public int getPrice(){
        return towerPrice;
    }
    public double getFirerate(){
        return towerFirerate;
    }
    public int getRange(){
        return towerRange;
    }
    public int getX(){
        return towerX;
    }
    public int getY(){
        return towerY;
    }
}
