package WaveManager.data;
import com.almasb.fxgl.entity.Entity;

import javafx.util.Duration;

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
        for(int i = 0; i < waveData.getEnemies().size();i++){
            int j = i;
            getGameTimer().runOnceAfter(() -> {
                Entity enemy = waveData.getEnemies().get(j);
                //set up EntityFactory
                //i guess enemy needs to be sent to the factory to be spawned?
                spawn("Enemy", 0,0); // get x and y from map, prob add some variance to the spawn
            }, Duration.seconds((double) 1 /waveData.getWaveCounter()));
        }
    }



}
