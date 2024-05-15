package common.tower;

import com.almasb.fxgl.entity.component.Component;
import common.data.GameData;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

public interface TowerSPI {
    Component createComponent(IObjectPool objectPool, GameData gameData);
    int getCost();
    String getName();

    Image getImage();




//    CommonTowerComponent createTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY);
}
