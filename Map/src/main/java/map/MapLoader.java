package map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;

import java.util.Map;
import javafx.scene.text.Text;
import map.MapFactory;
import map.MapLoader;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;

public class MapLoader {
    public MapLoader(){

    }

    public void loadLevel(){
        System.out.println("HERE:" + getAssetLoader().getURL("/levels/lvl0.tmx"));
        FXGL.getGameWorld().addEntityFactory(new MapFactory());
        var level = new TMXLevelLoader(true).load(getAssetLoader().getURL("/levels/lvl0.tmx"), FXGL.getGameWorld());
        FXGL.getGameWorld().setLevel(level);
    }
}
