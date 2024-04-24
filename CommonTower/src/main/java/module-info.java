import tower.services.TowerComponentSPI;
import tower.data.CommonTowerComponent;

module CommonTower {
    requires Common;
    requires com.almasb.fxgl.all;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    requires CommonEnemy;
    provides TowerComponentSPI with CommonTowerComponent;

    exports tower.data;
}