import enemy.EnemyComponentSPI;
import normalEnemy.NormalEnemy;

module NormalEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    provides EnemyComponentSPI with NormalEnemy;
}