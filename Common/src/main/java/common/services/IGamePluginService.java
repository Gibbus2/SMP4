package common.services;

import common.data.GameData;
import common.data.GameScene;

public interface IGamePluginService {
    void start(GameData gameData, GameScene gameScene);

    void stop(GameData gameData, GameScene gameScene);
}
