package common.tower;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import common.bullet.BulletSPI;
import common.bullet.CommonBullet;
import common.data.EntityType;
import common.data.GameData;
import enemy.CommonEnemyComponent;
import health.HealthComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import objectPool.IObjectPool;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class CommonTowerComponent extends Component  {
    private final GameData gameData;
    protected int cost;
    public int getCost(){ return cost; }

    protected double firerate; // in seconds
    public double getFirerate(){ return firerate; }

    protected int range;
    public int getRange(){ return range; }

    protected String name;
    public String getName() {
        return name;
    }

    public Image getImage() {
        return null;
    }

    protected IObjectPool objectPool;

    private TowerState targeting;

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

    // Some UI stuff trying to show the targeting state of the tower.
    // private InfoBox infoBox;

    public CommonTowerComponent(IObjectPool objectPool, GameData gameData) {
        this.cost = 10;
        this.firerate = 0.5;
        this.range = 300;
        this.targeting = TowerState.FIRST;
        enemiesInRange = new ArrayList<>();
        this.objectPool = objectPool;
        this.gameData = gameData;
    }

    public Component createComponent(IObjectPool objectPool, GameData gameData) {
        return new CommonTowerComponent(objectPool, gameData);
    }

    @Override
    public void onAdded() {
        entity.addComponent(new StateComponent());
        state = entity.getComponent(StateComponent.class);
        entity.getComponent(ViewComponent.class).addChild(new Rectangle(5, 48, Color.RED));

//        getEntity().getComponent(BoundingBoxComponent.class).addHitBox(new HitBox(BoundingShape.circle(range)));
//        getEntity().getComponent(ViewComponent.class).addChild(new Circle(range, Color.BLUE));

        System.out.println("Creating Tower Collider.");
        FXGL.entityBuilder()
                .type(EntityType.TOWER)
                .at(new Point2D(
                        (entity.getPosition().getX() + entity.getComponent(BoundingBoxComponent.class).getWidth() / 2 - (double) range / 2),
                        (entity.getPosition().getY() + entity.getComponent(BoundingBoxComponent.class).getHeight() / 2) - (double) range / 2))
                .viewWithBBox(new Rectangle(range, range, new Color(1,1,1,0.2)))
                .with(new CollidableComponent(true))
                .with(new CommonTowerCollider(this))
                .buildAndAttach();

        shootTimer = FXGL.newLocalTimer();
        shootTimer.capture();

        enemiesInRange = new ArrayList<>();
        targeting = TowerState.FIRST;

        IDLE_STATE = new EntityState(TowerState.IDLE.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (enemiesInRange != null && !enemiesInRange.isEmpty()) {
                    switch (targeting) {
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
                    if (shootTimer.elapsed(Duration.seconds(firerate))) {
                        if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                        state.changeState(IDLE_STATE);

                        if (targeting != TowerState.FIRST) {
                            switch (targeting) {
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

                        shoot(target);
                    }
                }
            }
        };
        LAST_STATE = new EntityState(TowerState.LAST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (shootTimer.elapsed(Duration.seconds(firerate))) {
                    if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                        state.changeState(IDLE_STATE);

                        if (targeting != TowerState.LAST) {
                            switch (targeting) {
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

                        shoot(target);
                    }
                }
            }
        };
        STRONGEST_STATE = new EntityState(TowerState.STRONGEST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (shootTimer.elapsed(Duration.seconds(firerate))) {
                    if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                        state.changeState(IDLE_STATE);

                        if (targeting != TowerState.STRONGEST) {
                            switch (targeting) {
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

                        shoot(target);
                    }
                }
            }
        };
        WEAKEST_STATE = new EntityState(TowerState.WEAKEST.toString()) {
            @Override
            protected void onUpdate(double tpf) {
                if (shootTimer.elapsed(Duration.seconds(firerate))) {
                    if (enemiesInRange == null || !enemiesInRange.isEmpty()) {
                        state.changeState(IDLE_STATE);

                        if (targeting != TowerState.WEAKEST) {
                            switch (targeting) {
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

                        shoot(target);
                    }
                }
            }
        };

        state.changeState(IDLE_STATE);

        // Make tower states cycleable by clicking on the tower
        entity.getComponent(ViewComponent.class).addOnClickHandler(mouseEvent -> {
            List<TowerState> states = Arrays.asList(TowerState.values());

            int index = states.indexOf(targeting);

            // Decrement the index, and reset to the last state if it's less than 0
            index = (index - 1 < 0) ? states.size() - 2 : index - 1;

            targeting = states.get(index);
            System.out.println("Targetting: " + targeting.toString());
        });

        // Some UI stuff trying to show the targeting state of the tower.
//        entity.getComponent(ViewComponent.class).addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
//            infoBox = new InfoBox(targeting);
//        });
//        entity.getComponent(ViewComponent.class).addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
//            infoBox = null;
//        });
    }

    private void shoot(Entity enemy) {
        if (objectPool != null) {
            getBulletSPIs().stream().findFirst().ifPresent(SPI -> {
                Texture texture = new Texture(SPI.getImage());
                objectPool.createPool(EntityType.BULLET.toString(), () -> FXGL.entityBuilder()
                        .type(EntityType.BULLET)
                        .viewWithBBox(new Rectangle(texture.getWidth(), texture.getHeight(), (gameData.debug) ? Color.GREEN : new Color(0, 0, 0, 0)))
                        .with(new CollidableComponent(true))
                        .with(SPI.createComponent(null))
                        .view(texture)
                        .buildAndAttach());

                Entity bullet = objectPool.getEntityFromPool(EntityType.BULLET.toString());
                for (Component comp : bullet.getComponents()) {
                    if (comp instanceof CommonBullet) {
                        ((CommonBullet) comp).setTarget(enemy);
                        bullet.setPosition(getEntity().getPosition().add(
                            new Point2D(
                                    Math.cos(Math.toRadians(getEntity().getRotation()) * getEntity().getWidth()) + getEntity().getWidth()/2,
                                    Math.sin(Math.toRadians(getEntity().getRotation()) * getEntity().getHeight()) + getEntity().getHeight()/2
                            )
                        ));
                    }
                }
            });
        }

        shootTimer.capture();
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

// Some UI stuff trying to show the targeting state of the tower.

//    private class InfoBox {
//        private TowerState targeting;
//
//        public InfoBox(TowerState targeting) {
//            this.targeting = targeting;
//            FXGL.addUINode(new Rectangle(200, 100, Color.WHITE).setOnMouseMoved(event -> {
//                FXGL.getGameScene().removeUINode(event.getSource());
//            }), 100, 100);
//            FXGL.removeUINode();
//            getEntity().getViewComponent().addChild(new Rectangle(20, 10, Color.WHITE));
//            getEntity().getViewComponent().addChild(new Text("Targeting state: " + targeting.toString()));
//        }
//    }
}
