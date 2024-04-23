import enemy.services.EnemyComponentSPI;
import enemy.services.PMSComponentSPI;

module WaveManager {
    uses EnemyComponentSPI;
    uses PMSComponentSPI;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    requires Map;
    exports WaveManager.data;
    exports WaveManager.services;
    //ask if this is correct
}