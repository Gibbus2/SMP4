package common.bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class CommonBullet extends Component {
    protected double speed;
    protected int damage;
    protected Entity target;

    public CommonBullet(Entity target) {
        this.speed = 10;
        this.damage = 1;
        this.target = target;
    }

    @Override
    public void onUpdate(double tpf) {
        if (target != null) {
            getEntity().rotateToVector(target.getPosition().subtract(entity.getPosition()));
            getEntity().translateTowards(target.getPosition(), speed * tpf);
        }
    }

    public int getDamage() {
        return damage;
    }
    public void setTarget(Entity target) {
        this.target = target;
    }
}
