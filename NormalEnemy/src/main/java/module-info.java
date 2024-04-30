import enemy.EnemyComponentSPI;
import normalEnemy.NormalEnemy;

module NormalEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
    provides EnemyComponentSPI with NormalEnemy;
}