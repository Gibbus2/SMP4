import enemy.services.EnemyComponentSPI;
import enemy.services.PMSComponentSPI;
import enemy.data.EnemyComponent;
import enemy.data.PointMovementSystemComponent;

module CommonEnemy {
    requires Common;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.all;
    requires Map;
    exports enemy.data;
    exports enemy.services;
    provides PMSComponentSPI with PointMovementSystemComponent;
    provides EnemyComponentSPI with EnemyComponent;
}