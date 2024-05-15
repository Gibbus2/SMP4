package app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import common.bullet.CommonBullet;
import common.player.PlayerSPI;
import common.tower.CommonTowerCollider;
import common.tower.CommonTowerComponent;
import common.tower.TowerSPI;
import enemy.CommonEnemyComponent;
import javafx.geometry.Point2D;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.ServiceLoader;

import common.data.EntityType;

import common.data.GameData;

import WaveManager.WaveManager;
import static java.util.stream.Collectors.toList;

import map.MapLoader;

import objectPool.ObjectPool;
import objectPool.IObjectPool;


import objectPool.PooledObjectComponent;
import ui.GameMenu;
import map.Waypoint;
import ui.TowerSelection;


public class App extends GameApplication {
    GameData gameData = new GameData();
    private IObjectPool objectPool = new ObjectPool();
    private WaveManager waveManager = new WaveManager(objectPool, gameData);

    private TowerSelection towerSelection = new TowerSelection();
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(gameData.getDisplayWidth());
        settings.setHeight(gameData.getDisplayHeight());
        settings.setTitle("SDU TD");
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
                        .viewWithBBox(new Rectangle(48, 48, (gameData.debug) ? Color.RED : new Color(0, 0, 0, 0)))
                        .with(spi.createComponent())
                        .with(new CollidableComponent(true))
                        .buildAndAttach()
        );

        Entity tower = FXGL.entityBuilder()
                .type(EntityType.NO_BUILD_ZONE)
                .at(600,600)
                .viewWithBBox(new Rectangle(48, 48, Color.BLUE))
                .with(new CollidableComponent(true))
                .with(new CommonTowerComponent(objectPool))
                .buildAndAttach();

        System.out.println(objectPool == null ? "True" : "False");

//        Entity bullet = FXGL.entityBuilder()
//                .type(EntityType.BULLET)
//                .at(600,600)
//                .viewWithBBox(new Rectangle(16, 16, Color.GRAY))
//                .with(new CollidableComponent(true))
//                .with(new CommonBullet(null))
//                .buildAndAttach();

        // init wave manager
        waveManager.init();
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
                System.out.println("Enemy colliding with tower");
                for(Component component : b.getComponents()){
                    if(component instanceof CommonTowerCollider){
                        System.out.println("Found tower collider component");
                        ((CommonTowerCollider) component).addEnemy(a);
                    }
                }
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                System.out.println("Enemy colliding with tower ended");
                for(Component component : b.getComponents()){
                    if(component instanceof CommonTowerCollider){
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

        System.out.println(" - Build and No_Build_Zone collision.");
        // Tower and NoBuildZone collision.
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BUILD, EntityType.NO_BUILD_ZONE) {

            @Override
            protected void onCollisionBegin(Entity Build, Entity no_build_zone) {
                // Handle collision here
                // Disable building on to build Entity. Sets canBuild flag to false

                System.out.println("Collision detected between imageEntity and NO_BUILD_ZONE :" + towerSelection.getColissionCounter());
                towerSelection.setColissionCounter(towerSelection.getColissionCounter() + 1);
                System.out.println("Coll counter = " + towerSelection.getColissionCounter());

            }

            @Override
            protected void onCollisionEnd(Entity build, Entity no_build_zone) {
                // Handle collision here
                // Enable  building on to build Entity. Sets canBuild flag to true
                System.out.println("Collision ended between imageEntity and NO_BUILD_ZONE :" + towerSelection.getColissionCounter());
                towerSelection.setColissionCounter(towerSelection.getColissionCounter() - 1);
                System.out.println("Coll counter = " + towerSelection.getColissionCounter());
            }
        });
    }

    @Override
    protected void initUI() {
        // TODO: Use Map module to load scene "Main Menu".


        HBox hbox = towerSelection.createTowerSelection(objectPool);
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
        System.out.println("Loading PlayerSPI.");
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

//    private Collection<? extends EnemySPI> getEnemySPIs() {
//        System.out.println("Loading EnemySPIs.");
//        return ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }



//    private Collection<? extends TowerSPI> getTowerSPIs() {
//        System.out.println("Loading TowerSPIs.");
//        return ServiceLoader.load(TowerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
//    }


}