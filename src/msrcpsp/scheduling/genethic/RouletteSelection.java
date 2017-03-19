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
    public List<BaseIndividual> spin(List<BaseIndividual> population) {
        List<BaseIndividual> selectedIndividuals = new ArrayList<>();

        double[] cumulativeFitness = new double[population.size()];
        cumulativeFitness[0] = population.get(0).getNormalDuration();
        for (int i = 1; i < population.size(); i++) {
            double fitness = population.get(i).getNormalDuration();
            cumulativeFitness[i] = cumulativeFitness[i - 1] + (1 - fitness);
        }

        double maxFitness = cumulativeFitness[cumulativeFitness.length - 1];
        cumulativeFitness = Arrays.stream(cumulativeFitness).map(s -> s/maxFitness).toArray();

        for (int i = 0; i < population.size()/2; i++) {
            Random random = new Random(System.currentTimeMillis() * (i + 1));
            double randomFitness = random.nextDouble() *
                    cumulativeFitness[cumulativeFitness.length - 1];

            int index = Arrays.binarySearch(cumulativeFitness, randomFitness);
            if (index < 0)
            {
                index = Math.abs(index);
            }

            BaseIndividual individual = population.get(index - 1);
            BaseIndividual candidate = new BaseIndividual(individual.getSchedule(),
                    individual.getSchedule().getEvaluator());
            selectedIndividuals.add(candidate);
        }
//        System.out.println(selectedIndividuals.stream().map(s -> s.getDuration()).collect(Collectors.toList()));
        return selectedIndividuals;
    }
}