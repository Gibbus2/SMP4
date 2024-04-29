import common.player.PlayerSPI;
import enemy.services.EnemyComponentSPI;
import objectPool.IObjectPool;

open module GameEngine {
    requires com.almasb.fxgl.all;

    requires Common;

    requires CommonEnemy;
    uses EnemyComponentSPI;

    requires CommonPlayer;
    uses PlayerSPI;

    requires ObjectPool;
    uses IObjectPool;
    uses common.bullet.BulletSPI;


    requires WaveManager;
    requires Map;


    requires Health;
    requires UI;
    requires CommonBullet;
}