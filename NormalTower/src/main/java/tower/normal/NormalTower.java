package tower.normal;

import com.almasb.fxgl.entity.component.Component;
import common.tower.CommonTowerComponent;
import common.tower.TowerSPI;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.io.InputStream;

public class NormalTower extends CommonTowerComponent implements TowerSPI {

    public NormalTower() {
        this(null);
    }

    public NormalTower(IObjectPool objectPool) {
        super(objectPool);
        this.damage = 1;
        this.cost = 10;
        this.firerate = 1;
        this.range = 15;

    }

    @Override
    public Component createComponent(IObjectPool objectPool) {
        return new NormalTower(objectPool);
    }

    @Override
    public Image getImage(){
        InputStream is = NormalTower.class.getResourceAsStream("/tower.png");
        if (is != null) {
            return new Image(is);
        }
        return null;
    }
    @Override
    public String getName() {
        return "Normal Tower";
    }
}
