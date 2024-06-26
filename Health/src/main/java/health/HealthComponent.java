package health;

import com.almasb.fxgl.entity.component.Component;

public class HealthComponent extends Component implements IHealthComponentSPI {
    private int health;

    public HealthComponent() {
        this(100);
    }

    public HealthComponent(int health) {
        this.health = health;
    }

    @Override
    public HealthComponent createHealthComponent(int amount) {
        return new HealthComponent(amount);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int amount) {
        health = amount;
    }

    public void changeHealth(int amount) {
        health += amount;
    }

    public boolean isDead() {
        return health <= 0;
    }

}
