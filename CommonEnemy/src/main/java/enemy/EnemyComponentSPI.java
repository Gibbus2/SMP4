package enemy;


import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.util.List;

public interface EnemyComponentSPI {
    Enemy createEnemyComponent(List<Point2D> wayPoints);

    Image getImage();
}
