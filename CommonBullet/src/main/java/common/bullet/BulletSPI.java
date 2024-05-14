package common.bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

public interface BulletSPI {
    Component createComponent(Entity target);
    int getDamage();
    Image getImage();
}
