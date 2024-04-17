import player.*;

module Player {
    exports player;
    requires com.almasb.fxgl.all;
    requires Common;
    requires Health;
    provides IPlayerComponentSPI with PlayerComponent;
}