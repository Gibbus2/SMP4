import common.player.PlayerSPI;
import objectPool.IObjectPool;

open module GameEngine {
    requires com.almasb.fxgl.all;
    requires Common;
    requires CommonEnemy;
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