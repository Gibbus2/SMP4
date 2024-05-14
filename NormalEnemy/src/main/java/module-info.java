import enemy.EnemySPI;
import normalEnemy.NormalEnemy;

module NormalEnemy {
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    provides EnemySPI with NormalEnemy;
}