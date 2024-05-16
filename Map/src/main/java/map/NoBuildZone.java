package map;

import com.almasb.fxgl.dsl.FXGL;
import common.data.EntityType;
import common.data.GameData;

public class NoBuildZone {

     public void noBuildZone(GameData gameData) {
         int a = FXGL.getGameWorld().getEntitiesByType(EntityType.NO_BUILD_ZONE).size();
         for (int i = 0; i < a; i++) {
                var obj = FXGL.getGameWorld().getEntitiesByType(EntityType.NO_BUILD_ZONE).get(i);
                double x = obj.getX() * 2;
                double y = obj.getY() * 2;

                obj.setPosition(x,y);
                obj.getViewComponent().setVisible(gameData.debug);
                //FXGL.getGameWorld().getEntitiesByType((EntityType.NO_BUILD_ZONE)).get(i).setPosition(x, y);
         }


     }
}


