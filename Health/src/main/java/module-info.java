import health.*;

module Health {
    exports health;
    requires com.almasb.fxgl.all;
    provides IHealthComponentSPI with HealthComponent;
}