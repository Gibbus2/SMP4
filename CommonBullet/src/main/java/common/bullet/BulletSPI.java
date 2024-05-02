package common.bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

public interface BulletSPI {
    CommonBullet createComponent(double speed, int damage, Entity target, IObjectPool objectPool, String objectPoolName);
    Image getImage();
}
