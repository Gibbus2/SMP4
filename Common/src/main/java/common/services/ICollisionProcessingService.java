package common.services;

import com.almasb.fxgl.app.scene.GameScene;
import common.data.GameData;

public interface ICollisionProcessingService {
    void process(GameData gameData, GameScene gameScene);
}
