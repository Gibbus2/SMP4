package player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import common.player.PlayerSPI;
import health.HealthComponent;
import javafx.scene.input.KeyCode;

public class PlayerComponent extends Component implements PlayerSPI {
    private static Entity player;
    private int ledger;

    public Entity getPlayerEntity() {
        return player;
    }

    @Override
    public void onAdded() {
        ledger = 0;
        player = getEntity();

        System.out.println("PlayerComponent added -> entity.");
        getPlayerEntity().addComponent(new HealthComponent(100));
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
        getPlayerEntity().getComponent(HealthComponent.class).setHealth(amount);
        System.out.println((getPlayerEntity().getComponent(HealthComponent.class).getHealth()));

        if (getPlayerEntity().getComponent(HealthComponent.class).isDead()) {
            getPlayerEntity().removeFromWorld();

            System.out.println("Player died.");
            // TODO: End game.
        }
    }

    @Override
    public void changeLedger(int amount) {
        System.out.print("Player ledger change: " + getLedger() + "->");
        this.ledger += amount;
        System.out.println(getLedger());
    }

    @Override
    public int getLedger() {
        return ledger;
    }
}
