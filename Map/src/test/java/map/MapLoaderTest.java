package map;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import common.data.GameData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapLoaderTest {

    /*@Test TODO: Fix MapLoaderTest test
    void testLoadLevel() throws URISyntaxException {
        // Mock the dependencies
        GameWorld gameWorld = mock(GameWorld.class);
        MapFactory mapFactory = mock(MapFactory.class);
        TMXLevelLoader tmxLevelLoader = mock(TMXLevelLoader.class);
        NoBuildZone noBuildZone = mock(NoBuildZone.class);

        // When FXGL.getGameWorld() is called, return the mocked gameWorld
        when(FXGL.getGameWorld()).thenReturn(gameWorld);

        // Create the MapLoader and run the test
        MapLoader loader = new MapLoader();
        GameData gameData = new GameData();
        assertDoesNotThrow(() -> loader.loadLevel(0, gameData));

        // Verify that the methods were called on the mocked objects
        Mockito.verify(gameWorld).addEntityFactory((EntityFactory) mapFactory);
        Mockito.verify(gameWorld).setLevel(Mockito.any());
        Mockito.verify(noBuildZone).noBuildZone(gameData);
    }*/
}