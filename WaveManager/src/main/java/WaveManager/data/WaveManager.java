package WaveManager.data;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.time.Duration;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;


public class WaveManager {
    /*
    The game needs to spawn normal mobs based on round number
    something like 5 mobs * wave counter
    Then there needs to be special mobs that spawn each rounds after x
    amount of rounds, say 10 rounds in, tanks now spawn each round
    then every 20 ish rounds, a boss spawns, which can be found with
    modulus. Game needs to increment enemy left counter when something
    spawns, and then decrement it when they die.
     */
    private void enemySpawner(){
        WaveData waveData = new WaveData();
        for(int i = 0; i < waveData.getEnemies().size();i++){
            int j = i;
            getGameTimer().runOnceAfter(() -> {
                Enemy enemy = waveData.getEnemies(j);
            }, Duration.seconds(1));

        }
    }


}
