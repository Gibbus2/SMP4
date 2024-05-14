package player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import health.HealthComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private PlayerComponent component;
    private Entity entity;

    @Test
    public void canCreatePlayerEntityWithPlayerComponent() {
        entity = FXGL.entityBuilder()
                .at(0,0)
                .with(new PlayerComponent())
                .build();

        assertTrue(entity.hasComponent(PlayerComponent.class));
        assertTrue(entity.hasComponent(HealthComponent.class));
    }

    @Test
    public void testCreateComponent() {
        this.component = new PlayerComponent();
        assertNotNull(this.component.createComponent());
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

        entity.getComponent(PlayerComponent.class).setMoney(10);

        assertEquals(10, entity.getComponent(PlayerComponent.class).getMoney());
    }
}
