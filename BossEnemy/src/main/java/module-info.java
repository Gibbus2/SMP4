import enemy.services.EnemyComponentSPI;

module BossEnemy {
    uses EnemyComponentSPI;
    requires com.almasb.fxgl.all;
    requires CommonEnemy;
    requires Common;
}