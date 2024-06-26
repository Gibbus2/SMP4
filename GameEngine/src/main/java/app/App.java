package app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import common.bullet.CommonBullet;
import common.data.ShowButtonTrigger;
import common.data.StartWaveTrigger;
import common.player.PlayerSPI;
import common.tower.CommonTowerCollider;
import enemy.CommonEnemyComponent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

import common.data.EntityType;

import common.data.GameData;

import WaveManager.WaveManager;
import static java.util.stream.Collectors.toList;

import javafx.scene.text.Text;
import javafx.util.Duration;
import map.MapLoader;

import objectPool.ObjectPool;
import objectPool.IObjectPool;


import objectPool.PooledObjectComponent;
import ui.GameInformation;
import map.Waypoint;
import ui.TowerSelection;


public class App extends GameApplication {
    GameData gameData = new GameData();
    private IObjectPool objectPool = new ObjectPool();
    private WaveManager waveManager = new WaveManager(objectPool, gameData);
    private TowerSelection towerSelection = new TowerSelection();
    private GameInformation gameInformation = new GameInformation();
    private Text gameOverText;
    private Text fpsText;




    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(gameData.getDisplayWidth());
        settings.setHeight(gameData.getDisplayHeight());
        settings.setTitle("SDU TD");
        settings.setGameMenuEnabled(true);
        settings.setMainMenuEnabled(true);
//        settings.setSceneFactory(new SceneFactory() {
//            @Override
//            public FXGLMenu newGameMenu() {
//                System.out.println("newGameMenu");
//                return new GameMenu();
//            }
//        });


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

    @Override
    protected void initGame() {
        //put this here as im too lazy to go to GameData and change
        gameData.setDebug(true);

        MapLoader mapLoader;
        try {
            mapLoader = new MapLoader();
            mapLoader.loadLevel(0, gameData);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        Point2D end = Waypoint.fromPolyline().getWaypoints().getLast().subtract(24,24);

        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> FXGL.entityBuilder()
                        .type(EntityType.PLAYER)
                        .at(end)
                        .viewWithBBox(new Rectangle(48, 48, (gameData.debug) ? Color.RED : new Color(0, 0, 0, 0)))
                        .with(spi.createComponent())
                        .with(new CollidableComponent(true))
                        .buildAndAttach()
        );

        System.out.println(objectPool == null ? "True" : "False");

        FXGL.getEventBus().addEventHandler(ShowButtonTrigger.ANY, showButtonTrigger -> {
            System.out.println("Show button trigger");
            gameInformation.showButton();
        });
        FXGL.getEventBus().addEventHandler(StartWaveTrigger.ANY, startWaveTrigger -> {
            System.out.println("Start wave trigger");
            waveManager.launchNextWave();
        });

        // init wave manager
        waveManager.init();
        endGameChecker();
    }

    @Override
    protected void initPhysics() {
        System.out.println("Creating physics handlers.");
        System.out.println(" - Enemy and Player collision.");
        // CommonEnemy and Player collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PLAYER) {
            @Override
            protected void onCollisionBegin(Entity enemy, Entity player) {
                System.out.println("Enemy colliding with player");
                for(Component component : enemy.getComponents()){
                    if(component instanceof CommonEnemyComponent){
                        ((CommonEnemyComponent) component).damage(((CommonEnemyComponent) component).getHealth(), true);
                    }
                }

                getPlayerSPIs().stream().findFirst().ifPresent(
                        spi -> spi.changeHealth(-1)
                );
            }
        });

        System.out.println(" - Enemy and Tower collision.");
        // CommonEnemy and Tower collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.TOWER) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                if(gameData.debug)
                    System.out.println("Enemy colliding with tower");
                for(Component component : b.getComponents()){
                    if(component instanceof CommonTowerCollider){
                        if(gameData.debug)
                            System.out.println("Found tower collider component");
                        ((CommonTowerCollider) component).addEnemy(a);
                    }
                }
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                if(gameData.debug)
                    System.out.println("Enemy colliding with tower ended");
                for(Component component : b.getComponents()){
                    if(component instanceof CommonTowerCollider){
                        if (gameData.debug)
                            System.out.println("Found tower collider component");
                        ((CommonTowerCollider) component).removeEnemy(a);
                    }
                }
            }
        });

        System.out.println(" - Bullet and Enemy collision.");
        // Bullet and CommonEnemy collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
//                System.out.println("Bullet colliding with enemy.");

                int damage = 0;

                for(Component component : bullet.getComponents()) {
                    if (component instanceof CommonBullet) {
                        damage = ((CommonBullet) component).getDamage();
                    }
                }

                if (damage == 0) {
                    System.out.println("No damage set on bullet. Is it missing a component or is wrong type set?");
                }

                for(Component component : enemy.getComponents()){
                    if(component instanceof CommonEnemyComponent){
                        ((CommonEnemyComponent) component).damage(damage, false);
                    }
                }

                if (bullet.hasComponent(PooledObjectComponent.class)){
                    bullet.getComponent(PooledObjectComponent.class).returnToPool();
                } else {
                    bullet.removeFromWorld();
                }
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BUILD, EntityType.NO_BUILD_ZONE) {
            @Override
            protected void onCollisionBegin(Entity Build, Entity no_build_zone) {towerSelection.setColissionCounter(towerSelection.getColissionCounter() + 1);}
            @Override
            protected void onCollisionEnd(Entity build, Entity no_build_zone) {towerSelection.setColissionCounter(towerSelection.getColissionCounter() - 1);}
        });
    }

    @Override
    protected void initUI() {
        // TODO: Use Map module to load scene "Main Menu".



        HBox hbox = towerSelection.createTowerSelection(objectPool, gameData);
        FXGL.getGameScene().addUINode(hbox);
        //HBox hboxInfo = towerSelection.displayInformation();
        //FXGL.getGameScene().addUINode(hboxInfo);

       /* var brickTexture = FXGL.getAssetLoader().loadTexture("brick.png");
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

        FXGL.getGameScene().addUINode(brickTexture);*/

        gameInformation.startWaveUI();
        VBox vbox = gameInformation.displayInformation();
        FXGL.getGameScene().addUINode(vbox);
        gameOverText();
        fpsCounter();
    }

    private long frameCount = 0;
    private long lastUpdate = 0;
    //setting to 60 so it doesnt have to start counting from 0
    private long totalFrameCount = 60;
    private long startTime = System.nanoTime();


    @Override
    protected void onUpdate(double tpf) {
        if(gameData.debug){
            frameCount++;
            totalFrameCount++;
            fpsText.setVisible(gameData.debug);
            long now = System.nanoTime();
            long elapsedTime = now - startTime;
            double avgFPS = totalFrameCount / (elapsedTime / 1_000_000_000.0);
            if (now - lastUpdate >= 1_000_000_000) {
                fpsText.setText("FPS: " + frameCount);
                frameCount = 0;
                lastUpdate = now;
                System.out.println("Average FPS: " + avgFPS);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void endGameChecker(){
        ColorAdjust grayScale = new ColorAdjust();
        grayScale.setSaturation(-1);
        Optional<? extends PlayerSPI> playerSPIOptional = getPlayerSPIs().stream().findFirst();
        PlayerSPI playerSPI = playerSPIOptional.orElse(null);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            if(playerSPI != null){
                if(playerSPI.getHealth() <= 0 && !gameOverText.isVisible() ){
                    endGameScreen();
                    FXGL.getGameController().pauseEngine();
                    FXGL.getGameScene().getRoot().setEffect(grayScale);
                    gameOverText.setVisible(true);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void endGameScreen(){
        HBox hbox = new HBox();

        hbox.setLayoutX(0);
        hbox.setLayoutY(725);
        hbox.setPrefSize(1440, 150);

        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, null, null);
        Background background = new Background(backgroundFill);

        hbox.setBackground(background);
        hbox.setSpacing(20);
        hbox.toFront();

        Button exitButton = new Button("Exit");
        exitButton.setPadding(new javafx.geometry.Insets(10, 20, 10, 20));
        exitButton.setOnAction(e -> System.exit(0));
        hbox.getChildren().add(exitButton);

        Text endText = new Text("You made it to round " + waveManager.getCurrentWave() + " congratulations!");
        endText.setFont(javafx.scene.text.Font.font(20));
        hbox.getChildren().add(endText);


        FXGL.getGameScene().addUINode(hbox);
    }
    public void gameOverText(){
        gameOverText = new Text("Game Over");
        gameOverText.setFont(javafx.scene.text.Font.font(50));
        double centerX = gameData.getDisplayWidth() / 2 - gameOverText.getLayoutBounds().getWidth() / 2;
        double centerY = gameData.getDisplayHeight() / 2 - gameOverText.getLayoutBounds().getHeight() / 2;
        gameOverText.setLayoutX(centerX);
        gameOverText.setLayoutY(centerY);
        FXGL.getGameScene().addUINode(gameOverText);
        gameOverText.setVisible(false);
    }
    public void fpsCounter(){
        fpsText = new Text("FPS: 0");
        fpsText.setVisible(false);

        fpsText.setLayoutX(10);
        fpsText.setLayoutY(20);

        fpsText.setFont(javafx.scene.text.Font.font(20));
        fpsText.setFill(Color.BLACK);

        FXGL.getGameScene().addUINode(fpsText);
    }




    private Collection<? extends PlayerSPI> getPlayerSPIs() {
        System.out.println("Loading PlayerSPI.");
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}