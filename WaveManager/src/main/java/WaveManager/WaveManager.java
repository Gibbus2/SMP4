package WaveManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.texture.Texture;
import common.data.EntityType;
import common.data.GameData;
import common.data.ShowButtonTrigger;
import common.data.StartWaveTrigger;
import enemy.CommonEnemyComponent;
import enemy.EnemySPI;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import objectPool.IObjectPool;
import javafx.util.Duration;
import map.Waypoint;


import java.util.*;


public class WaveManager {
    private int currentWave;
    private List<Point2D> wayPoints;
    private final IObjectPool objectPool;
    private final List<String> enemyKeys;
    private final GameData gameData;
    private Generations generations;

    private final int timeSpawnDelay;

    public WaveManager(IObjectPool objectPool, GameData gameData) {
        this.objectPool = objectPool;
        this.gameData = gameData;
        this.enemyKeys = new ArrayList<>();
        this.currentWave = 1;
        this.timeSpawnDelay = 400;
    }

    public void init() {
        //get waypoints
        this.wayPoints = Waypoint.fromPolyline().getWaypoints();

        //get config for enemies and add them to object pool
        List<EnemySPI> enemies = ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).toList();
        for (int i = 0; i < enemies.size(); i++) {

            String id = "WaveManager_enemy_" + i;
            Texture texture = new Texture(enemies.get(i).getImage());
            EnemySPI componentSPI = enemies.get(i);

            this.objectPool.createPool((id),
                    () -> FXGL.entityBuilder()
                            .viewWithBBox(new Rectangle(texture.getWidth(), texture.getHeight(), (gameData.debug) ? Color.GREEN : new Color(0, 0, 0, 0)))
                            .type(EntityType.ENEMY)
                            .with(new CollidableComponent(true))
                            .with(componentSPI.createEnemyComponent(wayPoints))
                            .view(texture.copy())
                            .with(new StateComponent())
                            .buildAndAttach()
            );

            this.enemyKeys.add(id);
        }

        this.generations = new Generations(this.enemyKeys.size(), 4);

    }

    public void launchNextWave() {
        FXGL.getWorldProperties().setValue("currentWave", currentWave);
        Population population = generations.getLatest();
        int delay = 0;
        // for each gene (enemy) in the chromosome
        for (int i = 0; i < population.getChromosome().size(); i++) {

            //launch the amount of entities defined by the gene
            int toLaunch = population.getChromosome().get(i);
            for (int j = 0; j < toLaunch; j++) {
                String key = this.enemyKeys.get(i);
                FXGL.getGameTimer().runOnceAfter(() -> {
                    Entity e = objectPool.getEntityFromPool(key);
                    //reset enemy component
                    for (Component component : e.getComponents()) {
                        if (component instanceof CommonEnemyComponent commonEnemyComponent) {
                            commonEnemyComponent.setOnRemove(() -> {
                                population.entityRemoved(commonEnemyComponent.getDistanceTravelled());
                                generateNextWave();
                            });
                            commonEnemyComponent.reset();
                        }
                    }
                }, Duration.millis(delay));
                delay += timeSpawnDelay;
            }

        }
    }

    private void generateNextWave() {
        if (generations.getLatest().isDead()) {
            System.out.println("Generating next wave");
            generations.reproduce();
            currentWave++;
            FXGL.getEventBus().fireEvent(new ShowButtonTrigger());
        }
    }



    public int getCurrentWave() {
        return currentWave;
    }
}