package WaveManager.data;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import common.data.EntityType;

import Enemy.data.EnemyReachedEndEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGL.*;



public class WaveManager {
    //this should prob be 0 since startWave() increments on call?
    //nvm it actually needs to be 1 since spawning is based on that number
    //so 0 = no spawning, need to offset counter by 1 or do i?
    private int currentWave = 1;
    private List<Point2D> wayPoints;
    private int enemyCount = 0;
    private WaveData waveData;
    private AtomicInteger countDown;

    public void init(){
        FXGL.getEventBus().addEventHandler(EnemyReachedEndEvent.ANY, event -> enemyCount--);
    }

    public void startWave(){
        wayPoints = map.Waypoint.fromPolyline().getWaypoints();
        //start wave eh
        waveData = new WaveData(currentWave);
        waveData.enemyArrayLoader(currentWave, waveData.getEnemies());
        enemySpawner(waveData);
        FXGL.getWorldProperties().setValue("currentWave", currentWave);
        currentWave++;
    }

    public void startWaveUI(WaveManager waveManager){
        Button startWaveButton = new Button("Start Wave");


        startWaveButton.setTranslateX(50);
        startWaveButton.setTranslateY(50);
        startWaveButton.setOnAction(e -> {
            waveManager.waveIntermission();
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

    public void stopWave(){
        //stop wave, use this on player death
        //need to figure out if we return to main screen on death
        //or just go back to start
        //need to also use fxgl to remove all enemies, figure out how to remove specific entities
        waveData.getEnemies().clear();
    }

    public int getCurrentWave(){
        return currentWave;
    }
    public int getEnemyCount(){
        return enemyCount;
    }
    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public void waveIntermission(){
        //intermission between waves
        //guess call this on wave end, then
        //new wave starts after x(30) seconds or something
        var countDownText = FXGL.getUIFactoryService().newText("");
        //countDownText.setFont(FXGL.getUIFactoryService().newFont(24)); // this bricks something forgot what
        countDownText.setTranslateX(300);
        countDownText.setTranslateY(300);
        countDownText.setFill(Paint.valueOf("BLACK"));
        //add countdown text to screen
        FXGL.getGameScene().addUINode(countDownText);

        //getGameTimer wants AtomicInteger for thread safety, no clue if needed
        countDown = new AtomicInteger(5);

        FXGL.getGameTimer().runAtInterval(() -> {
            countDown.getAndDecrement();
            countDownText.setText("Next wave in: " + countDown);
            if (countDown.get() == 0) {
                FXGL.getGameTimer().clear();
                startWave();
                countDownText.setText("");
            }
        }, Duration.seconds(1));
    }


    private void enemySpawner(WaveData waveData){

        Polyline polyline = FXGL.getGameWorld().getEntitiesByType(EntityType.WAYPOINT).getFirst().getObject("polyline");
        //polyline.getPoints returns an array of the points in the polyline

        for(int i = 0; i < waveData.getEnemies().size();i++){
            int j = i;
            getGameTimer().runOnceAfter(() -> {
                Entity enemy = waveData.getEnemies().get(j);
                  spawn(enemy.getType().toString(), wayPoints.get(0).getX(), wayPoints.get(0).getY());// get x and y from map, prob add some variance to the spawn
                enemyCount++;
            }, Duration.seconds(1 + i));

        }
    }
}
