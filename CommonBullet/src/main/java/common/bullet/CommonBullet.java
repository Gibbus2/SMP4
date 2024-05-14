package common.bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import enemy.CommonEnemyComponent;
import health.HealthComponent;
import objectPool.PooledObjectComponent;

public class CommonBullet extends Component {
    protected double speed;
    protected int damage;
    protected Entity target;

    public CommonBullet(Entity target) {
        this.speed = 400;
        this.damage = 2;
        this.target = target;
    }

    @Override
    public void onUpdate(double tpf) {
        if (target != null) {
            getEntity().rotateToVector(target.getPosition().subtract(entity.getPosition()));
            getEntity().translateTowards(target.getPosition(), speed * tpf);

            for (Component comp : target.getComponents()) {
                if (comp instanceof HealthComponent) {
                    if (((HealthComponent) comp).isDead()) {
                        target = null;
                    }
                }
            }
        } else {
            if (getEntity().hasComponent(PooledObjectComponent.class)) {
                getEntity().getComponent(PooledObjectComponent.class).returnToPool();
            } else {
                getEntity().removeFromWorld();
            }
        }
    }

    public int getDamage() {
        return damage;
    }
    public void setTarget(Entity target) {
        this.target = target;
    }
}
