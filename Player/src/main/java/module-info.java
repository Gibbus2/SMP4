import common.player.PlayerComponentSPI;
import player.*;

module Player {
    exports player;
    requires com.almasb.fxgl.all;
    requires Common;
    requires Health;
    requires CommonPlayer;
    provides PlayerComponentSPI with PlayerComponent;
}