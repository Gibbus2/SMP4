package tower.services;

public interface TowerComponentSPI {
    TowerComponentSPI createTowerComponent(int towerDamage, int towerPrice, double towerFirerate, int towerRange, int towerX, int towerY);
    javafx.scene.image.ImageView getImage();
}
