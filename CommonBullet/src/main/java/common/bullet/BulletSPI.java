package common.bullet;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public interface BulletSPI {
    Component createComponent();
}
