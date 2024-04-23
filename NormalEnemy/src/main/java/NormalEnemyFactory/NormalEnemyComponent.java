package NormalEnemyFactory;

import enemy.data.EnemyComponent;

public class NormalEnemyComponent extends EnemyComponent {
    private int hp = 100;
    private int damage = 10;
    private int speed = 2;
    private int score = 10;

    public NormalEnemyComponent() {
    }
    public EnemyComponent createEnemyComponent() {
        return new EnemyComponent(hp, damage, speed, score);
    }
}
