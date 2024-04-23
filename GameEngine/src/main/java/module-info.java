import objectPool.IObjectPool;

open module GameEngine {
    uses IObjectPool;
    requires com.almasb.fxgl.all;
    requires Common;
    requires WaveManager;
    requires Map;
    requires Player;
    requires Health;
    requires ObjectPool;
    requires UI;
    requires annotations;
    requires CommonEnemy;
}