module Bullet {
    requires Common;
    requires CommonBullet;
    requires ObjectPool;
    requires com.almasb.fxgl.all;
    exports bullet;
    provides common.bullet.BulletSPI with bullet.BulletComponent;
}