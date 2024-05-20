package objectPool;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import common.data.EntityType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectPoolTest {
    private ObjectPool objectPool;
    private Entity entity;

    @Test
    public void testCreatePool_WhenCalled_AddsPoolToPools() {
        objectPool = new ObjectPool();

        objectPool.createPool("ENEMY",
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool("ENEMY")))
                        .build()
        );

        assertEquals(1, ObjectPool.pools.size());
    }

    @Test
    public void testCreateEntity_WhenGettingFromEmptyPool() {
        objectPool = new ObjectPool();

        objectPool.createPool("ENEMY",
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool("ENEMY")))
                        .build()
        );

        entity = objectPool.getEntityFromPool("ENEMY");
        assertNotNull(entity);
    }

    @Test
    public void testReturnEntityAndComponentsArePaused_WhenCallingReturnToPool() {
        objectPool = new ObjectPool();

        objectPool.createPool("ENEMY",
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool("ENEMY")))
                        .build()
        );

        entity = objectPool.getEntityFromPool("ENEMY");

        entity.getComponent(PooledObjectComponent.class).returnToPool();

        for (Component component : entity.getComponents()) {
            assertTrue(component.isPaused());
        }

        assertEquals(objectPool.getPool("ENEMY").getSize(), 1);
    }

    @Test
    public void testGetEntityFromPoolAndComponentsAreResumed_WhenGettingFromNonEmptyPool() {
        objectPool = new ObjectPool();

        objectPool.createPool("ENEMY",
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool("ENEMY")))
                        .build()
        );

        objectPool.getEntityFromPool("ENEMY").getComponent(PooledObjectComponent.class).returnToPool();

        entity = objectPool.getEntityFromPool("ENEMY");

        for (Component component : entity.getComponents()) {
            assertFalse(component.isPaused());
        }

        assertNotNull(entity);
    }

    @Test
    public void testPerformanceTest_WhenUsingDifferentEntityGettersOrBuilders() {
        objectPool = new ObjectPool();

        objectPool.createPool("ENEMY",
                () -> FXGL.entityBuilder()
                        .type(EntityType.ENEMY)
                        .with(new PooledObjectComponent(objectPool.getPool("ENEMY")))
                        .build()
        );


        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            FXGL.entityBuilder()
                    .type(EntityType.ENEMY)
                    .build();
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            objectPool.getEntityFromPool("ENEMY");
        }
        endTime = System.currentTimeMillis();
        long duration2 = endTime - startTime;


        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            objectPool.getEntityFromPool("ENEMY").getComponent(PooledObjectComponent.class).returnToPool();
        }
        endTime = System.currentTimeMillis();
        long duration3 = endTime - startTime;

        System.out.println("Creating new Entity every time: " + duration + "ms");
        System.out.println("Using empty ObjectPool: " + duration2 + "ms");
        System.out.println("Using full ObjectPool: " + duration3 + "ms");

    }
}
