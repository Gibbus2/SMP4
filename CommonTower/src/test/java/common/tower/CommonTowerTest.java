package common.tower;


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
        List<Enemy> enemies = new ArrayList<>();

    }

}
