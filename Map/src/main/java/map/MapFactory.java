package map;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import common.data.EntityType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class MapFactory implements EntityFactory {



    @Spawns("waypoint")
    public Entity newWaypoint(SpawnData data) {
        Polyline polyline = data.get("polyline");
        return entityBuilder(data)
                .type(EntityType.WAYPOINT)
                .build();
    }


    @Spawns("nobuild")
    public Entity noBuild(SpawnData data) {
        int width = data.get("width");
        int height = data.get("height");

        return entityBuilder()
                .type(EntityType.NO_BUILD_ZONE)
                .viewWithBBox(new Rectangle(width, height))
                .build();
    }

}
