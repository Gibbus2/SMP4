import common.player.PlayerSPI;

module CommonEnemy {
    requires Health;
    requires com.almasb.fxgl.all;
    requires ObjectPool;
    requires CommonPlayer;
    exports enemy;

    uses PlayerSPI;
}