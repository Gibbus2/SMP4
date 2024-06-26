package player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import health.HealthComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Entity entity;

    @Test
    public void testCanCreatePlayerEntityWithPlayerComponent() {
        entity = FXGL.entityBuilder()
                .at(0,0)
                .with(new PlayerComponent())
                .build();

        assertTrue(entity.hasComponent(PlayerComponent.class));
        assertTrue(entity.hasComponent(HealthComponent.class));
    }

    @Test
    public void testCreateComponent() {
        PlayerComponent component = new PlayerComponent();
        assertNotNull(component.createComponent());
    }

    @Test
    public void testChangeHealth() {
        entity = FXGL.entityBuilder()
                .at(0,0)
                .with(new PlayerComponent())
                .build();

        int currentHealth = entity.getComponent(HealthComponent.class).getHealth();

        entity.getComponent(PlayerComponent.class).changeHealth(-10);

        assertEquals(currentHealth - 10, entity.getComponent(HealthComponent.class).getHealth());
    }

    @Test
    public void testChangeLedger() {
        entity = FXGL.entityBuilder()
                .at(0,0)
                .with(new PlayerComponent())
                .build();

        int startMoney = entity.getComponent(PlayerComponent.class).getMoney();

        entity.getComponent(PlayerComponent.class).changeMoney(10);

        assertEquals(startMoney + 10, entity.getComponent(PlayerComponent.class).getMoney());
    }
}
