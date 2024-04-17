import health.*;

module Health {
    exports health;
    requires com.almasb.fxgl.all;
    requires Common;
    provides IHealthComponentSPI with HealthComponent;
}