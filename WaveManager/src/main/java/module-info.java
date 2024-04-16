import common.services.EnemyComponentSPI;
import common.services.PMSComponentSPI;

module WaveManager {
    uses EnemyComponentSPI;
    uses PMSComponentSPI;
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