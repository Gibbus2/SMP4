import bullet.data.CommonBulletComponent;
import bullet.services.CommonBulletComponentSPI;

module CommonBullet {
    requires Common;
    requires com.almasb.fxgl.all;
    exports bullet.data;
    exports bullet.services;
    provides CommonBulletComponentSPI with CommonBulletComponent;
}