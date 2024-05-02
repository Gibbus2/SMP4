package tower.normal;

import com.almasb.fxgl.entity.component.Component;
import common.tower.CommonTowerComponent;
import common.tower.TowerSPI;
import objectPool.IObjectPool;

public class NormalTower extends CommonTowerComponent implements TowerSPI {

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
}
