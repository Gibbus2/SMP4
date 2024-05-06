package common.player;

import com.almasb.fxgl.core.collection.PropertyChangeListener;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public interface PlayerSPI {
    Component createComponent();
    void changeHealth(int amount);
    int getHealth();
    void setMoney(int amount);
    int getMoney();
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    Entity getPlayerEntity();
}