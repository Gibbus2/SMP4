package ui;

import com.almasb.fxgl.dsl.FXGL;
import common.data.ShowButtonTrigger;
import common.data.StartWaveTrigger;
import common.player.PlayerSPI;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class GameInformation {
    private Button startWaveButton;


    public void startWaveUI() {
        startWaveButton = new Button("Start Wave");


        startWaveButton.setTranslateX(1340);
        startWaveButton.setTranslateY(730);
        startWaveButton.setPrefSize(90,90);
        startWaveButton.setOnAction(e -> {
            FXGL.getEventBus().fireEvent(new StartWaveTrigger());
            startWaveButton.setVisible(false);
        });
        FXGL.getGameScene().addUINode(startWaveButton);

        //waveCounter text


    }
    public void showButton(){
        FXGL.getEventBus().addEventHandler(ShowButtonTrigger.ANY, event -> {
            startWaveButton.setVisible(true);
        });
    }
    public VBox displayInformation(){
        VBox vbox = new VBox();
        vbox.setLayoutX(1150);
        vbox.setLayoutY(725);
        vbox.setPrefSize(50, 88);

        Text moneyText = new Text();
        Text healthText = new Text();
        Text waveCounterText = new Text();

        moneyText.setFill(Color.BLACK);
        moneyText.setStyle("-fx-font: 24 arial;");
        healthText.setFill(Color.BLACK);
        healthText.setStyle("-fx-font: 24 arial;");
        waveCounterText.setFill(Color.BLACK);
        waveCounterText.setStyle("-fx-font: 24 arial;");
        vbox.getChildren().add(moneyText);
        vbox.getChildren().add(healthText);
        vbox.getChildren().add(waveCounterText);
        return vbox;
    }
    private Collection<? extends PlayerSPI> player() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
