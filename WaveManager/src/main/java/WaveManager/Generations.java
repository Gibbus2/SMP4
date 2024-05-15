package WaveManager;

import java.util.ArrayList;
import java.util.List;

public class Generations {
    private final ArrayList<Population> generations;
    private final int dnaLength, sizeModifier;
    public Generations(int genomeLength, int sizeModifier){
        this.generations = new ArrayList<>();
        this.dnaLength = genomeLength;
        this.sizeModifier = sizeModifier;

        // generate first gene
        reproduce();
    }

    private List<Integer> randomDna(){
        System.out.println("Creating random dna");
        List<Integer> dna = new ArrayList<>();

        for (int i = 0; i < dnaLength; i++) {
            int gene = (int) (Math.random() * (sizeModifier + 1));
            dna.add(gene);
        }

        if(dnaLength > 0) {
            //check for sum zero
            int sum = 0;
            for (Integer gene : dna) {
                sum += gene;
            }

            if (sum == 0) {
                dna = randomDna();
            }
        }

        return dna;
    }

    public void reproduce(){
        List<Integer> dna;
        // if there is less than 2 generations, create them randomly, else use GA
        if(generations.size() < 2){
            dna = randomDna();
        }else {
            // select parents
            int parent1Index = rouletteSelection(-1);
            int parent2Index = rouletteSelection(parent1Index);

            Population parent1 = generations.get(parent1Index);
            Population parent2 = generations.get(parent2Index);

            // breed/combine parents TO ONE CHILD
            dna = intermediateRecombination(parent1.getDna(), parent2.getDna());

            // mutate child
            mutate(dna);
        }

        // add to generations list
        generations.add(new Population(dna));
        System.out.println("New generation added, with dna: " + dna);
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

    private List<Integer> intermediateRecombination(List<Integer> dna1, List<Integer> dna2){
        System.out.println("Combining " + dna1 + " with " + dna2 + " using intermediate recombination");
        List<Integer> newDna = new ArrayList<>();
        for (int i = 0; i < dnaLength; i++) {
            double a = -0.5 + (1.5 + 0.5) * Math.random(); //random number between -0.25 and 1.25
            a = Math.round(a * 100.0) / 100.0; //round to two decimal points

            // find the range
            int highest = (dna1.get(i) > dna2.get(i)) ? dna1.get(i) : dna2.get(i);
            int lowest = (dna1.get(i) < dna2.get(i)) ? dna1.get(i) : dna2.get(i);
            int range = highest - lowest;

            // use 'a' to select the random value in the range as a new gene
            int newGene = (int) (range * a) + lowest;

            newDna.add(newGene);
        }
        return newDna;
    }

    private void mutate(List<Integer> dna){
        //add random value to random gene
        int value = (int) (Math.random() * 5);
        int index = (int) (Math.random() * dna.size());
        dna.set(index, dna.get(index) + value);

        System.out.println("Mutated dna on " + index + " with " + value);
    }
}
