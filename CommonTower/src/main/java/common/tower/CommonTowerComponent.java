package common.tower;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.time.LocalTimer;
import common.data.EntityType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;


public class CommonTowerComponent extends Component /*implements TowerComponentSPI*/ {
    private int damage;
    private int cost;
    private double firerate;
    private int range;
    private Targetting targetting;

    public List<Entity> enemiesInRange;

    private LocalTimer shootTimer;

    private StateComponent state;

    private CollisionHandler collisionHandler;

    public CommonTowerComponent() {

    }

    public CommonTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY, String towerTarget) {
        this.damage = towerDamage;
        this.cost = towerPrice;
        this.firerate = towerFirerate;
        this.range = towerRange;
        this.targetting = Targetting.FIRST;

        collisionHandler = new CollisionHandler(EntityType.ENEMY, EntityType.TOWER) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                enemiesInRange.add(a);
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                enemiesInRange.remove(a);
            }
        };

    }


   /* public void sortByDistanceTraveled() {
        enemiesInRange.sort(Comparator.comparing(enemy -> enemy.getComponent(EnemyComponent.class).getDistanceTraveled()));
    }

    public void sortByHealth() {
        enemiesInRange.sort(Comparator.comparing(enemy -> enemy.getComponent(EnemyComponent.class).getHealth()));
    } */
//
//    @Override
//    public void onAdded() {
//        state = entity.getComponent(StateComponent.class);
//        state.changeState(IDLE);
//
//        getEntity().getComponent(BoundingBoxComponent.class).addHitBox(new HitBox(BoundingShape.circle(range)));
//        getEntity().getComponent(ViewComponent.class).addChild(new Circle(range, Color.BLUE));
//
//        // TODO: Huge red flag. Might be a bug when trying to build.
//        FXGL.getPhysicsWorld().addCollisionHandler(collisionHandler);
//
//        shootTimer = FXGL.newLocalTimer();
//        shootTimer.capture();
//    }
//
//    @Override
//    public void onRemoved() {
//        FXGL.getPhysicsWorld().removeCollisionHandler(collisionHandler);
//    }
//
//    @Override
//    public void onUpdate(double tpf) {
//        if (enemiesInRange != null && !enemiesInRange.isEmpty()) {
//            switch (targetting) {
//                case FIRST:
//                    state.changeState(FIRSTTARGET);
//                    break;
//                case LAST:
//                    state.changeState(LASTTARGET);
//                    break;
//                case STRONGEST:
//                    state.changeState(STRONGTARGET);
//                    break;
//                case WEAKEST:
//                    state.changeState(WEAKTARGET);
//                    break;
//                default:
//                    state.changeState(IDLE);
//                    break;
//            }
//        }
//    }
//
//    private void shoot(Entity enemy) {
//        Point2D direction = enemy.getPosition().subtract(entity.getPosition());
//        entity.rotateToVector(direction);
//
//        // TODO: ObjectPool this.
////        Entity bullet = FXGL.entityBuilder()
////                .type(EntityType.BULLET)
////                .at(entity.getPosition())
////                .with(new Bullet())
////                .buildAndAttach();
//
////        Bullet Bullet = bullet.getComponent(Bullet.class);
////        Bullet.setDirection(direction);
//    }
//
//
//    private final EntityState FIRSTTARGET = new EntityState("FIRSTTARGET") {
//        @Override
//        protected void onUpdate(double tpf) {
//        //  TODO: PriorityQueue based on distance traveled from enemiesInRange.
//
//        //  sortByDistanceTraveled();
//            if (shootTimer.elapsed(Duration.seconds(firerate))) {
////                shoot(); // TODO: Implement shoot method.
//            }
//        }
//    };
//
//
//    private final EntityState LASTTARGET = new EntityState("LASTTARGET") {
//        @Override
//        public void onEntering() {
//            //sortByDistanceTraveled().reversed();
//        }
//        @Override
//        protected void onUpdate(double tpf) {
//            if (shootTimer.elapsed(Duration.seconds(firerate))) {
//                updateEnemiesInRange();
////                shoot();
//            }
//        }
//    };
//
//    private final EntityState STRONGTARGET = new EntityState("STRONGTARGET") {
//        @Override
//        public void onEntering() {
//            //sortByHealth().reversed();
//        }
//        @Override
//        protected void onUpdate(double tpf) {
//            if (shootTimer.elapsed(Duration.seconds(firerate))) {
//                updateEnemiesInRange();
////                shoot();
//            }
//        }
//    };
//
//    private final EntityState WEAKTARGET = new EntityState("WEAKTARGET") {
//        @Override
//        public void onEntering() {
//            //sortByHealth();
//        }
//        @Override
//        protected void onUpdate(double tpf) {
//            if (shootTimer.elapsed(Duration.seconds(firerate))) {
//                updateEnemiesInRange();
////                shoot();
//            }
//        }
//    };
//
//    private final EntityState IDLE = new EntityState("IDlE") {
//        @Override
//        public void onEntering() {
//
//        }
//        @Override
//        protected void onUpdate(double tpf) {
//
//        }
//
//    };
//
//
//
//    public int getDamage(){
//        return damage;
//    }
    public int getPrice(){
        return cost;
    }
//    public double getFirerate(){
//        return firerate;
//    }
//    public int getRange(){
//        return range;
//    }
//    public int getX(){
//        return towerX;
//    }
//    public int getY(){
//        return towerY;
//    }
}
