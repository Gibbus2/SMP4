package common.tower;

import com.almasb.fxgl.entity.component.Component;
import objectPool.IObjectPool;

public interface TowerSPI {
    Component createComponent(IObjectPool objectPool);
    int getCost();
    String getName();

//    CommonTowerComponent createTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY);
}
