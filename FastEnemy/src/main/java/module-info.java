import enemy.EnemySPI;
import fastEnemy.FastEnemy;

module FastEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    provides EnemySPI with FastEnemy;
}