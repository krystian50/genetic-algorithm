package msrcpsp.scheduling.genethic;

import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Krystian on 2017-03-11.
 */
public class Crossover {

    /**
     *
     * @param population - selected population by roulette
     * @return
     */
    public List<BaseIndividual> getCrossedNextPopulation(List<BaseIndividual> population) {
        List<BaseIndividual> result = new LinkedList<>();
        for(int i = 0; i<population.size(); i = i + 2) {
            BaseIndividual[] children = getNextSiblings(population.get(i), population.get(i+1));
            result.add(children[0]);
            result.add(children[1]);
        }
        return result;
    }

    public BaseIndividual[] getNextSiblings(BaseIndividual parent1, BaseIndividual parent2) {
        BaseIndividual[] result = new BaseIndividual[2];
        result[0] = getNextChild(parent1, parent2);
        result[1] = getNextChild(parent2, parent1);
        return result;
    }

    public BaseIndividual getNextChild(BaseIndividual parent1, BaseIndividual parent2) {
        BaseIndividual child = new BaseIndividual(parent1.getSchedule(),
                parent1.getSchedule().getEvaluator());

        Schedule childSchedule = child.getSchedule();
        Schedule secondSchedule = parent2.getSchedule();
        Task[] tasks = childSchedule.getTasks();
        Task[] parentTasks = secondSchedule.getTasks();

        for (int i = tasks.length/2; i < tasks.length; i++) {
            Task currentTask = tasks[i];
            Task parentTask = parentTasks[i];
//            System.out.println(currentTask.getId() + ") " + currentTask.getResourceId() + " -> " + parentTask.getResourceId());

//            System.out.println(currentTask);
            childSchedule.assign(currentTask,
                    childSchedule.getResource(parentTask.getResourceId()));
//            System.out.println(currentTask);
        }
        return child;
    }
}
