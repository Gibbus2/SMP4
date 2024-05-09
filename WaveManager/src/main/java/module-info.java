import enemy.EnemySPI;

module WaveManager {
    uses EnemySPI;
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    requires Map;
    requires ObjectPool;
    exports WaveManager;
}