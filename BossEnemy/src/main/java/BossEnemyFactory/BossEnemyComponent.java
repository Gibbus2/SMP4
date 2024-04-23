package BossEnemyFactory;

import enemy.data.EnemyComponent;

public class BossEnemyComponent extends EnemyComponent {
    private int hp = 1000;
    private int damage = 50;
    private int speed = 2;
    private int score = 100;

    public BossEnemyComponent() {
    }
    public EnemyComponent createEnemyComponent() {
        return new EnemyComponent(hp, damage, speed, score);
    }
}
