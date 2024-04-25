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
import enemy.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import common.data.EntityType;

import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WaveManagerEntityFactory implements EntityFactory {
    /*

    List<Point2D> waypoints = map.Waypoint.fromPolyline().getWaypoints();

    @Spawns("NORMAL_ENEMY")
    public Entity normalEnemy(SpawnData data) {
        Image image = FXGL.image("normalEnemy.png");
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(-24);
        imageView.setTranslateY(-24);

        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.NORMAL_ENEMY)
                .viewWithBBox(imageView)
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(waypoints));
            //kalder ikke den overridede createEnemyComponent fra normalEnemyComponent
        }

        return entityBuilder.build();
    }
    @Spawns("MEDIUM_ENEMY")
    public Entity mediumEnemy(SpawnData data) {
        Image image = FXGL.image("test1.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.MEDIUM_ENEMY)
                .viewWithBBox(imageView)
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(waypoints));
        }

        return entityBuilder.build();
    }
    @Spawns("HARD_ENEMY")
    public Entity hardEnemy(SpawnData data) {
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.HARD_ENEMY)
                .viewWithBBox(new Rectangle(40,40,Color.RED))
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(waypoints));
        }
        return entityBuilder.build();
    }
    @Spawns("BOSS_ENEMY")
    public Entity bossEnemy(SpawnData data) {
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.BOSS_ENEMY)
                .viewWithBBox(new Rectangle(50,50,Color.CRIMSON))
                .with(new CollidableComponent(true))
                //adding enemy component with hp, damage, speed, and score
                .with(new StateComponent());
        for(EnemyComponentSPI enemyComponent : getEnemyComponentSPIs()){
            entityBuilder.with((Component) enemyComponent.createEnemyComponent(waypoints));
        }

        return entityBuilder.build();
    }

    private Collection<? extends EnemyComponentSPI> getEnemyComponentSPIs() {
        return ServiceLoader.load(EnemyComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

     */



}
