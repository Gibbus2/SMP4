package common.services;

import com.almasb.fxgl.app.scene.GameScene;
import com.almasb.fxgl.physics.PhysicsWorld;
import common.data.GameData;

public interface IGamePluginService {
    void start(GameData gameData, GameScene gameScene, PhysicsWorld physicsWorld);

    void stop(GameData gameData, GameScene gameScene);
}
