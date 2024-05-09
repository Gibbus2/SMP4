import common.player.PlayerSPI;

open module UI {
    requires com.almasb.fxgl.all;
    requires CommonPlayer;
    requires Common;
    uses PlayerSPI;
    exports ui;
}
