package bullet.services;

import bullet.data.CommonBulletComponent;
import com.almasb.fxgl.entity.Entity;


public interface CommonBulletComponentSPI {
    CommonBulletComponent createBulletComponent(int damage,Entity target);
    javafx.scene.image.ImageView getImage();

}
