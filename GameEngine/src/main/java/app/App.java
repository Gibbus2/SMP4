package app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import common.bullet.BulletSPI;
import common.player.PlayerSPI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;

import common.data.EntityType;
import enemy.data.EnemyComponent;
import health.HealthComponent;

import common.data.GameData;
import javafx.scene.text.Text;

import WaveManager.data.WaveManager;
import WaveManager.data.WaveManagerEntityFactory;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static java.util.stream.Collectors.toList;

import map.MapLoader;

import objectPool.ObjectPool;
import objectPool.IObjectPool;


import ui.GameMenu;


public class App extends GameApplication {
    GameData gameData = new GameData();
    private IObjectPool objectPool = new ObjectPool();
    private WaveManager waveManager = new WaveManager(objectPool);



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


        objectPool.createPool(EnemyComponent.class.toString(),
                () -> FXGL.entityBuilder()
                        // TODO: Build enemy here.
                        .type(EntityType.ENEMY)
                        .at(0,0)
                        .view(new Circle(15, Color.RED))
                        //.with health
                        //.with some behavior component
                        .buildAndAttach()
        );

        try {
            MapLoader mapLoader = new MapLoader();
            mapLoader.loadLevel(0);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        objectPool.createPool("NORMAL_ENEMY",
                () -> FXGL.entityBuilder()
                        .type(EntityType.NORMAL_ENEMY)
                        .viewWithBBox(new Rectangle(48,48, Color.RED))
                        .with(new CollidableComponent(true))
                        //adding enemy component with hp, damage, speed, and score
                        .with(new StateComponent())
                        .buildAndAttach()
        );

        objectPool.getEntityFromPool("NORMAL_ENEMY").setPosition(50, 50);


        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> player = FXGL.entityBuilder()
                        .type(EntityType.PLAYER)
                        .at(300, 100)
                        .viewWithBBox(new Rectangle(25, 25, Color.BLUE))
                        .with(spi.createComponent())
                        .with(new CollidableComponent(true))
                        .buildAndAttach()
        );


        enemy = FXGL.entityBuilder()
                .type(EntityType.ENEMY)
                .at(100, 100)
                .viewWithBBox(new Circle(15, Color.RED))
                .with(new CollidableComponent(true))
                .buildAndAttach();

//        test.removeFromWorld();

//        coin = FXGL.entityBuilder()
//                .type(EntityType.COIN)
//                .at(500, 100)
//                .viewWithBBox(new Circle(15, Color.YELLOW))
//                .with(new CollidableComponent(true))
//                .buildAndAttach();

    //start wave

    //this should prob not be here, move to timer or button later
        getGameWorld().addEntityFactory(new WaveManagerEntityFactory());
        waveManager.init();
        //waveManager.waveIntermission();

    }

    @Override
    protected void initPhysics() {
        // CommonEnemy and Player collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PLAYER) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity enemy, Entity player) {
                enemy.removeFromWorld();
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
//                enemy.getComponent(EnemyComponent.class).damage(bullet.getComponent(BulletComponent.class));

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

        // TODO:

        ui.TowerSelection towerSelection = new ui.TowerSelection();
        FXGL.getGameScene().addUINode(towerSelection);

        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100
 // add to the scene graph

//        Text coinText = new Text();
//        coinText.setTranslateX(100); // x = 50
//        coinText.setTranslateY(100); // y = 100
//
//        coinText.textProperty().bind(FXGL.getWorldProperties().intProperty("coinsCollected").asString());
//
//        FXGL.getGameScene().addUINode(coinText);

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

        waveManager.startWaveUI(waveManager);

        //Button for starting wave, need to agree on if we do button to start
        //or just intermission on game start then run after x seconds
        //does this need to be in here or wavemanager for jpms?
        //would assume i need to change this as if wavemanager gets removed
        //it would just break right? but for now it just needs to work


    }

    @Override
    protected void onUpdate(double tpf) {
        //System.out.println("onUpdate : " + waveManager.getEnemyCount() );
        waveManager.delayButton(waveManager);
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