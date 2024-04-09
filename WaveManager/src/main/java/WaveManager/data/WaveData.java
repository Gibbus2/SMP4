package WaveManager.data;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import java.util.ArrayList;
import java.util.List;

public class WaveData {

    private int waveCounter;
    private List<Entity> enemies;
    private int enemiesRemaining;


    public WaveData(int waveCounter) {
        this.waveCounter = waveCounter;
        this.enemies = new ArrayList<>();
        this.enemiesRemaining = 0;
        //check if i made some spaghetti with how the enemy array
        //has been set up, feel like i might have
    }

    public void enemyArrayLoader(int waveCounter, List<Entity> enemies) {
        //this should create enemy entities based on the waveCounter
        //then load it into the enemies list and sending it off to
        //the enemySpawner, although i think the entity array is actually not used
        int waveRange = 0;
        int normalEnemyCount = 0;
        int mediumEnemyCount = 0;
        int hardEnemyCount = 0;
        int bossEnemyCount = 0;

        if (waveCounter <= 5) {
            waveRange = 1;
        } else if (waveCounter <= 9) {
            waveRange = 2;
        } else {
            waveRange = 3;
        }
        //switched to switch case, no clue if its actually better or something
        switch (waveRange) {
            case 1:
                for(int i = 0; i < waveCounter; i++){
                    Entity enemy = createNormalEnemy();
                    enemies.add(enemy);
                    enemiesRemaining++;
                }
                break;

            case 2:
                normalEnemyCount = waveCounter -3 ;
                mediumEnemyCount = 2 * waveCounter - 5;

                for(int i = 0; i < normalEnemyCount; i++) {
                    Entity normalEnemy = createNormalEnemy();
                    enemies.add(normalEnemy);
                    enemiesRemaining++;
                }
                for(int i = 0; i < mediumEnemyCount; i++) {
                    Entity mediumEnemy = createMediumEnemy();
                    enemies.add(mediumEnemy);
                    enemiesRemaining++;
                }
                break;

            case 3:
                normalEnemyCount = waveCounter -10 ;
                mediumEnemyCount = 2 * waveCounter - 7;
                hardEnemyCount = waveCounter - 7;
                bossEnemyCount = 1;

                for(int i = 0; i < normalEnemyCount; i++) {
                    Entity normalEnemy = createNormalEnemy();
                    enemies.add(normalEnemy);
                    enemiesRemaining++;
                }
                for(int i = 0; i < mediumEnemyCount; i++) {
                    Entity mediumEnemy = createMediumEnemy();
                    enemies.add(mediumEnemy);
                    enemiesRemaining++;
                }
                for(int i = 0; i < hardEnemyCount; i++) {
                    Entity hardEnemy = createHardEnemy();
                    enemies.add(hardEnemy);
                    enemiesRemaining++;
                }
                if (waveCounter % 10 == 0){
                    for(int i = 0; i < bossEnemyCount; i++){
                        Entity bossEnemy = createBossEnemy();
                        enemies.add(bossEnemy);
                        enemiesRemaining++;
                    }
                }
                break;
        }
    }


    private Entity createNormalEnemy() {
        WaveManagerEntityFactory factory = new WaveManagerEntityFactory();
        return factory.normalEnemy(new SpawnData(0,0));
    }

    private Entity createMediumEnemy() {
        WaveManagerEntityFactory factory = new WaveManagerEntityFactory();
        return factory.mediumEnemy(new SpawnData(0,0));
    }

    private Entity createHardEnemy() {
        WaveManagerEntityFactory factory = new WaveManagerEntityFactory();
        return factory.hardEnemy(new SpawnData(0,0));
    }

    private Entity createBossEnemy() {
        WaveManagerEntityFactory factory = new WaveManagerEntityFactory();
        return factory.bossEnemy(new SpawnData(0,0));
    }

    public int getWaveCounter() {
        return waveCounter;
    }

    public void setWaveCounter(int waveCounter) {
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
