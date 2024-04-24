import tower.services.TowerComponentSPI;
import tower.data.CommonTowerComponent;

module CommonTower {
    uses bullet.services.CommonBulletComponentSPI;
    uses enemy.services.EnemyComponentSPI;
    requires Common;
    requires com.almasb.fxgl.all;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    requires CommonEnemy;
    requires CommonBullet;
    provides TowerComponentSPI with CommonTowerComponent;

    exports tower.data;
}