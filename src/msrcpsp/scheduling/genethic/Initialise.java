package msrcpsp.scheduling.genethic;

import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.scheduling.greedy.Greedy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Krystian on 2017-03-11.
 */
public class Initialise {

    private Schedule schedule;
    private int numberOfChildren;
    public Initialise(Schedule sch, int ch) {
        schedule = sch;
        numberOfChildren = ch;
    }
    public List<BaseIndividual> getFirstPopulation(){
        List<BaseIndividual> result = new LinkedList<>();
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i<numberOfChildren;i++) {
            result.add(getOneParent(random));
        }
        return result;
    }

    public BaseIndividual getOneParent(Random random) {
        Schedule sch = new Schedule(schedule);
        int[] upperBounds = sch.getUpperBounds(sch.getTasks().length);
        Task[] tasks = sch.getTasks();
        List<Resource> capableResources;
        Greedy greedy = new Greedy(sch.getSuccesors());
        for (int i = 0; i < tasks.length; ++i) {
            // get resources capable of performing given task
            capableResources = sch.getCapableResources(tasks[i]);
            // assign the task to random capable resource
            sch.assign(tasks[i], capableResources.get((int)(random.nextDouble() * upperBounds[i])));
        }
        greedy.buildTimestamps(sch);
        BaseEvaluator evaluator = new DurationEvaluator(sch);
        BaseIndividual result = new BaseIndividual(sch, evaluator);
        result.setDurationAndCost();
        result.setNormalDurationAndCost();
        return result;
    }


    public List<BaseIndividual> getNextPopulation(List<BaseIndividual> population) {
        RouletteSelection roulette = new RouletteSelection();
        List<BaseIndividual> nextPopulation = roulette.spin(population, numberOfChildren);

        return nextPopulation;
    }
}
