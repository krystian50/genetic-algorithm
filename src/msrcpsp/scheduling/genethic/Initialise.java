package msrcpsp.scheduling.genethic;

import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;

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
    public List<Schedule> getSchedulesList(){
        List<Schedule> result = new LinkedList<>();
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i<numberOfChildren;i++) {
            result.add(getOneSchedule(i, random));
        }
        return result;
    }

    public Schedule getOneSchedule(int index, Random random) {
        Schedule result = new Schedule(schedule);
        int[] upperBounds = result.getUpperBounds(result.getTasks().length);
        Task[] tasks = result.getTasks();
        List<Resource> capableResources;
        for (int i = 0; i < tasks.length; ++i) {
            // get resources capable of performing given task
            capableResources = result.getCapableResources(tasks[i]);
            // assign the task to random capable resource
            result.assign(tasks[i], capableResources.get((int)(random.nextDouble() * upperBounds[i])));
        }
        return result;
    }
}
