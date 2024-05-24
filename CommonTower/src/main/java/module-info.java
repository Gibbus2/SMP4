module CommonTower {
    uses common.bullet.BulletSPI;
    requires com.almasb.fxgl.all;
    requires Common;
    requires CommonEnemy;
    requires Health;
    requires CommonBullet;
    requires ObjectPool;
    exports common.tower;
}