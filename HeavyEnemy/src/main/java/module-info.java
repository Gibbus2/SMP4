import enemy.EnemyComponentSPI;
import heavyEnemy.HeavyEnemy;


module HeavyEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    provides EnemyComponentSPI with HeavyEnemy;
}