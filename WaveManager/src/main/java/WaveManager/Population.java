package WaveManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Population {

    private final List<Integer> chromosome;
    private int totalDistance;
    private int removedEntities;
    private final int totalEntities;

    public Population(List<Integer> chromosome) {
        this.chromosome = chromosome;
        this.totalDistance = 0;
        this.removedEntities = 0;

        int sum = 0;
        for (int count : chromosome) {
            sum += count;
        }
        totalEntities = sum;
        System.out.println("Enemies in next wave" + totalEntities);
        //writeToFile("Enemies in next wave: " + totalEntities);
    }

    private void writeToFile(String text) {
        try {
            FileWriter writer = new FileWriter("output.txt", true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDead() {
        return removedEntities == totalEntities;
    }

    public void entityRemoved(int distance) {
        this.totalDistance += distance;
        this.removedEntities++;
    }

    public int fitness() {
        return totalDistance;
    }

    public List<Integer> getChromosome() {
        return chromosome;
    }

}
