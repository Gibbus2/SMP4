package map;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.shape.Polyline;
import java.util.ArrayList;
import java.util.List;
import common.data.EntityType;
public class Waypoint {



    private List<Point2D> waypoints;

    public Waypoint(List<Point2D> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Point2D> getWaypoints() {
        return new ArrayList<>(waypoints);
    }

    public static Waypoint fromPolyline() {
        var list = new ArrayList<Point2D>();
        Entity obj = FXGL.getGameWorld().getEntitiesByType(EntityType.WAYPOINT).getFirst();
        Polyline polyline = (Polyline) obj.getObject("polyline");

        for (int i = 0; i < polyline.getPoints().size(); i += 2) {
            var x = polyline.getPoints().get(i) + obj.getX();
            var y = polyline.getPoints().get(i+1) + obj.getY();
            list.add(new Point2D(x, y));
        }

        return new Waypoint(list);
    }
}

