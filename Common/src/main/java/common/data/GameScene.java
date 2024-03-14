package common.data;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameScene {
    private final Map<UUID, Entity> entityMap = new ConcurrentHashMap<>();

    public UUID addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }
    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }
}
