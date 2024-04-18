module NormalEnemy {
    uses Enemy.services.EnemyComponentSPI;
    uses Enemy.services.PMSComponentSPI;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.core;
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    requires Map;
    //ask if this is correct
}