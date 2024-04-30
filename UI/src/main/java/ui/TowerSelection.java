package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.UI;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.util.Arrays;

public class TowerSelection extends HBox {

    private Entity cell;
    public TowerSelection(){

        setLayoutX(0);
        setLayoutY(720);
        setPrefSize(1440, 88);


        Rectangle rectangle = new Rectangle(10, 10, Color.RED);
        Rectangle rectangle2 = new Rectangle(10, 10, Color.BLUE);

        rectangle.setTranslateX(0);
        rectangle.setTranslateY(0);
        rectangle2.setTranslateX(1420);
        rectangle2.setTranslateY(78);


        ImageLoader imageLoader = new ImageLoader();

        /*
        System.out.println("From TowerSelection"+ Arrays.toString(imageLoader.getTextures()));*/

    /*    for (Texture texture : imageLoader.getTextures()) {
            System.out.println("Texture: ");
        }*/


        InputStream is = TowerSelection.class.getResourceAsStream("/assets/tower2.png");
        Image img = new Image(is);
        Texture texture = new Texture(img);
        getChildren().add(texture);
        System.out.println(Arrays.toString(imageLoader.getTextures()));
        System.out.println("Texture: " + texture.toString());


        System.out.println("TowerMenu Loaded.");
        System.out.println(Arrays.toString(imageLoader.getTextures()));
        /*
        Texture[] textures = imageLoader.getTextures();
        System.out.println("Textures: " + Arrays.toString(textures));*/
/*        for (Texture texture : textures) {
            getChildren().add(texture);
        }*/

        getChildren().addAll(rectangle, rectangle2);






    }

/*    public static void main(String[] args) {
        ImageLoader imageLoader = new ImageLoader();
        System.out.println(Arrays.toString(imageLoader.getTextures()));
    }*/

}
