package WaveManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import enemy.Enemy;
import javafx.util.Duration;
import objectPool.IObjectPool;

import java.util.HashMap;
import java.util.Map;

public class Generation {
    private final Map<String, Integer> distribution;
    private final Map<String, Integer> totalDistance;
    private final Runnable onStop;
    private int removedEntities;
    private final int totalEntities;

    public Generation(Runnable onStop,Map<String, Integer> distribution){
        this.onStop = onStop;
        this.distribution = distribution;
        totalDistance = new HashMap<>();
        removedEntities = 0;
        totalEntities = distribution.values().stream().mapToInt(i -> i).sum();
    }

    public void launch(IObjectPool objectPool){
        int delay = 0;
        for (String key : this.distribution.keySet()){
            totalDistance.put(key, 0);
            for (int i = 0; i < this.distribution.get(key); i++) {
                FXGL.getGameTimer().runOnceAfter(() -> {
                    Entity e = objectPool.getEntityFromPool(key);
                    //reset enemy component
                    for(Component component : e.getComponents()){
                        if(component instanceof Enemy){
                            Enemy enemy = (Enemy) component;
                            enemy.setOnRemove(() -> enemyRemoved(key, enemy.getDistanceTravelled()));
                            enemy.reset();
                        }
                    }
                }, Duration.millis(delay));
                delay += 300;
            }
        }
    }

    private void enemyRemoved(String key, int distance){
        System.out.println("Enemy removed: " + key);
        //update tacking of total distance and removed enemies
        totalDistance.put(key, totalDistance.get(key) + distance);
        removedEntities++;

        //check if wave is over
        if(removedEntities == totalEntities){
            FXGL.getGameTimer().runOnceAfter(this.onStop, Duration.millis(500));
        }
    }

    public int getTotalDistance(){
        int sumDistance = 0;
        for (String key : totalDistance.keySet()){
            sumDistance += totalDistance.get(key);
        }
        return sumDistance;
    }

    public int getTotalEntities() {
        return totalEntities;
    }

    public Map<String, Integer> getDistribution() {
        return distribution;
    }
}
