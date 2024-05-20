module Bullet {
    requires CommonBullet;
    requires com.almasb.fxgl.all;
    exports bullet;
    provides common.bullet.BulletSPI with bullet.BulletComponent;
}