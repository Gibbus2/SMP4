package common.services;


public interface EnemyComponentSPI {
    EnemyComponentSPI createEnemyComponent(int hp, int damage, int speed, int score);
}
