package waveManager;

import WaveManager.Genome;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenomeTest {
    @Test
    public void testRemoveEntity(){
        List<Integer> chromosome = new ArrayList<>();
        chromosome.add(2);
        chromosome.add(1);
        Genome genome = new Genome(chromosome);
        assertFalse(genome.isDead());

        genome.entityRemoved(0);
        assertFalse(genome.isDead());

        genome.entityRemoved(0);
        assertFalse(genome.isDead());

        genome.entityRemoved(0);
        assertTrue(genome.isDead());
    }

    @Test
    public void testFitness(){
        Genome genome = new Genome(new ArrayList<>());
        assertEquals(0, genome.fitness());

        genome.entityRemoved(5);
        assertEquals(5, genome.fitness());

        genome.entityRemoved(10);
        assertEquals(15, genome.fitness());
    }

    @Test
    public void testGetChromosome(){
        List<Integer> chromosome = new ArrayList<>();
        chromosome.add(6);
        chromosome.add(2);

        Genome genome = new Genome(chromosome);

        assertEquals(chromosome, genome.getChromosome());
    }
}
