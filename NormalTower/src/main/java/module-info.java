import common.tower.TowerSPI;
import tower.normal.NormalTower;

module NormalTower {
    requires CommonTower;
    requires com.almasb.fxgl.all;
    requires ObjectPool;

    provides TowerSPI with NormalTower;

}