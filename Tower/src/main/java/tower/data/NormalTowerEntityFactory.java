package tower.data;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import tower.services.TowerComponentSPI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class NormalTowerEntityFactory implements EntityFactory {
    @Spawns("Tower")
    public Entity Tower(SpawnData data) {
        Image image = new Image("tower.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        EntityBuilder entityBuilder = FXGL.entityBuilder(data)
                .type(EntityType.TOWER)
                .viewWithBBox(imageView)
                .with(new CollidableComponent(true))
                .with(new StateComponent());

        for(TowerComponentSPI towerComponent : getTowerComponentSPIs())

        return entityBuilder.build();
    }
    private Collection<? extends TowerComponentSPI> getEnemyComponentSPIs() {
        return ServiceLoader.load(TowerComponentSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
