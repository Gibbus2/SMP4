package objectPool;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;

import java.util.LinkedList;
import java.util.Queue;

public class Pool {
    private final Queue<Entity> pool;
    private final ICreateEntityPool createEntityPool;

    public int getSize() {
        return pool.size();
    }

    public Pool(ICreateEntityPool createEntityPool) {
        this.createEntityPool = createEntityPool;
        pool = new LinkedList<>();
    }

    public Entity getEntityFromPool() {
        if (!pool.isEmpty()) {
            Entity entity = pool.poll();

            for (Component component : entity.getComponents()) {
                component.resume();
            }

            if (!entity.hasComponent(CollidableComponent.class)) {
                entity.getComponent(CollidableComponent.class).setValue(true);
            }

            return entity;
        } else {
            Entity entity = createEntityPool.createEntity();

            // if the iCreateEntityPool method does not add the
            // PooledObjectComponent to the entity, then we add it here.
            if (!entity.hasComponent(PooledObjectComponent.class)) {
                entity.addComponent(new PooledObjectComponent(this));
            }

            if (!entity.hasComponent(CollidableComponent.class)) {
                entity.getComponent(CollidableComponent.class).setValue(true);
            }

            return entity;
        }
    }

    public void returnEntityToPool(Entity entity) {
        for (Component component : entity.getComponents()) {
            component.pause();
        }

        if (!entity.hasComponent(CollidableComponent.class)) {
            entity.getComponent(CollidableComponent.class).setValue(false);
        }

        entity.setPosition(-10000, -10000);

        pool.add(entity);
    }
}
