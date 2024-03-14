package common.services;

import common.data.GameData;
import common.data.GameScene;

public interface ICollisionProcessingService {
    void process(GameData gameData, GameScene gameScene);
}
