package map;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class MapFactory implements EntityFactory {

    private final List<Rectangle> rectangles = new ArrayList<>();

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    @Spawns("waypoint")
    public Entity newWaypoint(SpawnData data) {
        // System.out.println("here:" + entityBuilder(data).type(EntityType.WAYPOINT));
       // System.out.println("DATA HERE: " + data.getData().get("polyline"));
        Polyline polyline = data.get("polyline");
       // System.out.println("POLYLINE: " + polyline.getPoints().toArray());
        return entityBuilder(data)
                .type(EntityType.WAYPOINT)
                .build();
    }

    List<Entity> getWaypoints() {
        return FXGL.getGameWorld().getEntitiesByType(EntityType.WAYPOINT);
    }

    @Spawns("nobuild")
    public Entity noBuild(SpawnData data) {
        int width = data.get("width");
        int height = data.get("height");

        Rectangle rectangle = new Rectangle(data.getX(), data.getY(), width, height);
        rectangles.add(rectangle);

        return entityBuilder(data)
                .type(EntityType.NO_BUILD_ZONE)
                .with(new CollidableComponent(true))
                .build();
    }
}
