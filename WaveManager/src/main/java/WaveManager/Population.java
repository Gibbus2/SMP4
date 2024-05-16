package WaveManager;

import java.util.List;

public class Population {
    private final List<Integer> dna;
    private int totalDistance;
    private int removedEntities;
    private final int totalEntities;

    public Population(List<Integer> dna){
        this.dna = dna;
        this.totalDistance = 0;
        this.removedEntities = 0;

        int sum = 0;
        for (int count : dna){
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

    public List<Integer> getDna() {
        return dna;
    }
}
