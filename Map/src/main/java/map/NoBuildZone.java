package map;

import com.almasb.fxgl.dsl.FXGL;
import common.data.EntityType;

public class NoBuildZone {

     public void noBuildZone() {
         int a = FXGL.getGameWorld().getEntitiesByType(EntityType.NO_BUILD_ZONE).size();

         for (int i = 0; i < a; i++) {

                var obj = FXGL.getGameWorld().getEntitiesByType(EntityType.NO_BUILD_ZONE).get(i);
                double x = obj.getX() * 2;
                double y = obj.getY() * 2;

             System.out.println("x: " + x + " y: " + y);

                FXGL.getGameWorld().getEntitiesByType((EntityType.NO_BUILD_ZONE)).get(i).setPosition(x, y);


             System.out.println("Entity : " + FXGL.getGameWorld().getEntitiesByType(EntityType.NO_BUILD_ZONE).get(i));

         }


     }
}


