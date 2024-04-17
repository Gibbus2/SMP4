package player;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public interface IPlayerComponentSPI {
    Entity getEntity();
    Component createComponent();
}
