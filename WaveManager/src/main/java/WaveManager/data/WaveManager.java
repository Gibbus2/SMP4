package WaveManager.data;
import com.almasb.fxgl.entity.Entity;

import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.*;


public class WaveManager {
    //this should prob be 0 since startWave() increments on call?
    private int currentWave = 1;
    private WaveData waveData;
    public void startWave(){
        //start wave eh
        WaveData waveData = new WaveData(currentWave);
        waveData.enemyArrayLoader(currentWave, waveData.getEnemies());
        enemySpawner(waveData);
        currentWave++;
    }

    public void stopWave(){
        //stop wave, use this on player death
        //need to figure out if we return to main screen on death
        //or just go back to start
        //need to also use fxgl to remove all enemies, figure out how to remove specific entities
        waveData.getEnemies().clear();
    }

    public void pauseWave(){
        //pause wave, not too sure if this is gonna be used
    }
    private void enemySpawner(WaveData waveData){
        Random random = new Random();
        for(int i = 0; i < waveData.getEnemies().size();i++){
            int j = i;
            getGameTimer().runOnceAfter(() -> {
                Entity enemy = waveData.getEnemies().get(j);
                  spawn(enemy.getType().toString(), 500 + random.nextInt(10),500 + random.nextInt(10)); // get x and y from map, prob add some variance to the spawn
            }, Duration.seconds((double) 1 /waveData.getWaveCounter()));
        }
    }



}
