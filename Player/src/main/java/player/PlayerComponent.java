package player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import common.player.PlayerComponentSPI;
import health.HealthComponent;
import javafx.scene.input.KeyCode;

public class PlayerComponent extends Component implements PlayerComponentSPI /*IDamageable*/ {
    private static Entity player;
    private int ledger;

    public Entity getPlayerEntity() {
        return player;
    }

    @Override
    public void onAdded() {
        ledger = 0;
        player = getEntity();

        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayerEntity().translateX(5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                getPlayerEntity().translateX(-5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                getPlayerEntity().translateY(-5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                getPlayerEntity().translateY(5);
            }
        }, KeyCode.S);


        input.addAction(new UserAction("Play Sound") {
            @Override
            protected void onActionBegin() {
                FXGL.play("drop.wav");
            }
        }, KeyCode.F);

        getPlayerEntity().addComponent(new HealthComponent(100));
        System.out.println("Player added.");
    }

    @Override
    public void onRemoved() {
        Input input = FXGL.getInput();
        input.clearAll();
    }

    @Override
    public Component createComponent() {
        return new PlayerComponent();
    }

    @Override
    public void changeHealth(int amount) {
        System.out.println("Player health change: " + getPlayerEntity().getComponent(HealthComponent.class).getHealth() + "->" +  (getPlayerEntity().getComponent(HealthComponent.class).getHealth()-1));
        getPlayerEntity().getComponent(HealthComponent.class).setHealth(-amount);

        if (getPlayerEntity().getComponent(HealthComponent.class).getHealth() <= 0) {
            getPlayerEntity().removeFromWorld();

            System.out.println("Player died.");
            // TODO: End game.
        }
    }

    @Override
    public void changeLedger(int amount) {
        this.ledger += amount;
    }

    @Override
    public int getLedger() {
        return ledger;
    }
}
