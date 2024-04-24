import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import common.data.EntityType;
import objectPool.PooledObjectComponent;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import objectPool.ObjectPool;

public class ObjectPoolTest {
    private ObjectPool objectPool;
    private Entity entity;

    @Test
    void createPool_WhenCalled_AddsPoolToPools() {
        objectPool = new ObjectPool();

        objectPool.createPool(EntityType.ENEMY,
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool(EntityType.ENEMY)))
                        .build()
        );

        assertEquals(1, ObjectPool.pools.size());
    }

    @Test
    void createEntity_WhenGettingFromEmptyPool() {
        objectPool = new ObjectPool();

        objectPool.createPool(EntityType.ENEMY,
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool(EntityType.ENEMY)))
                        .build()
        );

        entity = objectPool.getEntityFromPool(EntityType.ENEMY);
        assertNotNull(entity);
    }

    @Test
    void returnEntityAndComponentsArePaused_WhenCallingReturnToPool() {
        objectPool = new ObjectPool();

        objectPool.createPool(EntityType.ENEMY,
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool(EntityType.ENEMY)))
                        .build()
        );

        entity = objectPool.getEntityFromPool(EntityType.ENEMY);

        entity.getComponent(PooledObjectComponent.class).returnToPool();

        for (Component component : entity.getComponents()) {
            assertTrue(component.isPaused());
        }

        assertEquals(objectPool.getPool(EntityType.ENEMY).getSize(), 1);
    }

    @Test
    void getEntityFromPoolAndComponentsAreResumed_WhenGettingFromNonEmptyPool() {
        objectPool = new ObjectPool();

        objectPool.createPool(EntityType.ENEMY,
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool(EntityType.ENEMY)))
                        .build()
        );

        objectPool.getEntityFromPool(EntityType.ENEMY).getComponent(PooledObjectComponent.class).returnToPool();

        entity = objectPool.getEntityFromPool(EntityType.ENEMY);

        for (Component component : entity.getComponents()) {
            assertFalse(component.isPaused());
        }

        assertNotNull(entity);
    }
}