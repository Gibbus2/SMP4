import common.player.PlayerSPI;

open module UI {
    requires com.almasb.fxgl.all;
    requires CommonPlayer;
    requires Common;
    requires CommonTower;

    uses PlayerSPI;
    uses common.tower.TowerSPI;

    exports ui;
}
