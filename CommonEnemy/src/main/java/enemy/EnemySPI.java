package enemy;


import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.List;

public interface EnemySPI {
    Component createEnemyComponent(List<Point2D> wayPoints);

    Image getImage();
}
