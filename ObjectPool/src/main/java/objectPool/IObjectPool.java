package objectPool;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public interface IObjectPool {
    void createPool(String poolName, ICreateEntityPool iCreateEntityPool);
    Entity getEntityFromPool(String poolName);
    Pool getPool(String poolName);
}