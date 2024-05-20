package WaveManager;

import java.util.ArrayList;
import java.util.List;

public class Generations {
    private final ArrayList<Population> generations;
    private final int chromosomeLength, sizeModifier;
    public Generations(int genomeLength, int sizeModifier){
        this.generations = new ArrayList<>();
        this.chromosomeLength = genomeLength;
        this.sizeModifier = sizeModifier;

        // generate first gene
        reproduce();
    }

    private List<Integer> randomChromosome(){
        System.out.println("Creating random chromosome");
        List<Integer> chromosome = new ArrayList<>();

        for (int i = 0; i < chromosomeLength; i++) {
            int gene = (int) (Math.random() * (sizeModifier + 1));
            chromosome.add(gene);
        }

        if(chromosomeLength > 0) {
            //check for sum zero
            int sum = 0;
            for (Integer gene : chromosome) {
                sum += gene;
            }

            if (sum == 0) {
                chromosome = randomChromosome();
            }
        }

        return chromosome;
    }

    public void reproduce(){
        List<Integer> chromosome;
        // if there is less than 2 generations, create them randomly, else use GA
        if(generations.size() < 2){
            chromosome = randomChromosome();
        }else {
            // select parents
            int parent1Index = rouletteSelection(-1);
            int parent2Index = rouletteSelection(parent1Index);

            Population parent1 = generations.get(parent1Index);
            Population parent2 = generations.get(parent2Index);

            // breed/combine parents TO ONE CHILD
            chromosome = intermediateRecombination(parent1.getChromosome(), parent2.getChromosome());

            // mutate child
            mutate(chromosome);
        }

        // add to generations list
        generations.add(new Population(chromosome));
        System.out.println("New generation added, with chromosome: " + chromosome);
    }

    public Population getLatest(){
     return generations.getLast();
    }

    public int generationsCount(){
        return generations.size();
    }

    private int rouletteSelection(int ignoreIndex){
        int totalFitness = 0;
        for (int i = 0; i < generations.size(); i++) {
            if(i == ignoreIndex) continue;
            totalFitness += generations.get(i).fitness();
        }

        int randValue = (int) (Math.random() * totalFitness);
        int cumulativeFitness = 0;
        for (int i = 0; i < generations.size(); i++) {
            if(i == ignoreIndex) continue;

            cumulativeFitness += generations.get(i).fitness();
            if(cumulativeFitness >= randValue){
                System.out.println("Selected " + i + " as parent");
                return i;
            }
        }

        return 0;
    }

    private List<Integer> intermediateRecombination(List<Integer> chromosome1, List<Integer> chromosome2){
        System.out.println("Combining " + chromosome1 + " with " + chromosome2 + " using intermediate recombination");
        List<Integer> newChromosome = new ArrayList<>();
        for (int i = 0; i < chromosomeLength; i++) {
            double a = -0.5 + (1.5 + 0.5) * Math.random(); //random number between -0.5 and 1.5
            a = Math.round(a * 100.0) / 100.0; //round to two decimal points

            // find the range
            int highest = (chromosome1.get(i) > chromosome2.get(i)) ? chromosome1.get(i) : chromosome2.get(i);
            int lowest = (chromosome1.get(i) < chromosome2.get(i)) ? chromosome1.get(i) : chromosome2.get(i);
            int range = highest - lowest;

            // use 'a' to select the random value in the range as a new gene
            int newGene = (int) (range * a) + lowest;

            newChromosome.add(newGene);
        }
        return newChromosome;
    }

    private void mutate(List<Integer> chromosome){
        //add random value to random gene
        int value = (int) (Math.random() * 5);
        int index = (int) (Math.random() * chromosome.size());
        chromosome.set(index, chromosome.get(index) + value);

        System.out.println("Mutated chromosome on " + index + " with " + value);
    }
}
