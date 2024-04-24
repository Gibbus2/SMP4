package BossEnemyFactory;

import enemy.data.EnemyComponent;
import javafx.geometry.Point2D;
import java.util.List;

public class BossEnemyComponent extends EnemyComponent {
    private int hp = 1000;
    private int damage = 50;
    private int speed = 2;
    private int score = 100;

    public BossEnemyComponent() {
    }
    @Override
    public EnemyComponent createEnemyComponent(List<Point2D> wayPoints) {
        return new EnemyComponent(hp, damage, speed, score, wayPoints);
    }
}