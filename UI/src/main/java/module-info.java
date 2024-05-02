import common.player.PlayerSPI;

open module UI {
    requires com.almasb.fxgl.all;
    requires CommonPlayer;
    uses PlayerSPI;
    exports ui;
}
