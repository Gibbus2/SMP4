package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;

import common.data.EntityType;
import common.data.GameData;
import common.player.PlayerSPI;
import common.tower.TowerSPI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

import objectPool.IObjectPool;
import static java.util.stream.Collectors.toList;

public class TowerSelection {
    private Entity imageEntity;

    private IObjectPool objectPool;
    private GameData gameData;
    private String towerName;

    private AtomicBoolean isInsideShop = new AtomicBoolean(false);
    private AtomicBoolean isImageFollowingCursor = new AtomicBoolean(false);

    private int colissionCounter = 0;

    private Collection<? extends PlayerSPI> player() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends TowerSPI> towers() {
        return ServiceLoader.load(TowerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public int getColissionCounter() {
        return colissionCounter;
    }

    public void setColissionCounter(int colissionCounter) {
        this.colissionCounter = colissionCounter;
    }
    private boolean canBuild(){
        boolean canBuild;
        canBuild = colissionCounter == 0 && !isInsideShop.get() && isImageFollowingCursor.get();
        return canBuild;
    }
    private void removeImageFromMouse(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY && isImageFollowingCursor.get()) {
            isImageFollowingCursor.set(false);
            FXGL.getGameWorld().removeEntity(imageEntity);
        }
    }
    private void moveImageWithCursor(MouseEvent e) {
        if (isImageFollowingCursor.get()) {
            imageEntity.setPosition(e.getSceneX() - imageEntity.getHeight() / 2, e.getSceneY() - imageEntity.getWidth() / 2);
        }
    }
    private void mouseClickHandler(MouseEvent event){
        removeImageFromMouse(event);
        if (event.getButton() == MouseButton.PRIMARY && canBuild()) {
            towers().stream().filter(tower -> tower.getName().equals(towerName)).findFirst().ifPresent(tower -> {
                Entity towerEntity = buildTowerAndAttach(
                        tower.getImage(),
                        event.getSceneX() - tower.getImage().getHeight() / 2,
                        event.getSceneY() - tower.getImage().getWidth() / 2
                );

                if (towerEntity != null) {
                    FXGL.getGameWorld().addEntity(towerEntity);
                    FXGL.getGameWorld().removeEntity(imageEntity);
                    isImageFollowingCursor.set(false);
                }
            });

            player().stream().findFirst().ifPresent(spi ->
                    towers().stream()
                            .filter(tower -> tower.getName().equals(towerName))
                            .findFirst()
                            .ifPresent(tower -> spi.changeMoney(- tower.getCost()))

            );
            System.out.println("Tower built" + player().stream().findFirst().get().getMoney());
        }
    }
    private Entity buildTowerAndAttach(Image image, double x, double y) {
        Entity towerEntity = null;
        for (TowerSPI tower : towers())
            if (tower.getName().equals(towerName)) {
                towerEntity = FXGL.entityBuilder()
                        .type(EntityType.NO_BUILD_ZONE)
                        .at(x, y)
                        .viewWithBBox(new Rectangle(image.getWidth(), image.getHeight(), (gameData.debug) ? Color.GREEN : new Color(0, 0, 0, 0)))
                        .view(new ImageView(image))
                        .with(tower.createComponent(objectPool, gameData))
                        .with(new CollidableComponent(true))
                        .buildAndAttach();
            }
        return towerEntity;
    }
    public HBox getTowers() {
        HBox towerBox = new HBox();
        for (TowerSPI tower : towers()) {
            Texture texture;
            if (tower.getImage() != null) {
                texture = new Texture(tower.getImage());
                towerBox.getChildren().add(texture);
            } else {
                texture = null;
            }
            assert texture != null;
            texture.setOnMouseClicked(e -> {
                player().stream().findFirst().ifPresent(
                        spi -> {
                            if (tower.getCost() <= spi.getMoney() && !isImageFollowingCursor.get()) {
                                imageEntity = FXGL.entityBuilder()
                                        .type(EntityType.BUILD)
                                        .viewWithBBox(new Rectangle(texture.getWidth() - 2, texture.getHeight() - 2, (gameData.debug) ? Color.GREEN : new Color(0, 0, 0, 0)))
                                        .with(new CollidableComponent(true))
                                        .view(texture.copy())
                                        .buildAndAttach();
                                isImageFollowingCursor.set(true);
                                imageEntity.setPosition(e.getSceneX() - imageEntity.getHeight() / 2, e.getSceneY() - imageEntity.getWidth() / 2);
                                towerName = tower.getName();
                            } else {

                                //TODO add a message to the screen that says not enough money
                                if ((gameData.debug)){
                                System.out.println("Not enough money");
                            }
                            }
                        }
                );
            });
            FXGL.getGameScene().getContentRoot().setOnMouseClicked(this::mouseClickHandler);
            FXGL.getGameScene().getContentRoot().setOnMouseMoved(this::moveImageWithCursor);
        }
        return towerBox;
    }

    public HBox createTowerSelection(IObjectPool objectPool, GameData gameData){
        HBox hbox = new HBox();
        hbox.setLayoutX(0);
        hbox.setLayoutY(720);
        hbox.setPrefSize(1440, 88);

        this.objectPool = objectPool;
        this.gameData = gameData;

        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);

        hbox.setOnMouseEntered(e -> {isInsideShop.set(true);});
        hbox.setOnMouseExited(e -> {isInsideShop.set(false);});
        hbox.getChildren().add(getTowers());
        return hbox;
    }

}