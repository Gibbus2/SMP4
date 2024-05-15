import common.player.PlayerSPI;

module CommonEnemy {
    requires Common;
    requires Health;
    requires com.almasb.fxgl.all;
    requires ObjectPool;
    requires CommonPlayer;
    exports enemy;

    uses PlayerSPI;
}