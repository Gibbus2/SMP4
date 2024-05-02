package objectPool;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.HashMap;

public class ObjectPool implements IObjectPool {
    public static HashMap<String, Pool> pools;


    public ObjectPool() {
        pools = new HashMap<>();
    }

    @Override
    public void createPool(String poolName, ICreateEntityPool iCreateEntityPool) {
        if (poolExists(poolName)) {
            return;
        }
        pools.put(poolName, new Pool(iCreateEntityPool));
    }

    @Override
    public Pool getPool(String poolName) {
        return pools.get(poolName);
    }

    @Override
    public Entity getEntityFromPool(String poolName) {
        return pools.get(poolName).getEntityFromPool();
    }

    private boolean poolExists(String poolName) {
        return pools.containsKey(poolName);
    }
}