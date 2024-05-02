package bullet;

import com.almasb.fxgl.entity.Entity;
import common.bullet.CommonBullet;
import objectPool.IObjectPool;

public class BulletComponent extends CommonBullet {
    public BulletComponent(Entity target) {
        super(target);
        this.speed = 1;
        this.damage = 2;
    }
}
