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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;
import javafx.scene.text.Text;
import map.MapFactory;
import map.MapLoader;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;

public class MapLoader {
    private final File location;
    public MapLoader() throws URISyntaxException {
        Path path = Path.of(MapLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().resolve("levels");
        this.location = path.toFile();
        FXGL.getGameWorld().addEntityFactory(new MapFactory());
    }

    public void loadLevel(int levelNumber) throws MalformedURLException {
        File file = new File(location, "lvl" + levelNumber + ".tmx");
        System.out.println(file.toURI().toURL());
        var level = new TMXLevelLoader(true).load(file.toURI().toURL(), FXGL.getGameWorld());
        FXGL.getGameWorld().setLevel(level);
    }
}
