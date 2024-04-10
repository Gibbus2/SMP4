package map;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MapFactory implements EntityFactory {
    public enum EntityType  {
        WAYPOINT,TOWER_BASE
    }
    @Spawns("waypoint")
    public Entity newWaypoint(SpawnData data){
        return FXGL.entityBuilder().type(EntityType.WAYPOINT).build();
    }

    @Spawns("towerBase")
    public Entity newTowerBase(SpawnData data) {
        var rect = new Rectangle(64, 64, Color.GREEN);
        rect.setOpacity(0.25);

        var cell = FXGLForKtKt.entityBuilder(data)
                .type(EntityType.TOWER_BASE)
                .viewWithBBox(rect)
                .build();

        rect.fillProperty().bind(
                Bindings.when(cell.getViewComponent().getParent().hoverProperty())
                        .then(Color.DARKGREEN)
                        .otherwise(Color.GREEN)
        );

        return cell;
    }

}
