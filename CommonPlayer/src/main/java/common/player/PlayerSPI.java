package common.player;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public interface PlayerSPI {
    Component createComponent();
    void changeHealth(int amount);
    void changeLedger(int amount);
    int getLedger();
    Entity getPlayerEntity();
}