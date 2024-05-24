package map;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import common.data.GameData;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class MapLoader {
    private final File location;
    public MapLoader() throws URISyntaxException {
        Path path = Path.of(MapLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().resolve("levels");
        this.location = path.toFile();
        FXGL.getGameWorld().addEntityFactory(new MapFactory());
    }

    public void loadLevel(int levelNumber, GameData gameData) throws MalformedURLException {
        File file = new File(location, "lvl" + levelNumber + ".tmx");
        System.out.println(file.toURI().toURL());
        var level = new TMXLevelLoader(true).load(file.toURI().toURL(), FXGL.getGameWorld());
        FXGL.getGameWorld().setLevel(level);

        NoBuildZone noBuildZone = new NoBuildZone();
        noBuildZone.noBuildZone(gameData);
    }


}
