package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;

import common.data.EntityType;
import common.player.PlayerSPI;
import common.tower.TowerSPI;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.stream.Collectors.toList;

public class TowerSelection {
    private Entity imageEntity;
    private boolean buildableArea;
    private AtomicBoolean isInsideShop = new AtomicBoolean(false);
    private String towerName;
    private AtomicBoolean isImageFollowingCursor = new AtomicBoolean(false);
    private Collection<? extends PlayerSPI> player() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends TowerSPI> towers() {
        return ServiceLoader.load(TowerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
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

            FXGL.getGameScene().getContentRoot().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && buildableArea && !isInsideShop.get() && isImageFollowingCursor.get()) {
                    player().stream().findFirst().ifPresent(spi -> {spi.setMoney(- tower.getCost());});
                    System.out.println("Building tower : " + tower.getName());

                    /*FXGL.getGameWorld().removeEntity(imageEntity);*/
                    //TODO: Add tower to the game world
                }
                if (event.getButton() == MouseButton.SECONDARY && isImageFollowingCursor.get()) {
                    isImageFollowingCursor.set(false);
                    FXGL.getGameWorld().removeEntity(imageEntity);
                }
            });
/*            FXGL.getGameScene().getContentRoot().setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.SECONDARY && isImageFollowingCursor.get()) {
                    isImageFollowingCursor.set(false);
                    FXGL.getGameWorld().removeEntity(imageEntity);
                }
            });*/

            FXGL.getGameScene().getContentRoot().setOnMouseMoved(e -> {
                if (isImageFollowingCursor.get()) {
                    ;
                    imageEntity.setPosition(e.getSceneX() - imageEntity.getHeight() / 2, e.getSceneY() - imageEntity.getWidth() / 2);
                }
            });
        }
        return towerBox;
    }

    private void clickBuilder(){

    }

    public HBox createTowerSelection() {
        HBox hbox = new HBox();
        hbox.setLayoutX(0);
        hbox.setLayoutY(720);
        hbox.setPrefSize(1440, 88);

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

    //TODO add method to fix the long ifstatment in build

    public void setBuildableArea(boolean buildableArea) {
        this.buildableArea = buildableArea;
    }
}