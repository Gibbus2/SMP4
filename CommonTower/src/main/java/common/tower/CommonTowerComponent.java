package common.tower;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.time.LocalTimer;
import common.bullet.BulletSPI;
import common.data.EntityType;
import enemy.CommonEnemyComponent;
import health.HealthComponent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import objectPool.IObjectPool;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class CommonTowerComponent extends Component implements TowerSPI {
    protected int damage;
    public int getDamage(){ return damage; }
    protected int cost;
    @Override
    public int getCost(){ return cost; }

    protected double firerate; // in seconds
    public double getFirerate(){ return firerate; }

    protected int range;
    public int getRange(){ return range; }

    @Override
    public String getName() {
        return "";
    }

    public Image getImage() {
        return null;
    }

    protected IObjectPool objectPool;

    private TowerState targetting;

    protected List<Entity> enemiesInRange;
    public void addEnemy(Entity enemy) { enemiesInRange.add(enemy); }
    public void removeEnemy(Entity enemy) { enemiesInRange.remove(enemy); }

    protected LocalTimer shootTimer;

    private StateComponent state;

    private EntityState IDLE_STATE;
    private EntityState FIRST_STATE;
    private EntityState LAST_STATE;
    private EntityState STRONGEST_STATE;
    private EntityState WEAKEST_STATE;

    public CommonTowerComponent(IObjectPool objectPool) {
        this.damage = 1;
        this.cost = 10;
        this.firerate = 0.5;
        this.range = 15;
        this.targetting = TowerState.FIRST;
        enemiesInRange = new ArrayList<>();
        this.objectPool = objectPool;

    }

    public Component createComponent(IObjectPool objectPool) {
        return new CommonTowerComponent(objectPool);
    }

    @Override
    public void onAdded() {
        entity.addComponent(new StateComponent());
        state = entity.getComponent(StateComponent.class);

        getEntity().getComponent(BoundingBoxComponent.class).addHitBox(new HitBox(BoundingShape.circle(range)));
        getEntity().getComponent(ViewComponent.class).addChild(new Circle(range, Color.BLUE));

        shootTimer = FXGL.newLocalTimer();
        shootTimer.capture();

        enemiesInRange = new ArrayList<>();
        targetting = TowerState.FIRST; // Changed outside of this class.

        IDLE_STATE = new EntityState(TowerState.IDLE.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (enemiesInRange != null && !enemiesInRange.isEmpty()) {
                    switch (targetting) {
                        case FIRST:
                            state.changeState(FIRST_STATE);
                            break;
                        case LAST:
                            state.changeState(LAST_STATE);
                            break;
                        case STRONGEST:
                            state.changeState(STRONGEST_STATE);
                            break;
                        case WEAKEST:
                            state.changeState(WEAKEST_STATE);
                            break;
                    }
                }
            }
        };
        FIRST_STATE = new EntityState(TowerState.FIRST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                    state.changeState(IDLE_STATE);

                    if (targetting != TowerState.FIRST) {
                        switch (targetting) {
                            case LAST:
                                state.changeState(LAST_STATE);
                                break;
                            case STRONGEST:
                                state.changeState(STRONGEST_STATE);
                                break;
                            case WEAKEST:
                                state.changeState(WEAKEST_STATE);
                                break;
                        }
                    }

                    Entity target = sortByDistanceTraveled(enemiesInRange).getLast();
                    rotateToTarget(target);

                    if (shootTimer.elapsed(Duration.seconds(firerate))) {
                        shoot(target);
                    }
                }
            }
        };
        LAST_STATE = new EntityState(TowerState.LAST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                    state.changeState(IDLE_STATE);

                    if (targetting != TowerState.LAST) {
                        switch (targetting) {
                            case FIRST:
                                state.changeState(FIRST_STATE);
                                break;
                            case STRONGEST:
                                state.changeState(STRONGEST_STATE);
                                break;
                            case WEAKEST:
                                state.changeState(WEAKEST_STATE);
                                break;
                        }
                    }

                    Entity target = sortByDistanceTraveled(enemiesInRange).getFirst();
                    rotateToTarget(target);

                    if (shootTimer.elapsed(Duration.seconds(firerate))) {
                        shoot(target);
                    }
                }
            }
        };
        STRONGEST_STATE = new EntityState(TowerState.STRONGEST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                    state.changeState(IDLE_STATE);

                    if (targetting != TowerState.STRONGEST) {
                        switch (targetting) {
                            case FIRST:
                                state.changeState(FIRST_STATE);
                                break;
                            case LAST:
                                state.changeState(LAST_STATE);
                                break;
                            case WEAKEST:
                                state.changeState(WEAKEST_STATE);
                                break;
                        }
                    }

                    Entity target = sortByHealth(enemiesInRange).getLast();
                    rotateToTarget(target);

                    if (shootTimer.elapsed(Duration.seconds(firerate))) {
                        shoot(target);
                    }
                }
            }
        };
        WEAKEST_STATE = new EntityState(TowerState.WEAKEST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                    state.changeState(IDLE_STATE);

                    if (targetting != TowerState.WEAKEST) {
                        switch (targetting) {
                            case FIRST:
                                state.changeState(FIRST_STATE);
                                break;
                            case LAST:
                                state.changeState(LAST_STATE);
                                break;
                            case STRONGEST:
                                state.changeState(STRONGEST_STATE);
                                break;
                        }
                    }

                    Entity target = sortByHealth(enemiesInRange).getFirst();
                    rotateToTarget(target);

                    if (shootTimer.elapsed(Duration.seconds(firerate))) {
                        shoot(target);
                    }
                }
            }
        };

        state.changeState(IDLE_STATE);
    }

    private void shoot(Entity enemy) {

        getBulletSPIs().stream().findFirst().ifPresent(SPI -> {
            objectPool.createPool(EntityType.BULLET.toString(), () -> FXGL.entityBuilder()
                    .type(EntityType.BULLET)
                    .at(entity.getPosition())
                    .with(SPI.createComponent(enemy))
                    .build()
            );

            objectPool.getEntityFromPool(EntityType.BULLET.toString());
        });

        // TODO: ObjectPool this.
//        Entity bullet = FXGL.entityBuilder()
//                .type(EntityType.BULLET)
//                .at(entity.getPosition())
//                .with(new Bullet())
//                .buildAndAttach();

//        Bullet Bullet = bullet.getComponent(Bullet.class);
//        Bullet.setDirection(direction);
    }

    private void rotateToTarget(Entity target) {
        entity.rotateToVector(target.getPosition().subtract(entity.getPosition()));
    }

    public List<Entity> sortByDistanceTraveled(List<Entity> enemiesInRange) {
        List<Entity> list = new ArrayList<>(enemiesInRange);
        list.sort((o1, o2) -> {
            List<Component> components1 = o1.getComponents();
            List<Component> components2 = o2.getComponents();

            for (Component c1 : components1) {
                for (Component c2 : components2) {
                    if (c1 instanceof CommonEnemyComponent && c2 instanceof CommonEnemyComponent) {
                        return ((CommonEnemyComponent) c1).getDistanceTravelled() - ((CommonEnemyComponent) c2).getDistanceTravelled();
                    }
                }
            }
            return 0;
        });

        return list;
    }

    public List<Entity> sortByHealth(List<Entity> enemiesInRange) {
        List<Entity> list = new ArrayList<>(enemiesInRange);
        list.sort(Comparator.comparingInt((Entity o) -> o.getComponent(HealthComponent.class).getHealth()));

        return list;
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
