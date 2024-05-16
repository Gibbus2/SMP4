package PlayerSPI;

import com.almasb.fxgl.dsl.FXGL;
import common.player.PlayerSPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerSPITest {

    @BeforeEach
    public void playerSPIExists() {
        Assertions.assertFalse(getPlayerSPIs().isEmpty());
    }

    @Test
    public void canCreatePlayerEntityWithPlayerComponent() {
        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> {
                    FXGL.entityBuilder()
                            .at(0,0)
                            .with(spi.createComponent())
                            .build();

                    Assertions.assertNotNull(spi.getPlayerEntity());
                    Assertions.assertNotNull(spi.getPlayerEntity().getComponent(spi.createComponent().getClass()));
                }
        );
    }

    @Test
    public void testCreateComponent() {
        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> {
                    Assertions.assertNotNull(spi.createComponent());
                }
        );
    }

    @Test
    public void testChangeHealth() {
        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> {
                    FXGL.entityBuilder()
                            .at(0,0)
                            .with(spi.createComponent())
                            .build();

                    int currentHealth = spi.getHealth();
                    spi.changeHealth(-10);
                    Assertions.assertEquals(currentHealth-10, spi.getHealth());
                }
        );
    }

    @Test
    public void testChangeLedger() {
        getPlayerSPIs().stream().findFirst().ifPresent(
                spi -> {
                    spi.changeMoney(10);
                    Assertions.assertEquals(10, spi.getMoney());    ;
                }
        );
    }

    private Collection<? extends PlayerSPI> getPlayerSPIs() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
