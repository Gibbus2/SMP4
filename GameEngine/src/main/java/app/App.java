package app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import common.bullet.BulletSPI;
import common.player.PlayerSPI;
import enemy.Enemy;
import javafx.geometry.Point2D;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;

import common.data.EntityType;
import health.HealthComponent;

import common.data.GameData;

import WaveManager.data.WaveManager;
import static java.util.stream.Collectors.toList;

import map.MapLoader;

import objectPool.ObjectPool;
import objectPool.IObjectPool;


import ui.GameMenu;
import ui.ImageLoader;
import map.Waypoint;
import ui.TowerSelection;


public class App extends GameApplication {
    GameData gameData = new GameData();
    private IObjectPool objectPool = new ObjectPool();
    private WaveManager waveManager = new WaveManager(objectPool, gameData);

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(gameData.getDisplayWidth());
        settings.setHeight(gameData.getDisplayHeight());
        settings.setTitle("Dick N Bauss");
        settings.setGameMenuEnabled(true);
        settings.setMainMenuEnabled(true);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newGameMenu() {
                System.out.println("newGameMenu");
                return new GameMenu();
            }
        });


    }

    @Override
    protected void onPreInit() {

    }

    @Override
    protected void initInput() {

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // TODO: Should probably be moved into UI module when rendering
        // current wave to view.
        vars.put("currentWave", waveManager.getCurrentWave());
    }

    Entity player;
    Entity enemy;
    @Override
    protected void initGame() {

        MapLoader mapLoader;
        try {
            mapLoader = new MapLoader();
            mapLoader.loadLevel(0);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }


        Point2D end = Waypoint.fromPolyline().getWaypoints().getLast().subtract(24,24);
        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> player = FXGL.entityBuilder()
                        .type(EntityType.PLAYER)
                        .at(end)
                        .viewWithBBox(new Rectangle(48, 48, Color.RED))
                        .with(spi.createComponent())
                        .with(new CollidableComponent(true))
                        .buildAndAttach()
        );


        player = FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(end)
                .viewWithBBox(new Rectangle(48, 48, Color.RED))
                //.with(spi.createComponent())
                .with(new CollidableComponent(true))
                .buildAndAttach();



        System.out.println(player.getWidth());

        // init wave manager
        waveManager.init();

    }

    @Override
    protected void initPhysics() {
        System.out.println("Physics");
        // CommonEnemy and Player collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PLAYER) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity enemy, Entity player) {
                for(Component component : enemy.getComponents()){
                    if(component instanceof Enemy){
                        ((Enemy) component).returnToObjectPool();
                    }
                }
                getPlayerSPIs().stream().findFirst().ifPresent(
                        spi -> spi.changeHealth(-1)
                );
            }
        });

        // Bullet and CommonEnemy collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
                // TODO: Uncomment line below when enemy is merged into dev.
                enemy.getComponent(Enemy.class).damage(5);

                // TODO: Logic should be moved into EnemyComponent.
                if (enemy.getComponent(HealthComponent.class).isDead()) {
                    enemy.removeFromWorld();
                    // TODO: Scales with wave level, or something.
//                    getEnemySPIs().stream().findFirst().ifPresent(
//                            spi -> spi.changeHealth(-1)
//                    );
                }
            }
        });
    }

    @Override
    protected void initUI() {
        // TODO: Use Map module to load scene "Main Menu".

        TowerSelection towerSelection = new TowerSelection();
        HBox hbox = towerSelection.createTowerSelection();
        FXGL.getGameScene().addUINode(hbox);

        var brickTexture = FXGL.getAssetLoader().loadTexture("brick.png");
        brickTexture.setTranslateX(50);
        brickTexture.setTranslateY(450);

        brickTexture.setOnMouseClicked(e -> {
            System.out.println("Clicked on textPixels");
        });

        brickTexture.setOnMouseEntered(e -> {
            System.out.println("Mouse entered textPixels");
            brickTexture.setScaleX(1.2);
            brickTexture.setScaleY(1.2);
        });

        brickTexture.setOnMouseExited(e -> {
            System.out.println("Mouse exited textPixels");
            brickTexture.setScaleX(1.0);
            brickTexture.setScaleY(1.0);
        });

        FXGL.getGameScene().addUINode(brickTexture);

        waveManager.startWaveUI();

        //Button for starting wave, need to agree on if we do button to start
        //or just intermission on game start then run after x seconds
        //does this need to be in here or wavemanager for jpms?
        //would assume i need to change this as if wavemanager gets removed
        //it would just break right? but for now it just needs to work


    }


    @Override
    protected void onUpdate(double tpf) {

    }

    public static void main(String[] args) {
        launch(args);
    }

    private Collection<? extends PlayerSPI> getPlayerSPIs() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

//    private Collection<? extends EnemySPI> getEnemySPIs() {
//        return ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


}