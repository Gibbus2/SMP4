package NormalEnemyFactory;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import enemy.services.EnemyComponentSPI;
import enemy.services.PMSComponentSPI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import common.data.EntityType;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class NormalEntity implements EntityFactory{

    @Spawns("NORMAL_ENEMY")
    public Entity normalEnemy(SpawnData data) {
        Image image = FXGL.image("normalEnemy.png");
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(-24);
        imageView.setTranslateY(-24);

        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.ENEMY)
                .viewWithBBox(imageView)
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

    private Collection<? extends EnemyComponentSPI> getEnemyComponentSPIs() {
        return ServiceLoader.load(EnemyComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends PMSComponentSPI> getPMSComponentSPIs() {
        return ServiceLoader.load(PMSComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
