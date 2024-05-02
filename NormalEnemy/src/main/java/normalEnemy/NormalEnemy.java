package normalEnemy;

import enemy.Enemy;
import enemy.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import objectPool.IObjectPool;

import java.io.InputStream;
import java.util.List;

public class NormalEnemy implements EnemyComponentSPI {

    @Override
    public Enemy createEnemyComponent(List<Point2D> wayPoints) {
        return new NormalEnemyComponent(wayPoints);
    }

    @Override
    public Image getImage(){
        InputStream is = NormalEnemy.class.getResourceAsStream("/normalEnemy48.png");
        return new Image(is);
    }
}
