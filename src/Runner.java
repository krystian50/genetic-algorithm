import msrcpsp.io.MSRCPSPIO;
import msrcpsp.scheduling.BaseIndividual;
import msrcpsp.scheduling.Schedule;
import msrcpsp.scheduling.genethic.Initialise;
import msrcpsp.validation.BaseValidator;
import msrcpsp.validation.CompleteValidator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.csvreader.CsvWriter;

/**
 * Runner class to help with understanding of the library.
 */
public class Runner {


  public static void run(String definition, String write, int chill, int loop) {
      double mutProbability = 0.005;
      double crossProbability = 0.75;
      int childrenNumber= chill;
      int loopTimes = loop;
      MSRCPSPIO reader = new MSRCPSPIO();

      Schedule schedule2 = reader.readDefinition("assets/def_big/"+definition);
      Initialise init = new Initialise(schedule2,childrenNumber, mutProbability, crossProbability);
      // first population
      List<BaseIndividual> population = init.getFirstPopulation();
      BaseValidator validator = new CompleteValidator();

      population = init.runAlgorithm(population, loopTimes);
      BaseIndividual result = init.getBestBaseIndividualSchedule(population);


      System.out.println("--------------");
      System.out.println("data: "+definition);
      System.out.println("mutation probability: "+mutProbability);
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

    public static void runForTest(String definition, String write) throws IOException {
        double probability = 0;
        String outputFile = "children_and_loops.csv";
        CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ';');
        MSRCPSPIO reader = new MSRCPSPIO();
        csvOutput.write("test for:");
        csvOutput.write(definition);
        csvOutput.endRecord();
//        csvOutput.write("value:");
//        csvOutput.write("children_number");
//        csvOutput.endRecord();
//        for(int childrenNumber = 25; childrenNumber<= 1000;childrenNumber = childrenNumber +25) {
//
//            int loopTimes = 100;
//            Schedule schedule2 = reader.readDefinition("assets/def_big/" + definition);
//            Initialise init = new Initialise(schedule2, childrenNumber, probability, 0.75);
//            // first population
//            List<BaseIndividual> population = init.getFirstPopulation();
//
//            population = init.runAlgorithm(population, loopTimes);
//            BaseIndividual result = init.getBestBaseIndividualSchedule(population);
//            csvOutput.write(Integer.toString(childrenNumber));
//            csvOutput.write(Integer.toString(result.getDuration()));
//            csvOutput.endRecord();
//            System.out.println("children: "+ childrenNumber+" Duration: " + result.getDuration());
//            try {
//                reader.write(schedule2, "assets/solutions_small/" + write);
//            } catch (IOException e) {
//                System.out.print("Writing to a file failed");
//            }
//        }
        csvOutput.write("value:");
        csvOutput.write("loops");
        csvOutput.endRecord();
        for(int loopTimes = 1; loopTimes<= 300;loopTimes = loopTimes +10) {
            int childrenNumber = 100;
            Schedule schedule2 = reader.readDefinition("assets/def_big/" + definition);
            Initialise init = new Initialise(schedule2, childrenNumber, probability, 0.95);
            // first population
            List<BaseIndividual> population = init.getFirstPopulation();

            population = init.runAlgorithm(population, loopTimes);
            BaseIndividual result = init.getBestBaseIndividualSchedule(population);
            csvOutput.write(Integer.toString(loopTimes));
            csvOutput.write(Integer.toString(result.getDuration()));
            csvOutput.endRecord();
            System.out.println("loops: "+ loopTimes+" Duration: " + result.getDuration());
            try {
                reader.write(schedule2, "assets/solutions_small/" + write);
            } catch (IOException e) {
                System.out.print("Writing to a file failed");
            }
        }
        csvOutput.close();

    }


  public static void main(String[] args) throws IOException {
//      run("10_3_5_3.def","10_3_5_3.sol", 100, 100);
//      run("10_5_8_5.def","10_5_8_5.sol", 100, 100);
//      run("10_7_10_7.def","10_7_10_7.sol", 100, 100);
//      run("15_3_5_3.def","15_3_5_3.sol", 100, 100);
//      run("15_6_10_6.def","15_6_10_6.sol", 100, 100);
      runForTest("100_5_22_15.def","15_9_12_9.sol");
//      runForTest("100_10_47_9.def","15_9_12_9.sol");
//      runForTest("100_10_48_15.def","15_9_12_9.sol");
//      runForTest("100_20_22_15.def","15_9_12_9.sol");
//      runForTest("100_20_47_9.def","15_9_12_9.sol");
//      runForTest("100_20_65_15.def","15_9_12_9.sol");
//      runForTest("10_3_5_3.def","10_3_5_3.sol");
//      runForTest("10_5_8_5.def","10_5_8_5.sol");
//      runForTest("10_7_10_7.def","10_7_10_7.sol");
//      runForTest("15_3_5_3.def","15_3_5_3.sol");
//      runForTest("15_6_10_6.def","15_6_10_6.sol");
//      runForTest("15_9_12_9.def","15_9_12_9.sol");

  }

}
