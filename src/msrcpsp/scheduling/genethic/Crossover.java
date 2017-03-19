package msrcpsp.scheduling.genethic;

import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Krystian on 2017-03-11.
 */
public class Crossover {

    /**
     *
     * @param population - selected population by roulette
     * @return
     */

    private double probability;

    public Crossover(double p) {
        probability = p;
    }


    public List<BaseIndividual> getCrossedNextPopulation(List<BaseIndividual> population) {
        List<BaseIndividual> result = new LinkedList<>();
        Random generator = new Random();
        for(int i = 0; i<population.size(); i++) {
            int index1 = generator.nextInt(population.size() - 1);
            int index2 = generator.nextInt(population.size() - 1);
            BaseIndividual[] children = getNextSiblings(population.get(index1), population.get(index2));
            result.add(children[0]);
            result.add(children[1]);
        }
        return result;
    }

    public BaseIndividual[] getNextSiblings(BaseIndividual firstParent, BaseIndividual secondParent) {
        Random rng = new Random(System.currentTimeMillis());
        BaseIndividual firstChild, secondChild;
        BaseIndividual[] result = new BaseIndividual[2];
        double p = rng.nextDouble();
        if (p <= probability) {
            firstChild = getNextChild(firstParent, secondParent);
            secondChild = getNextChild(secondParent, firstParent);
        }else{
            firstChild = new BaseIndividual(firstParent.getSchedule(),
                    firstParent.getSchedule().getEvaluator());
            secondChild = new BaseIndividual(secondParent.getSchedule(),
                    secondParent.getSchedule().getEvaluator());
        }
        result[0] = firstChild;
        result[1] = secondChild;

        return result;
    }

    private BaseIndividual getNextChild(BaseIndividual firstParent,
                                       BaseIndividual secondParent) {
        BaseIndividual child = new BaseIndividual(firstParent.getSchedule(),
                firstParent.getSchedule().getEvaluator());
        assert child.getSchedule() != firstParent.getSchedule();
        Schedule childSchedule = child.getSchedule();
        Schedule secondSchedule = secondParent.getSchedule();
        Task[] tasks = childSchedule.getTasks();
        Task[] parentTasks = secondSchedule.getTasks();

        Random rng = new Random(System.currentTimeMillis());

        int offset = rng.nextInt(tasks.length/3 - 1);
        boolean positive = rng.nextBoolean();
        offset = positive ? offset : -offset;

        int cuttingPoint = tasks.length/2 + offset;

        int j = 0;
        for (int i = cuttingPoint; i < tasks.length; i++) {
            Task currentTask = tasks[i];
            Task parentTask = parentTasks[i];
//            System.out.println(currentTask.getId() + ") " + currentTask.getResourceId() + " -> " + parentTask.getResourceId());
            j = currentTask.getResourceId() != parentTask.getResourceId() ? j + 1: j;
//            System.out.println(currentTask);
            childSchedule.assign(currentTask,
                    childSchedule.getResource(parentTask.getResourceId()));
//            System.out.println(currentTask);
        }
//        System.out.println("Changed: " + j);

        return child;
    }
}
