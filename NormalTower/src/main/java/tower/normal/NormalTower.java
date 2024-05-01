package tower.normal;

import com.almasb.fxgl.entity.component.Component;
import common.tower.CommonTowerComponent;
import common.tower.TowerSPI;

public class NormalTower extends CommonTowerComponent implements TowerSPI {
    @Override
    public Component createComponent() {
        return null;
    }

    @Override
    public int getCost() {
        return super.getPrice();
    }

    @Override
    public String getName() {
        return "Normal Tower";
    }
}
