package tower.data;

import bullet.services.CommonBulletComponentSPI;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import enemy.data.EnemyComponent;
import enemy.services.EnemyComponentSPI;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import tower.services.TowerComponentSPI;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

import static com.almasb.fxgl.dsl.FXGL.*;
import static java.util.stream.Collectors.toList;

public class CommonTowerComponent extends Component implements TowerComponentSPI {
    private int towerDamage;
    private int towerPrice;
    private double towerFirerate;
    private int towerRange;
    private int towerX;
    private int towerY;
    private String towerTarget = "";
    public List<Entity> enemiesInRange;
    private LocalTimer shootTimer;
    private StateComponent state;
    public ImageView image = FXGL.getAssetLoader().loadTexture("");


    public CommonTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY, String towerTarget) {
        this.towerDamage = towerDamage;
        this.towerPrice = towerPrice;
        this.towerFirerate = towerFirerate;
        this.towerRange = towerRange;
        this.towerTarget = towerTarget;
        this.towerX = towerX;
        this.towerY = towerY;
    }

    public CommonTowerComponent() {
        this(0, 0, 0, 0, 0, 0, "");
    }

    public void updateEnemiesInRange() {
        Circle range = new Circle(towerX - (double) towerRange / 2, towerY - (double) towerRange / 2, towerRange);

        enemiesInRange = getGameWorld().getEntitiesByType(EntityType.ENEMY).stream()
                .filter(enemy -> range.contains(enemy.getPosition()))
                .collect(Collectors.toList());
    }

    public void sortByDistanceTraveled(){
        enemiesInRange.sort(Comparator.comparing(enemy -> enemy.getComponent(EnemyComponent.class).getDistanceTraveled()));
    }
    public void sortByHealth() {
        enemiesInRange.sort(Comparator.comparing(enemy -> enemy.getComponent(EnemyComponent.class).getHp()));
    }
    @Override
    public void onAdded() {
        state = entity.getComponent(StateComponent.class);
        state.changeState(IDLE);

        shootTimer = newLocalTimer();
        shootTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        //updateEnemiesInRange();
        //sortByDistanceTraveled();

        if (enemiesInRange != null && !enemiesInRange.isEmpty() && towerTarget == "First") {
            state.changeState(FIRSTTARGET);
        } else if (enemiesInRange != null && !enemiesInRange.isEmpty() && towerTarget == "Last") {
            state.changeState(LASTTARGET);
        } else if (enemiesInRange != null && !enemiesInRange.isEmpty() && towerTarget == "Strong") {
            state.changeState(STRONGTARGET);
        } else if (enemiesInRange != null && !enemiesInRange.isEmpty() && towerTarget == "Weak") {
            state.changeState(WEAKTARGET);
        } else {
            state.changeState(IDLE);
        }
    }

    private void shoot(Entity enemy) {
        Point2D direction = enemy.getPosition().subtract(entity.getPosition());
        entity.rotateToVector(direction);

        EntityBuilder bullet = FXGL.entityBuilder()
                .type(EntityType.BULLET)
                .at(entity.getPosition());
        for (CommonBulletComponentSPI bulletComponent : getBulletComponentSPIs()) {
            bullet.with((Component) bulletComponent);
        }
    }


    private final EntityState FIRSTTARGET = new EntityState("FIRSTTARGET") {
        @Override
        public void onEntering() {
            updateEnemiesInRange();
            sortByDistanceTraveled();
        }
        @Override
        protected void onUpdate(double tpf) {
            if (shootTimer.elapsed(Duration.seconds(towerFirerate))) {
                updateEnemiesInRange();
                sortByDistanceTraveled();
                Entity enemy = enemiesInRange.getLast();
                shoot(enemy);
            }
        }
    };

    private final EntityState LASTTARGET = new EntityState("LASTTARGET") {
        @Override
        public void onEntering() {
            updateEnemiesInRange();
            sortByDistanceTraveled();
        }
        @Override
        protected void onUpdate(double tpf) {
            if (shootTimer.elapsed(Duration.seconds(towerFirerate))) {
                updateEnemiesInRange();
                sortByDistanceTraveled();
                Entity enemy = enemiesInRange.getFirst();
                shoot(enemy);
            }
        }
    };

    private final EntityState STRONGTARGET = new EntityState("STRONGTARGET") {
        @Override
        public void onEntering() {
            updateEnemiesInRange();
            sortByHealth();
        }
        @Override
        protected void onUpdate(double tpf) {
            if (shootTimer.elapsed(Duration.seconds(towerFirerate))) {
                updateEnemiesInRange();
                sortByHealth();
                Entity enemy = enemiesInRange.getLast();
                shoot(enemy);
            }
        }
    };

    private final EntityState WEAKTARGET = new EntityState("WEAKTARGET") {
        @Override
        public void onEntering() {
            updateEnemiesInRange();
            sortByHealth();
        }
        @Override
        protected void onUpdate(double tpf) {
            if (shootTimer.elapsed(Duration.seconds(towerFirerate))) {
                updateEnemiesInRange();
                sortByHealth();
                Entity enemy = enemiesInRange.getFirst();
                shoot(enemy);
            }
        }
    };

    private final EntityState IDLE = new EntityState("IDlE") {
        @Override
        public void onEntering() {
            updateEnemiesInRange();
        }
        @Override
        protected void onUpdate(double tpf) {
            updateEnemiesInRange();
        }
    };
    @Override
    public TowerComponentSPI createTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY) {
        return new CommonTowerComponent(towerDamage, towerPrice, towerFirerate, towerRange, towerX, towerY, towerTarget);
    }
    public int getTowerDamage() {
        return towerDamage;
    }
    public int getTowerPricePrice() {
        return towerPrice;
    }
    public double getTowerFirerate() {
        return towerFirerate;
    }
    public int getTowerRange() {
        return towerRange;
    }
    public int getTowerX() {
        return towerX;
    }
    public int getTowerY() {
        return towerY;
    }
    public void setTowerX(int towerX) {
        this.towerX = towerX;
    }
    public void setTowerY(int towerY) {
        this.towerY = towerY;
    }
    @Override
    public ImageView getImage() {
        return image;
    }
    private Collection<? extends CommonBulletComponentSPI> getBulletComponentSPIs() {
        return ServiceLoader.load(CommonBulletComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends EnemyComponentSPI> getEnemyComponentSPIs() {
        return ServiceLoader.load(EnemyComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
