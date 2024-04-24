package objectPool;

import com.almasb.fxgl.entity.Entity;
import common.data.EntityType;

public interface IObjectPool {
    void createPool(EntityType entityType, ICreateEntityPool iCreateEntityPool);
    Entity getEntityFromPool(EntityType entityType);
    Pool getPool(EntityType entityType);
}