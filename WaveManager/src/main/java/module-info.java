import common.services.EnemyComponentSPI;

module WaveManager {
    uses EnemyComponentSPI;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    requires com.almasb.fxgl.all;
    requires Enemy;
    requires Common;
    requires Map;
    exports WaveManager.data;
    exports WaveManager.services;
    //ask if this is correct
}