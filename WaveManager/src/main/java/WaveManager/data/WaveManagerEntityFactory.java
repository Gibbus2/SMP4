package WaveManager.data;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import common.services.EnemyComponentSPI;
import common.services.PMSComponentSPI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

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
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.normalEnemy)
                .viewWithBBox(new Rectangle(20,20, getRandomColor()))
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(10, 1, 100, 10));
        }
        for(PMSComponentSPI PMSComponent : getPMSComponentSPIs()){
            entityBuilder.with((Component) PMSComponent.createPMSComponent());
        }
        return entityBuilder.build();
    }
    @Spawns("mediumEnemy")
    public Entity mediumEnemy(SpawnData data) {
        Image image = FXGL.image("test1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.mediumEnemy)
                .viewWithBBox(imageView)
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(10, 1, 10, 10));
        }
        for(PMSComponentSPI PMSComponent : getPMSComponentSPIs()){
            entityBuilder.with((Component) PMSComponent.createPMSComponent());
        }
        return entityBuilder.build();
    }
    @Spawns("hardEnemy")
    public Entity hardEnemy(SpawnData data) {
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.hardEnemy)
                .viewWithBBox(new Rectangle(40,40,Color.RED))
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(10, 1, 10, 10));
        }
        for(PMSComponentSPI PMSComponent : getPMSComponentSPIs()){
            entityBuilder.with((Component) PMSComponent.createPMSComponent());
        }
        return entityBuilder.build();
    }
    @Spawns("bossEnemy")
    public Entity bossEnemy(SpawnData data) {
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.bossEnemy)
                .viewWithBBox(new Rectangle(50,50,Color.CRIMSON))
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(10, 1, 10, 10));
        }
        for(PMSComponentSPI PMSComponent : getPMSComponentSPIs()){
            entityBuilder.with((Component) PMSComponent.createPMSComponent());
        }

        return entityBuilder.build();
    }

    private Collection<? extends EnemyComponentSPI> getEnemyComponentSPIs() {
        return ServiceLoader.load(EnemyComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends PMSComponentSPI> getPMSComponentSPIs() {
        return ServiceLoader.load(PMSComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
