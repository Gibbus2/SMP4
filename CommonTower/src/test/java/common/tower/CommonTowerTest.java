package common.tower;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import common.data.GameData;
import enemy.CommonEnemyComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CommonTowerTest {
    GameData gameData = new GameData();
    @Test
    public void testCreateComponent() {
        CommonTowerComponent component = new CommonTowerComponent(null, gameData);
        Assertions.assertNotNull(component.createComponent(null, gameData));
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

        int startHealth = enemies.get(0).getComponent(CommonEnemyComponent.class).getHealth();

        enemies.get(1).getComponent(CommonEnemyComponent.class).damage(10, false);

        CommonTowerComponent component = new CommonTowerComponent(null, gameData);

        List<Entity> sortedList = component.sortByHealth(enemies);

        Assertions.assertEquals(startHealth - 10, sortedList.getFirst().getComponent(CommonEnemyComponent.class).getHealth());
        Assertions.assertEquals(startHealth, sortedList.getLast().getComponent(CommonEnemyComponent.class).getHealth());
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

        CommonTowerComponent component = new CommonTowerComponent(null, gameData);

        List<Entity> sortedList = component.sortByDistanceTraveled(enemies);

        Assertions.assertEquals(sortedList.getFirst().getComponent(CommonEnemyComponent.class).getDistanceTravelled(), 0);
        Assertions.assertEquals(sortedList.getLast().getComponent(CommonEnemyComponent.class).getDistanceTravelled(), 10);
    }

}
