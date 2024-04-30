package WaveManager.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import enemy.Enemy;
import javafx.util.Duration;
import objectPool.IObjectPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generation {
    private final Map<String, Integer> generation;
    private final Map<String, Double> totalDistance;
    private Runnable onStop;
    private boolean waveOver;
    private int removedEntities, totalEntities;

    public Generation(Runnable onStop){
        this.onStop = onStop;
        generation = new HashMap<>();
        totalDistance = new HashMap<>();
        removedEntities = 0;
        totalEntities = 0;
        waveOver = false;
    }

    public void generate(List<String> enemies, Generation bestGeneration){
        for (String enemy: enemies){
            generation.put(enemy, 5);
            totalEntities += 5;
            totalDistance.put(enemy, 0.0);
        }
        //TODO: use best generation as starting point
    }

    public void launch(IObjectPool objectPool){
        int delay = 0;
        for (String key : this.generation.keySet()){
            for (int i = 0; i < this.generation.get(key); i++) {
                FXGL.getGameTimer().runOnceAfter(() -> {
                    Entity e = objectPool.getEntityFromPool(key);
                    //reset enemy component
                    for(Component component : e.getComponents()){
                        if(component instanceof Enemy){
                            Enemy enemy = (Enemy) component;
                            enemy.reset();
                            enemy.setOnRemove(() -> {
                                totalDistance.put(key, totalDistance.get(key) + enemy.getDistanceTravelled());
                                removedEntities++;
                                if(removedEntities == totalEntities){
                                    waveOver = true;
                                    onStop.run();
                                }
                            });
                        }
                    }
                }, Duration.seconds(delay));
                delay++;
            }
        }
    }

    public double averageDistance(){
        double sumDistance = 0;
        for (String key : totalDistance.keySet()){
            sumDistance += totalDistance.get(key);
        }
        return sumDistance / removedEntities;
    }

    public boolean isWaveOver(){
        return waveOver;
    }
}
