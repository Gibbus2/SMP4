package WaveManager.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WaveManagerEntityFactory implements EntityFactory {

    public Color getRandomColor(){
        int random = (int) (Math.random() * 3);
        return switch (random) {
            case 0 -> Color.RED;
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            default -> Color.BLACK;
        };
    }

    @Spawns("normalEnemy")
    public Entity normalEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.normalEnemy)
                .viewWithBBox(new Rectangle(20,20, getRandomColor()))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("mediumEnemy")
    public Entity mediumEnemy(SpawnData data) {
        Image image = FXGL.image("test1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        return FXGL.entityBuilder(data)
                .type(EntityType.mediumEnemy)
                .viewWithBBox(imageView) //test image for showing it can load it
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("hardEnemy")
    public Entity hardEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.hardEnemy)
                .viewWithBBox(new Rectangle(40,40, Color.RED))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("bossEnemy")
    public Entity bossEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.bossEnemy)
                .viewWithBBox(new Rectangle(50,50, Color.RED))
                .with(new CollidableComponent(true))
                .build();
    }
}
