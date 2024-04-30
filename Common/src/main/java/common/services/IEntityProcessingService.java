package common.services;

import com.almasb.fxgl.app.scene.GameScene;
import common.data.GameData;

public interface IEntityProcessingService {
    void process(GameData gameData, GameScene gameScene);
}
