package map;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import common.data.EntityType;
import javafx.scene.shape.Polyline;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapFactoryTest {

    @Test
    void testNewWaypoint() {
        MapFactory factory = new MapFactory();
        SpawnData data = new SpawnData(0, 0);
        data.put("polyline", new Polyline());
        Entity waypoint = factory.newWaypoint(data);
        assertEquals(EntityType.WAYPOINT, waypoint.getType());
    }

    @Test
    void testNoBuild() {
        MapFactory factory = new MapFactory();
        SpawnData data = new SpawnData(0, 0);
        data.put("width", 10);
        data.put("height", 10);
        Entity noBuild = factory.noBuild(data);
        assertEquals(EntityType.NO_BUILD_ZONE, noBuild.getType());
    }
}