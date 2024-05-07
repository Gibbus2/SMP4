package WaveManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.texture.Texture;
import common.data.EntityType;
import common.data.GameData;
import enemy.Enemy;
import enemy.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import objectPool.IObjectPool;
import javafx.util.Duration;


import java.util.*;


public class WaveManager {
    private int currentWave;
    private List<Point2D> wayPoints;
    private Button startWaveButton;
    private final IObjectPool objectPool;
    private final List<String> enemyKeys;
    private final GameData gameData;
    private Generations generations;

    public WaveManager(IObjectPool objectPool, GameData gameData) {
        this.objectPool = objectPool;
        this.gameData = gameData; //TODO: what was this for?
        this.enemyKeys = new ArrayList<>();
        this.currentWave = 1;
    }

    public void init() {
        //get waypoints
        this.wayPoints = map.Waypoint.fromPolyline().getWaypoints();

        //get config for enemies and add them to object pool
        List<EnemyComponentSPI> enemies = ServiceLoader.load(EnemyComponentSPI.class).stream().map(ServiceLoader.Provider::get).toList();
        for (int i = 0; i < enemies.size(); i++) {

            String id = "WaveManager_enemy_" + i;
            Texture texture = new Texture(enemies.get(i).getImage());
            EnemyComponentSPI componentSPI = enemies.get(i);

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

        this.generations = new Generations(this.enemyKeys.size(), 10);

    }

    public void launchNextWave() {
        FXGL.getWorldProperties().setValue("currentWave", currentWave); //TODO: move into UI
        Genome genome = generations.getLatest();
        int delay = 0;
        // for each gene (enemy) in the chromosome
        for (int i = 0; i < genome.getChromosome().size(); i++) {

            //launch the amount of entities defined by the gene
            int toLaunch = genome.getChromosome().get(i);
            for (int j = 0; j < toLaunch; j++) {
                String key = this.enemyKeys.get(i);
                FXGL.getGameTimer().runOnceAfter(() -> {
                    Entity e = objectPool.getEntityFromPool(key);
                    //reset enemy component
                    for (Component component : e.getComponents()) {
                        if (component instanceof Enemy) {
                            Enemy enemy = (Enemy) component;
                            enemy.setOnRemove(() -> {
                                genome.entityRemoved(enemy.getDistanceTravelled());
                                generateNextWave();
                            });
                            enemy.reset();
                        }
                    }
                }, Duration.millis(delay));
                delay += 300;
            }

        }
    }

    private void generateNextWave() {
        if (generations.getLatest().isDead()) {
            System.out.println("Generating next wave");
            generations.reproduce();
            currentWave++;
            startWaveButton.setVisible(true);
        }
    }

    //TODO: move into UI
    public void startWaveUI() {
        startWaveButton = new Button("Start Wave");


        startWaveButton.setTranslateX(50);
        startWaveButton.setTranslateY(50);
        startWaveButton.setOnAction(e -> {
            this.launchNextWave();
            startWaveButton.setVisible(false);
        });
        FXGL.getGameScene().addUINode(startWaveButton);

        //waveCounter text
        Text waveCounterText = new Text();
        waveCounterText.setTranslateX(50);
        waveCounterText.setTranslateY(150);
        waveCounterText.textProperty().bind(FXGL.getWorldProperties().intProperty("currentWave").asString("Wave: %d"));

        FXGL.getGameScene().addUINode(waveCounterText);

    }

    public int getCurrentWave() {
        return currentWave;
    }
}