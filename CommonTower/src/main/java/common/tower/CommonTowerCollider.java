package common.tower;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class CommonTowerCollider extends Component {
    CommonTowerComponent tower;

    public CommonTowerCollider(CommonTowerComponent tower) {
        this.tower = tower;
    }

    public void removeEnemy(Entity a) {
        tower.removeEnemy(a);
    }

    public void addEnemy(Entity a) {
        tower.addEnemy(a);
    }
}
