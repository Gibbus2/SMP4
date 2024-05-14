import enemy.EnemySPI;
import heavyEnemy.HeavyEnemy;


module HeavyEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    provides EnemySPI with HeavyEnemy;
}