package common.tower;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import enemy.Enemy;
import objectPool.IObjectPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommonTowerTest {
    @Test
    public void testCreateComponent() {
        CommonTowerComponent component = new CommonTowerComponent(null);
        Assertions.assertNotNull(component.createComponent(null));
    }

    @Test
    public void sortByHealthTest() {
        List<Entity> enemies = new ArrayList<>();
        enemies.add(
                FXGL.entityBuilder()
                        .with(new Enemy(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new Enemy(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new Enemy(null))
                        .build()
        );

        enemies.get(1).getComponent(Enemy.class).damage(10, false);

        CommonTowerComponent component = new CommonTowerComponent(null);

        List<Entity> sortedList = component.sortByHealth(enemies);

        Assertions.assertEquals(sortedList.getFirst().getComponent(Enemy.class).getHealth(), 90);
        Assertions.assertEquals(sortedList.getLast().getComponent(Enemy.class).getHealth(), 100);
    }

    @Test
    public void sortByDistanceTravelled() {
        List<Entity> enemies = new ArrayList<>();
        enemies.add(
                FXGL.entityBuilder()
                        .with(new Enemy(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new Enemy(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new Enemy(null))
                        .build()
        );

        enemies.get(1).getComponent(Enemy.class).setDistanceTravelled(10);

        CommonTowerComponent component = new CommonTowerComponent(null);

        List<Entity> sortedList = component.sortByDistanceTraveled(enemies);

        Assertions.assertEquals(sortedList.getFirst().getComponent(Enemy.class).getDistanceTravelled(), 0);
        Assertions.assertEquals(sortedList.getLast().getComponent(Enemy.class).getDistanceTravelled(), 10);
    }

}
