package objectPool;

import com.almasb.fxgl.entity.Entity;
import common.data.EntityType;

import java.util.HashMap;

public class ObjectPool implements IObjectPool {
    public static HashMap<EntityType, Pool> pools;


    public ObjectPool() {
        pools = new HashMap<>();
    }

    @Override
    public void createPool(EntityType entityType, ICreateEntityPool iCreateEntityPool) {
        pools.put(entityType, new Pool(iCreateEntityPool));
    }

    @Override
    public Pool getPool(EntityType entityType) {
        return pools.get(entityType);
    }

    @Override
    public Entity getEntityFromPool(EntityType entityType) {
        return pools.get(entityType).getEntityFromPool();
    }
}