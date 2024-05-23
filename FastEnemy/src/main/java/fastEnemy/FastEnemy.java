package fastEnemy;

import com.almasb.fxgl.entity.component.Component;
import enemy.EnemySPI;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.List;

public class FastEnemy implements EnemySPI {
    @Override
    public Component createEnemyComponent(List<Point2D> wayPoints) {
        return new FastEnemyComponent(wayPoints);
    }

    @Override
    public Image getImage() {
        InputStream is = FastEnemy.class.getResourceAsStream("/fastEnemy48.png");
        if (is != null) {
            return new Image(is);
        }
        return null;
    }
}
