package ui;

import com.almasb.fxgl.dsl.FXGL;
import common.data.ShowButtonTrigger;
import common.data.StartWaveTrigger;
import common.player.PlayerSPI;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class GameInformation {
    private Button startWaveButton;


    public void startWaveUI() {
        startWaveButton = new Button("Start Wave");


        startWaveButton.setTranslateX(50);
        startWaveButton.setTranslateY(50);
        startWaveButton.setOnAction(e -> {
            FXGL.getEventBus().fireEvent(new StartWaveTrigger());
            startWaveButton.setVisible(false);
        });
        FXGL.getGameScene().addUINode(startWaveButton);

        //waveCounter text
        Text waveCounterText = new Text();
        waveCounterText.setTranslateX(50);
        waveCounterText.setTranslateY(150);
        waveCounterText.textProperty().bind(FXGL.getWorldProperties().intProperty("currentWave").asString("Wave: %d"));

        FXGL.getGameScene().addUINode(waveCounterText);

    }
    public void showButton(){
        FXGL.getEventBus().addEventHandler(ShowButtonTrigger.ANY, event -> {
            startWaveButton.setVisible(true);
        });
    }
    public HBox displayInformation(){
        HBox hbox = new HBox();
        hbox.setLayoutX(1300);
        hbox.setLayoutY(720);
        hbox.setPrefSize(100, 88);

        Text text = new Text();
        text.setText("Money: " + player().stream().findFirst().get().getMoney());
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font: 24 arial;");
        hbox.getChildren().add(text);
        return hbox;
    }
    private Collection<? extends PlayerSPI> player() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
