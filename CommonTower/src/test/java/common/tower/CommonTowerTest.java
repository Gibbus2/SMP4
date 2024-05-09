package common.tower;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import enemy.CommonEnemyComponent;
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
                        .with(new CommonEnemyComponent(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new CommonEnemyComponent(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new CommonEnemyComponent(null))
                        .build()
        );

        enemies.get(1).getComponent(CommonEnemyComponent.class).damage(10, false);

        CommonTowerComponent component = new CommonTowerComponent(null);

        List<Entity> sortedList = component.sortByHealth(enemies);

        Assertions.assertEquals(sortedList.getFirst().getComponent(CommonEnemyComponent.class).getHealth(), 90);
        Assertions.assertEquals(sortedList.getLast().getComponent(CommonEnemyComponent.class).getHealth(), 100);
    }

    @Test
    public void sortByDistanceTravelled() {
        List<Entity> enemies = new ArrayList<>();
        enemies.add(
                FXGL.entityBuilder()
                        .with(new CommonEnemyComponent(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new CommonEnemyComponent(null))
                        .build()
        );
        enemies.add(
                FXGL.entityBuilder()
                        .with(new CommonEnemyComponent(null))
                        .build()
        );

        enemies.get(1).getComponent(CommonEnemyComponent.class).setDistanceTravelled(10);

        CommonTowerComponent component = new CommonTowerComponent(null);

        List<Entity> sortedList = component.sortByDistanceTraveled(enemies);

        Assertions.assertEquals(sortedList.getFirst().getComponent(CommonEnemyComponent.class).getDistanceTravelled(), 0);
        Assertions.assertEquals(sortedList.getLast().getComponent(CommonEnemyComponent.class).getDistanceTravelled(), 10);
    }

}
