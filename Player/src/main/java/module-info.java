import common.player.PlayerSPI;
import player.*;

module Player {
    exports player;
    requires com.almasb.fxgl.all;
    requires Common;
    requires Health;
    requires CommonPlayer;
    provides PlayerSPI with PlayerComponent;
}