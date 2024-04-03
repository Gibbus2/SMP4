package WaveManager.data;
import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class WaveData {
    private int waveCounter;
    private int enemiesSpawned;
    private boolean isStarted;
    private boolean isComplete;
    private List<Entity> enemies;


    public WaveData(){
        enemies = new ArrayList<>();
        isStarted = false;
        isComplete = false;
    }

    public List<Entity> getEnemies(){
        return enemies;
    }

    public boolean isComplete(){
        return isComplete;
    }

    public void setComplete() {
        isComplete = true;
    }

    public int getWaveCounter() {
        return waveCounter;
    }

    public void setWaveCounter(int waveCounter) {
        this.waveCounter = waveCounter;
    }

    public void incrementWaveCounter(int waveCounter){
        this.waveCounter = waveCounter + 1;
    }

    public void decrementWaveCounter(int waveCounter){
        this.waveCounter = waveCounter - 1;
    }


    public int getEnemiesSpawned() {
        return enemiesSpawned;
    }

    public int incrementEnemiesSpawned(){
        return enemiesSpawned + 1;
    }

    public int decrementEnemiesSpawned(){
        return enemiesSpawned - 1;
    }

    public void addEnemy(Entity enemy){
        enemies.add(enemy);
    }


}
