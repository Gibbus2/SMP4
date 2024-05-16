package waveManager;

import WaveManager.Population;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationTest {
    @Test
    public void testRemoveEntity(){
        List<Integer> chromosome = new ArrayList<>();
        chromosome.add(2);
        chromosome.add(1);
        Population population = new Population(chromosome);
        assertFalse(population.isDead());

        population.entityRemoved(0);
        assertFalse(population.isDead());

        population.entityRemoved(0);
        assertFalse(population.isDead());

        population.entityRemoved(0);
        assertTrue(population.isDead());
    }

    @Test
    public void testFitness(){
        Population population = new Population(new ArrayList<>());
        assertEquals(0, population.fitness());

        population.entityRemoved(5);
        assertEquals(5, population.fitness());

        population.entityRemoved(10);
        assertEquals(15, population.fitness());
    }

    @Test
    public void testGetChromosome(){
        List<Integer> chromosome = new ArrayList<>();
        chromosome.add(6);
        chromosome.add(2);

        Population population = new Population(chromosome);

        assertEquals(chromosome, population.getDna());
    }
}
