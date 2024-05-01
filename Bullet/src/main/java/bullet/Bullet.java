package bullet;

import com.almasb.fxgl.entity.Entity;
import common.bullet.BulletSPI;
import common.bullet.CommonBullet;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.io.InputStream;

public class Bullet implements BulletSPI {
    @Override
    public CommonBullet createComponent(double speed, int damage, Entity target, IObjectPool objectPool, String objectPoolName) {
        return new BulletComponent(500.0, 34, target, objectPool, objectPoolName);
    }

    @Override
    public Image getImage() {
        InputStream is = Bullet.class.getResourceAsStream("/Bullet.png");
        return new Image(is);
    }
}
