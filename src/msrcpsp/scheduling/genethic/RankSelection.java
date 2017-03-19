package msrcpsp.scheduling.genethic;

import msrcpsp.scheduling.BaseIndividual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Krystian on 2017-03-19.
 */
public class RankSelection {
    public List<BaseIndividual> spin(List<BaseIndividual> population) {
        List<BaseIndividual> selectedIndividuals = new ArrayList<>();
        for (BaseIndividual individual: population) {
            selectedIndividuals.add(
                    new BaseIndividual(individual.getSchedule(),
                            individual.getSchedule().getEvaluator()));
        }
        Collections.sort(selectedIndividuals);
//        System.out.println(selectedIndividuals.stream().map(s->s.getDuration()).collect(Collectors.toList()));
        return selectedIndividuals.subList(0, selectedIndividuals.size() / 2);
    }
}