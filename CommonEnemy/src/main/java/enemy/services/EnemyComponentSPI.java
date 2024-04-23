package enemy.services;


import enemy.data.EnemyComponent;

public interface EnemyComponentSPI {
    EnemyComponent createEnemyComponent();
}
