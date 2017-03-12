package msrcpsp.scheduling.genethic;

import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Krystian on 2017-03-11.
 */
public class Mutation {
    private double probability;

    public Mutation(double p) {
        probability = p;
    }

    public BaseIndividual mutateBaseIndividualSchedule(BaseIndividual base) {
        Task[] tasks = base.getSchedule().getTasks();
        for( Task elem : tasks) {
            elem = mutateTask(elem, base.getSchedule().getCapableResources(elem));
        }
        return base;
    }

    public Task mutateTask(Task task, List<Resource> resources) {
        double randomNum = ThreadLocalRandom.current().nextDouble(100);
        List<Integer> resourcesId = new LinkedList<>();
        for( Resource elem : resources) {
            if (elem.getId() != task.getResourceId()) {
                resourcesId.add(elem.getId());
            }
        }
        if(randomNum <= probability) {
            if (resourcesId.size() != 0) {
                int id = ThreadLocalRandom.current().nextInt(0, resourcesId.size());
                task.setResourceId(resourcesId.get(id));
            }
        }
        return task;
    }
}
