import enemy.services.EnemyComponentSPI;

module WaveManager {
    uses EnemyComponentSPI;
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    requires Map;
    exports WaveManager.data;
    exports WaveManager.services;
    //ask if this is correct
}