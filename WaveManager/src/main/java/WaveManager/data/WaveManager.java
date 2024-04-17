package WaveManager.data;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import common.data.EntityType;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static com.almasb.fxgl.dsl.FXGL.*;



public class WaveManager {
    //this should prob be 0 since startWave() increments on call?
    //nvm it actually needs to be 1 since spawning is based on that number
    //so 0 = no spawning, need to offset counter by 1 or do i?
    private int currentWave = 1;
    private List<Point2D> wayPoints;


    private WaveData waveData;
    public void startWave(){
        wayPoints = map.Waypoint.fromPolyline().getWaypoints();
        //start wave eh
        WaveData waveData = new WaveData(currentWave);
        waveData.enemyArrayLoader(currentWave, waveData.getEnemies());
        enemySpawner(waveData);
        FXGL.getWorldProperties().setValue("currentWave", currentWave);
        currentWave++;
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
        AtomicInteger countDown = new AtomicInteger(5);

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

   

    public void pauseWave(){
        //pause wave, not too sure if this is finna be used
    }
    private void enemySpawner(WaveData waveData){

        Polyline polyline = FXGL.getGameWorld().getEntitiesByType(EntityType.WAYPOINT).getFirst().getObject("polyline");
        //polyline.getPoints returns an array of the points in the polyline

        for(int i = 0; i < waveData.getEnemies().size();i++){
            int j = i;
            getGameTimer().runOnceAfter(() -> {
                Entity enemy = waveData.getEnemies().get(j);
                  spawn(enemy.getType().toString(), wayPoints.get(0).getX(), wayPoints.get(0).getY());// get x and y from map, prob add some variance to the spawn
                System.out.println(polyline.getPoints());
            }, Duration.seconds(1 + i));

        }
    }
}
