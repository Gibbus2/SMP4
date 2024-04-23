import enemy.services.EnemyComponentSPI;
import enemy.data.EnemyComponent;

module CommonEnemy {
    requires Common;
    requires com.almasb.fxgl.all;
    requires Map;
    exports enemy.data;
    exports enemy.services;
    provides EnemyComponentSPI with EnemyComponent;
}