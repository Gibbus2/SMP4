package ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameMenu extends FXGLMenu {

    public GameMenu() {
        super(MenuType.GAME_MENU);

        var btnPause = new Button("Resume");
        btnPause.getStyleClass().add("play_button");
        btnPause.setOnAction(e -> fireResume());

        var btnExit = new Button("Main Menu");
        btnExit.getStyleClass().add("play_button");
        btnExit.setOnAction(e -> fireExitToMainMenu());

        var btnLevelSelect = new Button("Level Select");
        btnLevelSelect.getStyleClass().add("play_button");
        btnLevelSelect.setOnAction(e -> fireNewGame());


        var box = new VBox(btnPause, btnExit, btnLevelSelect);
        box.setAlignment(Pos.TOP_CENTER);
        box.setTranslateX(getAppWidth() / 2.0 - 100);
        box.setTranslateY(getAppHeight() / 2.0 - 100);

        getContentRoot().getChildren().addAll(box);
    }


}
