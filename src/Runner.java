import msrcpsp.evaluation.BaseEvaluator;
import msrcpsp.evaluation.DurationEvaluator;
import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Resource;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.Task;
import msrcpsp.scheduling.genethic.Initialise;
import msrcpsp.scheduling.greedy.Greedy;
import msrcpsp.validation.BaseValidator;
import msrcpsp.validation.CompleteValidator;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runner class to help with understanding of the library.
 */
public class Runner {


  public static void run(String definition, String write) {
      double probability = 0.1;
      int childrenNumber= 100;
      int loopTimes = 100;
      MSRCPSPIO reader = new MSRCPSPIO();

      Schedule schedule2 = reader.readDefinition("assets/def_small/"+definition);
      Initialise init = new Initialise(schedule2,childrenNumber, probability);
      // first population
      List<BaseIndividual> population = init.getFirstPopulation();
      BaseValidator validator = new CompleteValidator();

      population = init.runAlgorithm(population, loopTimes);
      BaseIndividual result = init.getBestBaseIndividualSchedule(population);


      System.out.println("--------------");
      System.out.println("data: "+definition);
      System.out.println("mutation probability: "+probability);
      System.out.println("number of children: "+childrenNumber);
      System.out.println("loops: "+loopTimes);
      System.out.println("valid: "+validator.validate(result.getSchedule()));
      System.out.println("Error list: "+validator.getErrorMessages());
      System.out.println("NormalDuration: " +result.getNormalDuration());
      System.out.println("Duration: " +result.getDuration());
      try {
          reader.write(schedule2, "assets/solutions_small/"+write);
      } catch (IOException e) {
          System.out.print("Writing to a file failed");
      }
  }

  public static void main(String[] args) {
      run("10_3_5_3.def","10_3_5_3.sol");
      run("10_5_8_5.def","10_5_8_5.sol");
      run("10_7_10_7.def","10_7_10_7.sol");
      run("15_3_5_3.def","15_3_5_3.sol");
      run("15_6_10_6.def","15_6_10_6.sol");
      run("15_9_12_9.def","15_9_12_9.sol");

  }

}
