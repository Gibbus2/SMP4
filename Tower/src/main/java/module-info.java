import tower.services.TowerComponentSPI;
import tower.data.TowerComponent;

module Tower {
    requires Common;
    requires com.almasb.fxgl.all;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    provides TowerComponentSPI with TowerComponent;

    exports tower.data;
}