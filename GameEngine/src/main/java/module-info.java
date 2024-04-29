import objectPool.IObjectPool;

open module GameEngine {
    requires com.almasb.fxgl.all;

    requires Common;
    requires CommonEnemy;

    requires CommonPlayer;
    uses common.player.PlayerComponentSPI;

    requires ObjectPool;
    uses IObjectPool;


    requires WaveManager;
    requires Map;


    requires Health;
    requires UI;
}