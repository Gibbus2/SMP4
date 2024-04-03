package common.services;

import com.almasb.fxgl.app.scene.GameScene;
import common.data.GameData;

public interface IGamePluginService {
    void start(GameData gameData, GameScene gameScene);

    void stop(GameData gameData, GameScene gameScene);
}
