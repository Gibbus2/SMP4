import enemy.EnemyComponentSPI;
import fastEnemy.FastEnemy;

module FastEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    provides EnemyComponentSPI with FastEnemy;
}