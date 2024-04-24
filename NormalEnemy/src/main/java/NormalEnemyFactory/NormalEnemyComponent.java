package NormalEnemyFactory;

import enemy.data.EnemyComponent;
import javafx.geometry.Point2D;

import java.util.List;

public class NormalEnemyComponent extends EnemyComponent {
    private int hp = 100;
    private int damage = 10;
    private int speed = 2;
    private int score = 10;

    public NormalEnemyComponent() {
    }
    @Override
    public EnemyComponent createEnemyComponent(List<Point2D> wayPoints) {
        return new EnemyComponent(hp, damage, speed, score, wayPoints);
    }
}
