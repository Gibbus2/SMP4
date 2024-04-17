package objectPool;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.LinkedList;
import java.util.Queue;

public class Pool {
    private Queue<Entity> pool;
    private ICreateEntityPool createEntityPool;

    public Pool() {
        pool = new LinkedList<>();
    }

    public Entity getEntityFromPool() {
        if (!pool.isEmpty()) {
            Entity entity = pool.poll();

            for (Component component : entity.getComponents()) {
                component.resume();
            }

            return entity;
        } else {
            return createEntityPool.createEntity();
        }
    }

    public void add(Entity entity) {
        pool.add(entity);
    }
}
