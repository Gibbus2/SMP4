package WaveManager;

import java.util.List;

public class Genome { //TODO: find better name??
    private final List<Integer> chromosome;
    private int totalDistance;
    private int removedEntities;
    private final int totalEntities;

    public Genome(List<Integer> chromosome){
        this.chromosome = chromosome;
        this.totalDistance = 0;
        this.removedEntities = 0;

        int sum = 0;
        for (int count : chromosome){
            sum += count;
        }
        totalEntities = sum;
    }
    public boolean isDead(){
        return removedEntities == totalEntities;
    }
    public void entityRemoved(int distance){
        this.totalDistance += distance;
        this.removedEntities++;
    }
    public int fitness(){
        return totalDistance;
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }
}
