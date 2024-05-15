package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;

import common.data.EntityType;
import common.player.PlayerSPI;
import common.tower.CommonTowerComponent;
import common.tower.TowerSPI;

import javafx.scene.control.Button;
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
    private String towerName;
    private boolean buildableArea = true;

    public int getColissionCounter() {
        return colissionCounter;
    }

    public void setColissionCounter(int colissionCounter) {
        this.colissionCounter = colissionCounter;
    }

    private int colissionCounter = 0;
    private AtomicBoolean isInsideShop = new AtomicBoolean(false);
    private AtomicBoolean isImageFollowingCursor = new AtomicBoolean(false);
    private Collection<? extends PlayerSPI> player() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends TowerSPI> towers() {
        return ServiceLoader.load(TowerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    public void setBuildableArea(boolean buildableArea) {
        this.buildableArea = buildableArea;
    }
    private boolean canBuild(){
        boolean canBuild;
        canBuild = buildableArea && !isInsideShop.get() && isImageFollowingCursor.get();
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
            Entity towerEntity = buildTowerAndAttach(
                    towers().stream().filter(tower -> tower.getName().equals(towerName)).findFirst().get().getImage(),
                    event.getSceneX() - towers().stream().filter(tower -> tower.getName().equals(towerName)).findFirst().get().getImage().getHeight() / 2,
                    event.getSceneY() - towers().stream().filter(tower -> tower.getName().equals(towerName)).findFirst().get().getImage().getWidth() / 2
            );
            if (towerEntity != null) {
                FXGL.getGameWorld().addEntity(towerEntity);
                FXGL.getGameWorld().removeEntity(imageEntity);
                isImageFollowingCursor.set(false);
            }
        }
    }
    private Entity buildTowerAndAttach(Image image, double x, double y) {
        Entity towerEntity = null;
        for (TowerSPI tower : towers())
            if (tower.getName().equals(towerName)) {
                towerEntity = FXGL.entityBuilder()
                        .type(EntityType.NO_BUILD_ZONE)
                        .at(x, y)
                        .viewWithBBox(new Rectangle(image.getWidth(), image.getHeight(), Color.GREEN))
                        .view(new ImageView(image))
                        .with(new CommonTowerComponent(objectPool))
                        .with(new CollidableComponent(true))
                        .buildAndAttach();
            }
        return towerEntity;
    }
    public HBox getTowers() {
        HBox towerBox = new HBox();
        for (TowerSPI tower : towers()) {
            Texture texture = new Texture(tower.getImage());
            towerBox.getChildren().add(texture);
            texture.setOnMouseClicked(e -> {
                player().stream().findFirst().ifPresent(
                        spi -> {
                            if (tower.getCost() <= spi.getMoney() && !isImageFollowingCursor.get()) {
                                imageEntity = FXGL.entityBuilder()
                                        .type(EntityType.BUILD)
                                        .viewWithBBox(new Rectangle(texture.getWidth() - 2, texture.getHeight() - 2, Color.GREEN))
                                        .with(new CollidableComponent(true))
                                        .view(texture.copy())
                                        .buildAndAttach();
                                isImageFollowingCursor.set(true);
                                imageEntity.setPosition(e.getSceneX() - imageEntity.getHeight() / 2, e.getSceneY() - imageEntity.getWidth() / 2);
                                towerName = tower.getName();

                                System.out.println("TEST " + isImageFollowingCursor.get());
                            } else {
                                System.out.println("Not enough money");
                            }
                        }
                );
            });
            FXGL.getGameScene().getContentRoot().setOnMouseClicked(this::mouseClickHandler);
            FXGL.getGameScene().getContentRoot().setOnMouseMoved(this::moveImageWithCursor);
        }
        return towerBox;
    }

    public HBox createTowerSelection(IObjectPool objectPool) {
        HBox hbox = new HBox();
        hbox.setLayoutX(0);
        hbox.setLayoutY(720);
        hbox.setPrefSize(1440, 88);

        this.objectPool = objectPool;

        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);

        hbox.setOnMouseEntered(e -> {
            isInsideShop.set(true);
            System.out.println("Mouse entered :" + isInsideShop);
        });
        hbox.setOnMouseExited(e -> {
            isInsideShop.set(false);
            System.out.println("Mouse exited :" + isInsideShop);
        });

        //Testing area start

        Button setMoneyButton = new Button("Set Money to 200");
        setMoneyButton.setOnAction(e -> {
            player().stream().findFirst().ifPresent(
                    spi -> spi.setMoney(200)
            );

        });
        hbox.getChildren().add(getTowers());
        hbox.getChildren().add(setMoneyButton);

        //Testing area end


        return hbox;
    }

    //TODO add colission counter to check if tower is in buildable area instead of singular boolean


}