package heavyEnemy;

import enemy.Enemy;
import enemy.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.List;

public class HeavyEnemy implements EnemyComponentSPI {
    @Override
    public Enemy createEnemyComponent(List<Point2D> wayPoints) {
        return new HeavyEnemyComponent(wayPoints);
    }

    @Override
    public Image getImage() {
        InputStream is = HeavyEnemy.class.getResourceAsStream("/heavyEnemy48.png");
        if (is != null) {
            return new Image(is);
        }
        return null;
    }

}
