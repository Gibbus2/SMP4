package heavyEnemy;

import com.almasb.fxgl.entity.component.Component;
import enemy.EnemySPI;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.List;

public class HeavyEnemy implements EnemySPI {
    @Override
    public Component createEnemyComponent(List<Point2D> wayPoints) {
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
