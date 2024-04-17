import common.services.EnemyComponentSPI;
import common.services.PMSComponentSPI;
import enemy.data.EnemyComponent;
import enemy.data.PointMovementSystemComponent;

module Enemy {
    requires Common;
    requires com.almasb.fxgl.entity;
    requires com.almasb.fxgl.all;
    requires Map;
    exports enemy.data;
    provides EnemyComponentSPI with EnemyComponent;
    provides PMSComponentSPI with PointMovementSystemComponent;
    //requires Map;
}