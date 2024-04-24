package enemy.services;


import enemy.data.EnemyComponent;
import javafx.geometry.Point2D;

import java.util.List;

public interface EnemyComponentSPI {
    EnemyComponent createEnemyComponent(List<Point2D> wayPoints);
}
