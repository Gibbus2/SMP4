package bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import common.bullet.BulletSPI;
import common.bullet.CommonBullet;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.io.InputStream;

public class BulletComponent extends CommonBullet implements BulletSPI {

    public BulletComponent() {
        this(null);
    }

    public BulletComponent(Entity target) {
        super(target);
    }

    @Override
    public Component createComponent(Entity target) {
        return new BulletComponent(target);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public Image getImage() {
        InputStream is = BulletComponent.class.getResourceAsStream("/Bullet24.png");
        return new Image(is);
    }
}
