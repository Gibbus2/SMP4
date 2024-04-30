package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TowerSelection {

    public HBox createTowerSelection() {
        HBox hbox = new HBox();
        hbox.setLayoutX(0);
        hbox.setLayoutY(720);
        hbox.setPrefSize(1440, 88);

        Rectangle rectangle = new Rectangle(10, 10, Color.RED);
        Rectangle rectangle2 = new Rectangle(10, 10, Color.BLUE);

        rectangle.setTranslateX(0);
        rectangle.setTranslateY(0);
        rectangle2.setTranslateX(1420);
        rectangle2.setTranslateY(78);

        List<String> list = new ArrayList<>();
        list.add("/assets/tower1.png");
        list.add("/assets/tower2.png");
        list.add("/assets/tower3.png");
        list.add("/assets/tower4.png");
        list.add("/assets/tower5.png");
        list.add("/assets/tower6.png");

        System.out.println("List: " + list);

        AtomicBoolean isImageFollowingCursor = new AtomicBoolean(false);
        ImageView imageView = new ImageView();
        imageView.setMouseTransparent(true);

        for (String path : list) {
            InputStream is = TowerSelection.class.getResourceAsStream(path);
            assert is != null;
            Image img = new Image(is);
            Texture texture = new Texture(img);
            texture.setOnMouseClicked(e -> {
                System.out.println("Clicked on texture: " + path);
                if (!isImageFollowingCursor.get()) {
                    imageView.setImage(texture.getImage());
                    imageView.setX(e.getSceneX() - imageView.getImage().getWidth() / 2);
                    imageView.setY(e.getSceneY() - imageView.getImage().getHeight() / 2);
                    FXGL.getGameScene().addUINode(imageView);
                    isImageFollowingCursor.set(true);
                } else {
                    FXGL.getGameScene().removeUINode(imageView);
                    isImageFollowingCursor.set(false);
                }
            });
            hbox.getChildren().add(texture);
            System.out.println("Texture: " + texture);
        }

        FXGL.getGameScene().getContentRoot().setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY && isImageFollowingCursor.get()) { // Check if right button was clicked and an image is following the cursor
                FXGL.getGameScene().removeUINode(imageView);
                isImageFollowingCursor.set(false);
                System.out.println("Removed image from cursor. By right click.");
            }
        });

        FXGL.getGameScene().getContentRoot().setOnMouseMoved(e -> {
            if (isImageFollowingCursor.get()) {
                imageView.setX(e.getSceneX() - imageView.getImage().getWidth() / 2);
                imageView.setY(e.getSceneY() - imageView.getImage().getHeight() / 2);
            }
        });

        return hbox;
    }
}