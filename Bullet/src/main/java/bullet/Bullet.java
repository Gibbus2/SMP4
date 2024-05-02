package bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import common.bullet.BulletSPI;
import common.bullet.CommonBullet;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.io.InputStream;

public class Bullet extends CommonBullet implements BulletSPI {

    public Bullet(Entity target) {
        super(target);
    }

    @Override
    public Component createComponent(Entity target) {
        return new Bullet(target);
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public Image getImage() {
        InputStream is = Bullet.class.getResourceAsStream("/Bullet.png");
        return new Image(is);
    }
}
