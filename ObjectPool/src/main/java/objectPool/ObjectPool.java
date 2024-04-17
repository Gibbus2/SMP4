package objectPool;

import com.almasb.fxgl.entity.Entity;
import common.data.EntityType;

import java.util.HashMap;

public class ObjectPool {
    private static HashMap<EntityType, Pool> pools = new HashMap<>();

    public static void createPool(EntityType entityType, ICreateEntityPool iCreateEntityPool) {
        pools.put(entityType, new Pool());
    }

    public static Entity getEntityFromPool(EntityType entityType) {
        return pools.get(entityType).getEntityFromPool();
    }
}
