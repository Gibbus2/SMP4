package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;

import common.data.EntityType;
import common.player.PlayerSPI;
import common.tower.TowerSPI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.stream.Collectors.toList;

public class TowerSelection {
    private Entity imageEntity;

    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }

    private boolean canBuild = true;


    private Collection<? extends PlayerSPI> player() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends TowerSPI> towers() {
        return ServiceLoader.load(TowerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public HBox createTowerSelection() {
        HBox hbox = new HBox();
        hbox.setLayoutX(0);
        hbox.setLayoutY(720);
        hbox.setPrefSize(1440, 88);

        AtomicBoolean isImageFollowingCursor = new AtomicBoolean(false);
        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);


        //Testing area start

        Button setMoneyButton = new Button("Set Money to 200");
        setMoneyButton.setOnAction(e -> {
            player().stream().findFirst().ifPresent(
                    spi -> spi.setMoney(200)
            );

        });
        hbox.getChildren().add(setMoneyButton);

        //Testing area end

        for (TowerSPI tower : towers()) {
            Texture texture = new Texture(tower.getImage());
            hbox.getChildren().add(texture);
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

                            } else {
                                System.out.println("Not enough money");
                            }
                        }
                );
            });

            FXGL.getGameScene().getContentRoot().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && canBuild && isImageFollowingCursor.get()) {
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
        return hbox;
    }
}