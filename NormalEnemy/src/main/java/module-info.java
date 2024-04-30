import enemy.EnemyComponentSPI;
import normalEnemy.NormalEnemy;

module NormalEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    requires ObjectPool;
    requires Health;
    provides EnemyComponentSPI with NormalEnemy;
}