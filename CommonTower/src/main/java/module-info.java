module CommonTower {
    uses common.bullet.BulletSPI;
    requires com.almasb.fxgl.all;
    requires Common;
    requires Bullet;
    requires CommonEnemy;
    requires Health;
    requires CommonBullet;
    requires ObjectPool;
    exports common.tower;
}