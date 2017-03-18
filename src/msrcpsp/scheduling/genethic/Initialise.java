package msrcpsp.scheduling.genethic;

import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.scheduling.greedy.Greedy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Krystian on 2017-03-11.
 */
public class Initialise {

    private Schedule schedule;
    private int numberOfChildren;
    private double mutationProb;
    private double crossOverProb;

    public Initialise(Schedule sch, int ch, double m, double c) {
        schedule = sch;
        numberOfChildren = ch;
        mutationProb = m;
        crossOverProb = c;
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

    public List<BaseIndividual> runAlgorithm(List<BaseIndividual> population, int times) {
        List<BaseIndividual> result = population;
        for(int i = 0; i<times; i++) {
            result = getNextPopulation(result);
        }
        return result;
    }


    public List<BaseIndividual> getNextPopulation(List<BaseIndividual> population) {
        RouletteSelection roulette = new RouletteSelection();
        Crossover crossover = new Crossover(crossOverProb);
        Mutation mutation = new Mutation(mutationProb);
        List<BaseIndividual> nextPopulation;
        nextPopulation = roulette.spin(population, numberOfChildren);
        nextPopulation = crossover.getCrossedNextPopulation(nextPopulation);

        for(BaseIndividual elem : nextPopulation) {
            elem = mutation.mutateBaseIndividualSchedule(elem);
        }


        for(BaseIndividual elem : nextPopulation) {
            Greedy greedy = new Greedy(elem.getSchedule().getSuccesors());
            elem.setSchedule(greedy.buildTimestamps(elem.getSchedule()));
            elem.setDurationAndCost();
            elem.setNormalDurationAndCost();
        }
        return nextPopulation;
    }
    public BaseIndividual getBestBaseIndividualSchedule(List<BaseIndividual> schedules) {
        Collections.sort(schedules);
        return schedules.get(0);
    }
}
