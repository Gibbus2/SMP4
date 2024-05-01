package bullet;

import com.almasb.fxgl.entity.Entity;
import common.bullet.CommonBullet;
import objectPool.IObjectPool;

public class BulletComponent extends CommonBullet {
    public BulletComponent(double speed, int damage, Entity target, IObjectPool objectPool, String objectPoolName) {
        super(speed, damage, target, objectPool, objectPoolName);
    }
}
