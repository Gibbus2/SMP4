package WaveManager;

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

import java.util.*;


public class WaveManager {
    //this should prob be 0 since startWave() increments on call?
    //nvm it actually needs to be 1 since spawning is based on that number
    //so 0 = no spawning, need to offset counter by 1 or do i?
    private int currentWave;
    private int waveSize;
    private List<Point2D> wayPoints;
    private Button startWaveButton;
    private final IObjectPool objectPool;
    private final ArrayList<Generation> generations;
    private final List<String> enemies;
    private final GameData gameData;
    private final int reallocationRatio;

    public WaveManager(IObjectPool objectPool, GameData gameData) {
        this.reallocationRatio = 10;

        this.objectPool = objectPool;
        this.gameData = gameData;
        this.enemies = new ArrayList<>();
        this.generations = new ArrayList<>();
        this.currentWave = 1;
        this.waveSize = 10; //TODO: consider if we can increase wave size without disrupting the ai
    }

    public void init(){waveSize += 3;
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

        createFirstWave();
    }

    private void createFirstWave(){
        System.out.println("Creating the first wave");
        Map<String, Integer> generationValues = new HashMap<>();
        for (String enemy : this.enemies){
            generationValues.put(enemy, 0);
        }
        Generation generation = generateGeneration(new Generation(null, generationValues));
        this.generations.add(generation);
    }

    private void createNextWave(){
        currentWave++;
        System.out.println("Creating wave nr. " + currentWave);

        //find best wave
        Generation bestGeneration = this.generations.getFirst();
        for (Generation gen : this.generations){
            if(gen.getTotalDistance() > bestGeneration.getTotalDistance()){
                bestGeneration = gen;
            }
        }

        //generate new generation
        Generation generation = generateGeneration(bestGeneration);
        this.generations.add(generation);

        //update UI
        startWaveButton.setVisible(true);

    }


    private Generation generateGeneration(Generation bestGeneration){
        //calculate what to remove and redistribute
        int toRemove = (bestGeneration.getTotalEntities() / 100) * reallocationRatio;
        int toDistribute = toRemove + (waveSize - bestGeneration.getTotalEntities());

        //copy best generation
        Map<String, Integer> generationValues = new HashMap<>();
        for (String enemy : bestGeneration.getDistribution().keySet()){
            generationValues.put(enemy, bestGeneration.getDistribution().get(enemy));
        }

        //remove
        while (toRemove > 0) {
            for (String enemy : enemies) {
                int randomValue = (int) (Math.random() * (toRemove + 1));
                generationValues.put(enemy, generationValues.get(enemy) - randomValue);
                toRemove -= randomValue;
            }
        }

        //redistribute
        while (toDistribute > 0) {
            for (String enemy : enemies) {
                int randomValue = (int) (Math.random() * (toDistribute + 1));
                int newValue = generationValues.get(enemy) + randomValue;
                generationValues.put(enemy, newValue);
                toDistribute -= randomValue;
            }
        }

        return new Generation(this::createNextWave, generationValues);
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
