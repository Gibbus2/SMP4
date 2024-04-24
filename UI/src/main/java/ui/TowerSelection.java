package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.components.CollidableComponent;
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

public class TowerSelection extends HBox {

    private Entity cell;
    public TowerSelection(){
        // ImageView tower1 = new ImageView("assets/Towers/tower1.png");
/*        ImageView imageView = new ImageView();
        InputStream whatEver = getClass().getResourceAsStream("assets/Towers/tower1.png");
        Image fuckNo = new Image(whatEver);
        imageView.setImage(fuckNo);*/


        Rectangle rectangle = new Rectangle(10, 10, Color.RED);
        Rectangle rectangle2 = new Rectangle(10, 10, Color.BLUE);

        rectangle.setTranslateX(0);
        rectangle.setTranslateY(0);
        rectangle2.setTranslateX(0);
        rectangle2.setTranslateY(0);
       // getChildren().addAll(imageView);
/*        FXGL.addUINode(imageView);*/

        System.out.println("TowerMenu Loaded.");

        Label coordinatesLabel = new Label();

// Add an event handler to update the label text with the current mouse coordinates
        setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coordinatesLabel.setText("X: " + event.getX() + ", Y: " + event.getY());
            }
        });

        getChildren().add(coordinatesLabel);

    }

}
