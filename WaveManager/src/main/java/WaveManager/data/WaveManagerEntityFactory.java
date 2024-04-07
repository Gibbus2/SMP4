package WaveManager.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WaveManagerEntityFactory implements EntityFactory {

    @Spawns("normalEnemy")
    public Entity normalEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.normalEnemy)
                .viewWithBBox(new Rectangle(20,20, Color.BLACK))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("mediumEnemy")
    public Entity mediumEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.mediumEnemy)
                .viewWithBBox(new Rectangle(30,30, Color.RED))
                .with(new CollidableComponent(true))
                .build();
    }
}
