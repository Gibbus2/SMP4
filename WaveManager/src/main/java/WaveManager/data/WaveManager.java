package WaveManager.data;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import common.data.EntityType;
import enemy.Enemy;
import enemy.EnemyComponentSPI;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import objectPool.IObjectPool;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicInteger;


public class WaveManager {
    //this should prob be 0 since startWave() increments on call?
    //nvm it actually needs to be 1 since spawning is based on that number
    //so 0 = no spawning, need to offset counter by 1 or do i?
    private int currentWave = 1;
    private List<Point2D> wayPoints;
    private int enemyCount = 0;
    private WaveData waveData;
    private AtomicInteger countDown = new AtomicInteger(0);
    private Button startWaveButton;
    private boolean timerRunning = false;
    private IObjectPool objectPool;
    private ArrayList<Generation> generations;
    private List<String> enemies;

    public WaveManager(IObjectPool objectPool) {
        this.objectPool = objectPool;
    }

    public void init(){
        //FXGL.getEventBus().addEventHandler(EnemyReachedEndEvent.ANY, event -> enemyCount--);
        this.generations = new ArrayList<>();




//        for (EnemyComponentSPI enemy : enemiesSPI){
//            enemies.add("");
//        }

        //generations.add(new Generation(enemies));
        this.wayPoints = map.Waypoint.fromPolyline().getWaypoints();

        for (Point2D p : wayPoints){
            FXGL.entityBuilder()
                    .at(p)
                    .viewWithBBox(new Rectangle(1,1, Color.RED))
                    .buildAndAttach();
        }

        List<EnemyComponentSPI> enemies = ServiceLoader.load(EnemyComponentSPI.class).stream().map(ServiceLoader.Provider::get).toList();

        for (int i = 0; i < enemies.size(); i++) {

            Enemy enemy = enemies.get(i).createEnemyComponent(wayPoints);
            Texture texture = new Texture(enemies.get(i).getImage());

            this.objectPool.createPool("HELLO",
                    () -> FXGL.entityBuilder()
                            .viewWithBBox(new Rectangle(48, 48, Color.GREEN))
                            .type(EntityType.ENEMY)
                            .with(new CollidableComponent(true))
                            .with(enemy)
                            .view(texture)
                            .with(new StateComponent())
                            .buildAndAttach()
            );

        }

        Entity e = this.objectPool.getEntityFromPool("HELLO");
        e.getComponent(Enemy.class).reset();

        System.out.println("Enemy added");
        System.out.println(e.getComponent(Enemy.class).getEntity().getWidth());
        //e.setRotation(0);

       // generations.getFirst().createEnemies(this.wayPoints, this.objectPool);
    }

//    private void createNextGeneration(){
//        //find best wave
//        Generation bestGeneration = this.generations.getFirst();
//        for (Generation gen : this.generations){
//            if(gen.getAvgDistance() > bestGeneration.getAvgDistance()){
//                bestGeneration = gen;
//            }
//        }
//
//        //generate new wave
//        this.generations.add(new Generation(bestGeneration.getEnemies(), bestGeneration.getGeneration()));
//
//    }

    public void startWave(){
       // wayPoints = map.Waypoint.fromPolyline().getWaypoints();
        //start wave eh
//        waveData = new WaveData(objectPool, currentWave);
//        waveData.enemyArrayLoader(currentWave, waveData.getEnemies());
//        enemySpawner(waveData);
//        FXGL.getWorldProperties().setValue("currentWave", currentWave);
//        currentWave++;
    }

    public void setButtonVisible(){
        startWaveButton.setVisible(true);
    }

//    public void startWaveUI(WaveManager waveManager){
//        startWaveButton = new Button("Start Wave");
//
//
//        startWaveButton.setTranslateX(50);
//        startWaveButton.setTranslateY(50);
//        startWaveButton.setOnAction(e -> {
//            waveManager.waveIntermission();
//            startWaveButton.setVisible(false);
//        });
//        FXGL.getGameScene().addUINode(startWaveButton);
//
//        //waveCounter text
//        Text waveCounterText = new Text();
//        waveCounterText.setTranslateX(50);
//        waveCounterText.setTranslateY(150);
//        waveCounterText.textProperty().bind(FXGL.getWorldProperties().intProperty("currentWave").asString("Wave: %d"));
//
//        FXGL.getGameScene().addUINode(waveCounterText);
//
//    }

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
//    public int getEnemyCount(){
//        return enemyCount;
//    }
//    public void setEnemyCount(int enemyCount) {
//        this.enemyCount = enemyCount;
//    }


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

//    public AtomicInteger getCountDown(){
//        return countDown;
//    }

//    public void delayButton(WaveManager waveManager) {
//        if (!timerRunning && waveManager.getEnemyCount() == 0 && waveManager.getCountDown().get() == 0) {
//            timerRunning = true;
//            FXGL.getGameTimer().runOnceAfter(() -> {
//                // Check the conditions again after the delay
//                if (waveManager.getEnemyCount() == 0 && waveManager.getCountDown().get() == 0) {
//                    waveManager.setButtonVisible();
//                }
//                timerRunning = false;
//            }, Duration.seconds(1)); // delay in seconds
//        }
//    }
//    private void enemySpawner(WaveData waveData){
//
//        Polyline polyline = FXGL.getGameWorld().getEntitiesByType(EntityType.WAYPOINT).getFirst().getObject("polyline");
//        //polyline.getPoints returns an array of the points in the polyline
//
//        for(int i = 0; i < waveData.getEnemies().size();i++){
//            int j = i;
//            getGameTimer().runOnceAfter(() -> {
//                Entity enemy = waveData.getEnemies().get(j);
//                  spawn(enemy.getType().toString(), wayPoints.get(0).getX(), wayPoints.get(0).getY());// get x and y from map, prob add some variance to the spawn
//                enemyCount++;
//            }, Duration.seconds(1 + i));
//
//        }
//    }
}
