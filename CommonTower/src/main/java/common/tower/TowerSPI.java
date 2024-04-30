package common.tower;

import com.almasb.fxgl.entity.component.Component;

public interface TowerSPI {
    Component createComponent();
    int getCost();
    String getName();
//    CommonTowerComponent createTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY);
}
