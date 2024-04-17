package player;

import com.almasb.fxgl.entity.component.Component;
import health.HealthComponent;

public class PlayerComponent extends Component implements IPlayerComponentSPI /*IDamageable*/ {

    private HealthComponent health;
    private int ledger;

    @Override
    public void onAdded() {
        health = getEntity().getComponent(HealthComponent.class);
        ledger = 0;
    }

    public void damage(int amount) {
        health.setHealth(-amount);
        if (health.getHealth() <= 0) {
            getEntity().removeFromWorld();
            // TODO: End game.
        }
    }

    public void addCurrency(int amount) {
        ledger += amount;
    }

    public void removeCurrency(int amount) {
        ledger -= amount;
    }

    @Override
    public Component createComponent() {
        return new PlayerComponent();
    }
}
