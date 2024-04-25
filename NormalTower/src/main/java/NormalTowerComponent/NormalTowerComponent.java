package NormalTowerComponent;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.image.ImageView;
import tower.data.CommonTowerComponent;

public class NormalTowerComponent extends CommonTowerComponent {
    private int towerDamage = 10;
    private int towerPrice = 69;
    private double towerFirerate = 0.2;
    private int towerRange = 100;
    public ImageView image = FXGL.getAssetLoader().loadTexture("NormalTower");

    public NormalTowerComponent() {}

    public CommonTowerComponent createTowerComponent(int towerX, int towerY) {
        return new CommonTowerComponent(towerDamage, towerPrice, towerFirerate, towerRange, towerX, towerY, "Normal");
    }
}
