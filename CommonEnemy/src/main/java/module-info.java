import Enemy.services.EnemyComponentSPI;
import Enemy.services.PMSComponentSPI;
import Enemy.data.EnemyComponent;
import Enemy.data.PointMovementSystemComponent;

module CommonEnemy {
    requires Common;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.all;
    requires Map;
    exports Enemy.data;
    exports Enemy.services;
    provides EnemyComponentSPI with EnemyComponent;
    provides PMSComponentSPI with PointMovementSystemComponent;
}