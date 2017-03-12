package msrcpsp.scheduling.genethic;

import msrcpsp.scheduling.BaseIndividual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Krystian on 2017-03-12.
 */
public class RouletteSelection {

    public List<BaseIndividual> spin(List<BaseIndividual> population, int selectionSize) {
        // Record the cumulative fitness scores.  It doesn't matter whether the
        // population is sorted or not.  We will use these cumulative scores to work out
        // an index into the population.  The cumulative array itself is implicitly
        // sorted since each element must be greater than the previous one.  The
        // numerical difference between an element and the previous one is directly
        // proportional to the probability of the corresponding candidate in the population
        // being selected.
        double[] cumulativeFitnesses = new double[population.size()];
        cumulativeFitnesses[0] = population.get(0).getNormalDuration();
        Random random = new Random(System.currentTimeMillis());

        for (int i = 1; i < population.size(); i++)
        {
            double fitness = 1 - population.get(i).getNormalDuration();
            cumulativeFitnesses[i] = cumulativeFitnesses[i - 1] + fitness;
        }

        List<BaseIndividual> selection = new ArrayList<>(selectionSize);
        for (int i = 0; i < selectionSize; i++)
        {
            double randomFitness = random.nextDouble() * cumulativeFitnesses[cumulativeFitnesses.length - 1];
            int index = Arrays.binarySearch(cumulativeFitnesses, randomFitness);
            if (index < 0)
            {
                // Convert negative insertion point to array index.
                index = Math.abs(index + 1);
            }
            selection.add(population.get(index));
        }
        return selection;

    }

}
