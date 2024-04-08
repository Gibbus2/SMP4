package app;

import WaveManager.data.WaveManagerEntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import common.data.GameData;
import javafx.scene.text.Text;
import WaveManager.data.WaveManager;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;


public class App extends GameApplication {
    GameData gameData = new GameData();
    private WaveManager waveManager = new WaveManager();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(gameData.getDisplayWidth());
        settings.setHeight(gameData.getDisplayHeight());
        settings.setTitle("Basic Game App");

    }

    @Override
    protected void onPreInit() {

    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                test.translateX(5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                test.translateX(-5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                test.translateY(-5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                test.translateY(5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.S);



        input.addAction(new UserAction("Play Sound") {
            @Override
            protected void onActionBegin() {
                FXGL.play("drop.wav");
            }
        }, KeyCode.F);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
        vars.put("coinsCollected", 0);
        vars.put("currentWave", waveManager.getCurrentWave());
    }
    public enum EntityType  {
        PLAYER, COIN
    }
    Entity test;
    Entity coin;
    @Override
    protected void initGame() {
        test = FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(300, 100)
                //.view(new Rectangle(25, 25, Color.BLUE))
                .viewWithBBox("brick.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();

//        test.removeFromWorld();

        coin = FXGL.entityBuilder()
                .type(EntityType.COIN)
                .at(500, 100)
                .viewWithBBox(new Circle(15, Color.YELLOW))
                .with(new CollidableComponent(true))
                .buildAndAttach();
    //start wave

    //this should prob not be here, move to timer or button later
        getGameWorld().addEntityFactory(new WaveManagerEntityFactory());
        waveManager = new WaveManager();
        //waveManager.waveIntermission();
    }

    @Override
    protected void initPhysics() {


        // We passed PLAYER first and then COIN.
        // Therefore, in onCollisionBegin() the order of entities will be player and then coin
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.COIN) {

            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
                FXGL.inc("coinsCollected", +1);
            }

        });
    }

    @Override
    protected void initUI() {
        // TODO: Use Map module to load scene "Main Menu".

        // TODO:

        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());

        FXGL.getGameScene().addUINode(textPixels); // add to the scene graph

        Text coinText = new Text();
        coinText.setTranslateX(100); // x = 50
        coinText.setTranslateY(100); // y = 100

        coinText.textProperty().bind(FXGL.getWorldProperties().intProperty("coinsCollected").asString());

        FXGL.getGameScene().addUINode(coinText);

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

        //Button for starting wave, need to agree on if we do button to start
        //or just intermission on game start then run after x seconds
        //does this need to be in here or wavemanager for jpms?
        //would assume i need to change this as if wavemanager gets removed
        //it would just break right? but for now it just needs to work
        Button startWaveButton = new Button("Start Wave");
        startWaveButton.setTranslateX(50);
        startWaveButton.setTranslateY(50);
        startWaveButton.setOnAction(e -> {
            waveManager.waveIntermission();
        });
        FXGL.getGameScene().addUINode(startWaveButton);

        //waveCounter text
        Text waveCounterText = new Text();
        waveCounterText.setTranslateX(50);
        waveCounterText.setTranslateY(150);
        waveCounterText.textProperty().bind(FXGL.getWorldProperties().intProperty("currentWave").asString("Wave: %d"));

        FXGL.getGameScene().addUINode(waveCounterText);

    }

    @Override
    protected void onUpdate(double tpf) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}