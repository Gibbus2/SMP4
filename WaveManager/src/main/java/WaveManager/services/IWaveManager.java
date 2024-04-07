package WaveManager.services;
import com.almasb.fxgl.entity.Entity;
import java.util.List;

public interface IWaveManager {
    //talk with the others if any of this is correct eksdeee
    void startWave();
    void stopWave();
    boolean isWaveComplete();
    List<Entity> getEnemies();
}
