package WaveManager.data;

public class GameSpawner {
    /*
    The game needs to spawn normal mobs based on round number
    something like 5 mobs * wave counter
    Then there needs to be special mobs that spawn each rounds after x
    amount of rounds, say 10 rounds in, tanks now spawn each round
    then every 20 ish rounds, a boss spawns, which can be found with
    modulus. Game needs to increment enemy left counter when something
    spawns, and then decrement it when they die.
     */
    private int enemiesSpawned;

    public int getEnemiesSpawned() {
        return enemiesSpawned;
    }

    public void setEnemiesSpawned(int enemiesSpawned) {
        this.enemiesSpawned = enemiesSpawned;
    }


}
