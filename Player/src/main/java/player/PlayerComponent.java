package player;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import common.player.PlayerSPI;
import health.HealthComponent;

public class PlayerComponent extends Component implements PlayerSPI {
    private static Entity player;
    private static int ledger;

    public Entity getPlayerEntity() {
        return player;
    }

    @Override
    public void onAdded() {
        ledger = 20;
        player = getEntity();

        System.out.println("PlayerComponent added -> entity.");
        getPlayerEntity().addComponent(new HealthComponent(25));
        System.out.println("HealthComponent added -> " + PlayerComponent.class + ".");
    }

    @Override
    public Component createComponent() {
        System.out.println("PlayerComponent created.");
        return new PlayerComponent();
    }

    @Override
    public void changeHealth(int amount) {
        System.out.print("Player health change: " + getPlayerEntity().getComponent(HealthComponent.class).getHealth() + "->");
        getPlayerEntity().getComponent(HealthComponent.class).changeHealth(amount);
        System.out.println((getPlayerEntity().getComponent(HealthComponent.class).getHealth()));

        if (getPlayerEntity().getComponent(HealthComponent.class).isDead()) {
            //getPlayerEntity().removeFromWorld();

            System.out.println("Player died.");
            // TODO: End game.

        }

    }

    @Override
    public int getHealth() {
        return getPlayerEntity().getComponent(HealthComponent.class).getHealth();
    }

    @Override
    public void changeMoney(int amount) {
        System.out.print("Player ledger change: " + getMoney() + "->");
        this.ledger += amount;
        System.out.println(getMoney());
    }

    @Override
    public int getMoney() {
        return ledger;
    }
}
