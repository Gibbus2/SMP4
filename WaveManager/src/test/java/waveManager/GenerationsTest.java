package waveManager;

import WaveManager.Generations;
import WaveManager.Population;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GenerationsTest {

    @Test
    public void testInitialization(){
        Generations generations = new Generations(4, 11);

        List<Integer> chromosome = generations.getLatest().getChromosome();
        assertEquals(4, chromosome.size());

        assertEquals(1, generations.generationsCount());
    }

    @Test
    public void testReproduce(){
        Generations generations = new Generations(4,11);
        Population initial = generations.getLatest();

        generations.reproduce(); // random chromosome

        generations.reproduce(); // reproduction

        assertNotEquals(initial, generations.getLatest());
    }

}
