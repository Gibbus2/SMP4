import common.tower.TowerSPI;
import tower.fast.FastTower;

module FastTower {
    requires CommonTower;
    requires com.almasb.fxgl.all;
    requires ObjectPool;
    requires Common;

    provides TowerSPI with FastTower;

}