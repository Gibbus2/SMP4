package app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import common.data.EntityType;
import health.HealthComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Map;
import common.data.GameData;
import javafx.scene.text.Text;
import map.MapLoader;
import player.PlayerComponent;


public class App extends GameApplication {
    GameData gameData = new GameData();

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
                player.translateX(5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player.translateY(-5);
                FXGL.inc("pixelsMoved", +5);
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                player.translateY(5);
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
    }

    Entity player;
    @Override
    protected void initGame() {
        try {
            MapLoader mapLoader = new MapLoader();
            mapLoader.loadLevel(0);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        //How to get waypoints from FXGL object from TMX file
        Polyline polyline = FXGL.getGameWorld().getEntitiesByType(map.EntityType.WAYPOINT).getFirst().getObject("polyline");
        //poline.getpoints returns an array of the points in the polyline
        System.out.println("POLYLINE: " + polyline.getPoints());

        player = FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .at(300, 100)
                //.view(new Rectangle(25, 25, Color.BLUE))
                .viewWithBBox(new Rectangle(25, 25))
                .with(new PlayerComponent())
                .buildAndAttach();

//        test.removeFromWorld();

//        coin = FXGL.entityBuilder()
//                .type(EntityType.COIN)
//                .at(500, 100)
//                .viewWithBBox(new Circle(15, Color.YELLOW))
//                .with(new CollidableComponent(true))
//                .buildAndAttach();
    }

    @Override
    protected void initPhysics() {


        // We passed PLAYER first and then COIN.
        // Therefore, in onCollisionBegin() the order of entities will be player and then coin
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PLAYER) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity player, Entity coin) {
                coin.removeFromWorld();
                FXGL.inc("coinsCollected", +1);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.ENEMY) {
            // order of types is the same as passed into the constructor
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
                // TODO: Uncomment line below when enemy is merged into dev.
//                enemy.getComponent(EnemyComponent.class).damage(bullet.getComponent(BulletComponent.class));

                if (enemy.getComponent(HealthComponent.class).isDead()) {
                    enemy.removeFromWorld();
                }
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
    }

    @Override
    protected void onUpdate(double tpf) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}