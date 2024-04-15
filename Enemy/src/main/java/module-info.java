import common.services.EnemyComponentSPI;
import enemy.data.EnemyComponent;

module Enemy {
    requires Common;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.all;
    requires Map;
    exports enemy.data;
    provides EnemyComponentSPI with EnemyComponent;
    //requires Map;
}