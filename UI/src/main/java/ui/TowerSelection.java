package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;
import common.player.PlayerSPI;
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
    private int Moneh;

    private Collection<? extends PlayerSPI> getPlayerSPIs() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    public HBox createTowerSelection() {
        HBox hbox = new HBox();
        hbox.setLayoutX(0);
        hbox.setLayoutY(720);
        hbox.setPrefSize(1440, 88);

        List<String> list = new ArrayList<>();
        list.add("/assets/tower1_48px.png");
        list.add("/assets/tower2_48px.png");
        list.add("/assets/tower3_48px.png");
        list.add("/assets/tower4_48px.png");
        list.add("/assets/tower5_48px.png");
        list.add("/assets/tower6_48px.png");


        AtomicBoolean isImageFollowingCursor = new AtomicBoolean(false);
        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);

        Button setMoneyButton = new Button("Set Money to 200");
        setMoneyButton.setOnAction(e -> {
            getPlayerSPIs().stream().findFirst().ifPresent(
                    spi -> spi.setMoney(200)
            );

        });



        hbox.getChildren().add(setMoneyButton);


        for (String path : list) {
            InputStream is = TowerSelection.class.getResourceAsStream(path);
            assert is != null;
            int cost = 101;
            Image img = new Image(is);
            imageView.setImage(img);
            Texture texture = new Texture(img);
            hbox.getChildren().add(texture);

            texture.setOnMouseClicked(e -> {
                getPlayerSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            if (cost <= spi.getMoney() && !isImageFollowingCursor.get()) {
                                    imageEntity = FXGL.entityBuilder()
                                            .viewWithBBox(new Rectangle(texture.getWidth(), texture.getHeight(), Color.GREEN))
                                            .with(new CollidableComponent(true))
                                            .view(texture.copy())
                                            .buildAndAttach();
                                    isImageFollowingCursor.set(true);
                                    imageEntity.setPosition(e.getSceneX() - imageEntity.getHeight() / 2, e.getSceneY() - imageEntity.getWidth() / 2 );
                                }
                            else {
                                System.out.println("Not enough money");
                            }
                        }
                );

            });

            FXGL.getGameScene().getContentRoot().setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.SECONDARY && isImageFollowingCursor.get()) {
                    isImageFollowingCursor.set(false);
                    FXGL.getGameWorld().removeEntity(imageEntity);
                }
            });
            FXGL.getGameScene().getContentRoot().setOnMouseMoved(e -> {
                if (isImageFollowingCursor.get()) {;
                    imageEntity.setPosition(e.getSceneX() - imageEntity.getHeight() / 2, e.getSceneY() - imageEntity.getWidth() / 2 );
                }
            });


/* grayscale shit for money

if ("money".equals(evt.getPropertyName())) {
                int newMoney = (int) evt.getNewValue();
                if (newMoney < cost) {
                    texture.setEffect(new javafx.scene.effect.ColorAdjust(0, -1, 0, 0)); // grayscale
                } else {
                    texture.setEffect(null); // original color
                }
            }*/

        }
        return hbox;
    }
}