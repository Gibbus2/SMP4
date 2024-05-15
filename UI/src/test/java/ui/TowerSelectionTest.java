package ui;

import common.data.GameData;
import javafx.scene.layout.HBox;
import objectPool.IObjectPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class TowerSelectionTest {
    private TowerSelection towerSelection;
    private IObjectPool objectPool;
    private GameData gameData;

    @BeforeEach
    void setUp() {
        objectPool = Mockito.mock(IObjectPool.class);
        gameData = Mockito.mock(GameData.class);
        towerSelection = new TowerSelection();
    }

    @Test
    void testGetTowers() {
        HBox result = towerSelection.getTowers();
        assertNotNull(result);
        System.out.println("GetTowers test : " + result);
    }

    @Test
    void testCreateTowerSelection() {
        HBox result = towerSelection.createTowerSelection(objectPool, gameData);
        assertNotNull(result);
        System.out.println("CreateTowerSelection test : " + result);
    }

    @AfterEach
    void tearDown() {
        towerSelection = null;
        objectPool = null;
        gameData = null;
    }

}