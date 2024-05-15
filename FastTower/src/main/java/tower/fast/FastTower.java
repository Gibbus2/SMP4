package tower.fast;

import com.almasb.fxgl.entity.component.Component;
import common.data.GameData;
import common.tower.CommonTowerComponent;
import common.tower.TowerSPI;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.io.InputStream;

public class FastTower extends CommonTowerComponent implements TowerSPI {

    public FastTower() {
        this(null, null);
    }

    public FastTower(IObjectPool objectPool, GameData gameData) {
        super(objectPool, gameData);
        this.cost = 15;
        this.firerate = 0.42069;
        this.range = 10;
    }

    @Override
    public Component createComponent(IObjectPool objectPool, GameData gameData) {
        return new FastTower(objectPool, gameData);
    }

    @Override
    public Image getImage() {
        InputStream is = FastTower.class.getResourceAsStream("/fastTower48.png");
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
