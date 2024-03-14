package common.services;

import common.data.GameData;
import common.data.GameScene;

public interface IEntityProcessingService {
    void process(GameData gameData, GameScene gameScene);
}
