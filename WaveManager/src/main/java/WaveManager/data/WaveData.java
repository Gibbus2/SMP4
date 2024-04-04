package WaveManager.data;
import com.almasb.fxgl.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class WaveData {
    private int waveCounter;
    private List<Entity> enemies;
    private int enemiesRemaining;


    public WaveData(int waveCounter){
        this.waveCounter = waveCounter;
        this.enemies = new ArrayList<>();
        this.enemiesRemaining = 0;
    }

    public void enemyLoader(int waveCounter, List<Entity> enemies){


        if(waveCounter <= 5){
            for(int i = 0; i < waveCounter; i++){
                Entity enemy = createNormalEnemy();
                enemies.add(enemy);
            }
        } else if (waveCounter <= 9) {
            int normalEnemyCount = waveCounter -3 ;
            int mediumEnemyCount = 2 * waveCounter - 5;
            for(int i = 0; i < normalEnemyCount; i++) {
                Entity normalEnemy = createNormalEnemy();
                enemies.add(normalEnemy);
            }
            for(int i = 0; i < mediumEnemyCount; i++) {
                Entity mediumEnemy = createBossEnemy();
                enemies.add(mediumEnemy);
            }
        } else if (waveCounter >= 10) {
            int normalEnemyCount = waveCounter -10 ;
            int mediumEnemyCount = 2 * waveCounter - 7;
            int heavyEnemyCount = waveCounter - 13;
            int bossEnemyCount = 1;
            for(int i = 0; i < normalEnemyCount; i++) {
                Entity normalEnemy = createNormalEnemy();
                enemies.add(normalEnemy);
            }
            for(int i = 0; i < mediumEnemyCount; i++) {
                Entity mediumEnemy = createMediumEnemy();
                enemies.add(mediumEnemy);
            }
            for(int i = 0; i < heavyEnemyCount; i++) {
                Entity heavyEnemy = createHardEnemy();
                enemies.add(heavyEnemy);
            }
            if (waveCounter % 10 == 0){
                for(int i = 0; i < bossEnemyCount; i++){
                    Entity bossEnemy = createBossEnemy();
                    enemies.add(bossEnemy);
                }
            }
        }
    }



    private Entity createNormalEnemy(){
        return null;
    }
    private Entity createMediumEnemy(){
        return null;
    }
    private Entity createHardEnemy(){
        return null;
    }
    private Entity createBossEnemy(){
        return null;
    }

    public int getwaveCounter() {
        return waveCounter;
    }

    public void setwaveCounter(int waveCounter) {
        this.waveCounter = waveCounter;
    }

    public List<Entity> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Entity> enemies) {
        this.enemies = enemies;
    }

    public int getEnemiesRemaining() {
        return enemiesRemaining;
    }

    public void setEnemiesRemaining(int enemiesRemaining) {
        this.enemiesRemaining = enemiesRemaining;
    }

    public void addEnemy(Entity enemy) {
        enemies.add(enemy);
        enemiesRemaining++;
    }

    public void enemyDefeated() {
        enemiesRemaining--;
    }

    public boolean isWaveComplete() {
        return enemiesRemaining == 0;
    }
}
