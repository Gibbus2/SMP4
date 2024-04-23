module NormalEnemy {
    uses enemy.services.EnemyComponentSPI;
    uses enemy.services.PMSComponentSPI;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    requires Map;
    //ask if this is correct
}