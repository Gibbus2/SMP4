package bullet.data;
import bullet.services.CommonBulletComponentSPI;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;



public class CommonBulletComponent extends Component implements CommonBulletComponentSPI {
    public ImageView image = FXGL.getAssetLoader().loadTexture("");
    private int damage;
    private int speed = 1000;
    private com.almasb.fxgl.entity.Entity target;

    public CommonBulletComponent(int damage, Entity target) {
        this.damage = damage;
        this.target = target;
    }
    public CommonBulletComponent(){
        this(0, null);
    }

    @Override
    public CommonBulletComponent createBulletComponent(int damage, Entity target) {
        return new CommonBulletComponent(damage, target);
    }
    @Override
    public void onUpdate(double tpf) {
        entity.translateTowards(target.getCenter(), speed * tpf);
    }


    @Override
    public ImageView getImage() {
        return image;
    }
}
