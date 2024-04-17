package player;

import com.almasb.fxgl.entity.component.Component;
import health.HealthComponent;

public class PlayerComponent extends Component implements IPlayerComponentSPI /*IDamageable*/ {

    private int ledger;

    @Override
    public void onAdded() {
        ledger = 0;
    }

    public void damage(int amount) {
        System.out.println("Player health change: " + getEntity().getComponent(HealthComponent.class).getHealth() + "->" +  (getEntity().getComponent(HealthComponent.class).getHealth()-1));
        getEntity().getComponent(HealthComponent.class).setHealth(-amount);

        if (getEntity().getComponent(HealthComponent.class).getHealth() >= 0) {
            getEntity().removeFromWorld();

            System.out.println("Player died.");
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
