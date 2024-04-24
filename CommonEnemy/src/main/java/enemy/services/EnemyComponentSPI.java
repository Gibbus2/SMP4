package enemy.services;


import com.almasb.fxgl.dsl.FXGL.*;
import enemy.data.EnemyComponent;
import javafx.geometry.Point2D;

import java.util.List;

public interface EnemyComponentSPI {
    EnemyComponent createEnemyComponent(List<Point2D> wayPoints);
    javafx.scene.image.ImageView getImage();
}
