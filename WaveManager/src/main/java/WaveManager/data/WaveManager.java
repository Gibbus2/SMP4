package WaveManager.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.texture.Texture;
import common.data.EntityType;
import common.data.GameData;
import enemy.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import objectPool.IObjectPool;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;


public class WaveManager {
    //this should prob be 0 since startWave() increments on call?
    //nvm it actually needs to be 1 since spawning is based on that number
    //so 0 = no spawning, need to offset counter by 1 or do i?
    private int currentWave = 1;
    private List<Point2D> wayPoints;
    private Button startWaveButton;
    private IObjectPool objectPool;
    private ArrayList<Generation> generations;
    private List<String> enemies;
    private GameData gameData;

    public WaveManager(IObjectPool objectPool, GameData gameData) {
        this.objectPool = objectPool;
        this.gameData = gameData;
        this.enemies = new ArrayList<>();
        this.generations = new ArrayList<>();
    }

    public void init(){
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
                            .viewWithBBox(new Rectangle(texture.getWidth(), texture.getHeight(), (gameData.debug) ? Color.GREEN : new Color(0,0,0,0)))
                            .type(EntityType.ENEMY)
                            .with(new CollidableComponent(true))
                            .with(componentSPI.createEnemyComponent(wayPoints))
                            .view(texture.copy())
                            .with(new StateComponent())
                            .buildAndAttach()
            );

            this.enemies.add(id);
        }

        //add first generation
        Generation generation = new Generation(this::createNextGeneration);
        generation.generate(this.enemies, null);
        this.generations.add(generation);

    }

    private void createNextGeneration(){

        //find best wave
        Generation bestGeneration = this.generations.getFirst();
        for (Generation gen : this.generations){
            if(gen.averageDistance() > bestGeneration.averageDistance()){
                bestGeneration = gen;
            }
        }

        //generate new wave
        Generation generation = new Generation(this::createNextGeneration);
        generation.generate(this.enemies, bestGeneration);
        this.generations.add(generation);

        startWaveButton.setVisible(true);
        currentWave++;

    }

    public void startWave(){
        FXGL.getWorldProperties().setValue("currentWave", currentWave);
        this.generations.getLast().launch(this.objectPool);
    }

    public void startWaveUI(){
        startWaveButton = new Button("Start Wave");


        startWaveButton.setTranslateX(50);
        startWaveButton.setTranslateY(50);
        startWaveButton.setOnAction(e -> {
            this.startWave();
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


//    public void stopWave(){
//        //stop wave, use this on player death
//        //need to figure out if we return to main screen on death
//        //or just go back to start
//        //need to also use fxgl to remove all enemies, figure out how to remove specific entities
//        waveData.getEnemies().clear();
//    }
//
    public int getCurrentWave(){
        return currentWave;
    }

//
//    public void waveIntermission(){
//        //intermission between waves
//        //guess call this on wave end, then
//        //new wave starts after x(30) seconds or something
//        var countDownText = FXGL.getUIFactoryService().newText("");
//        //countDownText.setFont(FXGL.getUIFactoryService().newFont(24)); // this bricks something forgot what
//        countDownText.setTranslateX(300);
//        countDownText.setTranslateY(300);
//        countDownText.setFill(Paint.valueOf("BLACK"));
//        //add countdown text to screen
//        FXGL.getGameScene().addUINode(countDownText);
//
//        //getGameTimer wants AtomicInteger for thread safety, no clue if needed
//        countDown = new AtomicInteger(5);
//
//        FXGL.getGameTimer().runAtInterval(() -> {
//            countDown.getAndDecrement();
//            countDownText.setText("Next wave in: " + countDown);
//            if (countDown.get() == 0) {
//                FXGL.getGameTimer().clear();
//                startWave();
//                countDownText.setText("");
//            }
//        }, Duration.seconds(1));
//    }
}
