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
      double probability = 0.05;
      int childrenNumber= chill;
      int loopTimes = loop;
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

    public static void runForTest(String definition, String write) throws IOException {
        double probability = 0.05;
        String outputFile = "writer_"+definition+".csv";
        CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
        MSRCPSPIO reader = new MSRCPSPIO();
        csvOutput.write("test fo:");
        csvOutput.write("children_number");
        csvOutput.endRecord();
        for(int childrenNumber = 100; childrenNumber<= 1000;childrenNumber = childrenNumber +100) {
            
            int loopTimes = 100;
            Schedule schedule2 = reader.readDefinition("assets/def_small/" + definition);
            Initialise init = new Initialise(schedule2, childrenNumber, probability);
            // first population
            List<BaseIndividual> population = init.getFirstPopulation();

            population = init.runAlgorithm(population, loopTimes);
            BaseIndividual result = init.getBestBaseIndividualSchedule(population);
            csvOutput.write(Integer.toString(childrenNumber));
            csvOutput.write(Integer.toString(result.getDuration()));
            csvOutput.endRecord();
            System.out.println("children: "+ childrenNumber+"Duration: " + result.getDuration());
            try {
                reader.write(schedule2, "assets/solutions_small/" + write);
            } catch (IOException e) {
                System.out.print("Writing to a file failed");
            }
        }
        csvOutput.write("test fo:");
        csvOutput.write("loops");
        csvOutput.endRecord();
        for(int loopTimes = 100; loopTimes<= 1000;loopTimes = loopTimes +100) {
            int childrenNumber = 200;
            Schedule schedule2 = reader.readDefinition("assets/def_small/" + definition);
            Initialise init = new Initialise(schedule2, childrenNumber, probability);
            // first population
            List<BaseIndividual> population = init.getFirstPopulation();

            population = init.runAlgorithm(population, loopTimes);
            BaseIndividual result = init.getBestBaseIndividualSchedule(population);
            csvOutput.write(Integer.toString(loopTimes));
            csvOutput.write(Integer.toString(result.getDuration()));
            csvOutput.endRecord();
            System.out.println("loops: "+ loopTimes+"Duration: " + result.getDuration());
            try {
                reader.write(schedule2, "assets/solutions_small/" + write);
            } catch (IOException e) {
                System.out.print("Writing to a file failed");
            }
        }
        csvOutput.close();

    }


  public static void main(String[] args) throws IOException {
      run("10_3_5_3.def","10_3_5_3.sol", 300, 100);
      run("10_5_8_5.def","10_5_8_5.sol", 300, 100);
      run("10_7_10_7.def","10_7_10_7.sol", 300, 100);
      run("15_3_5_3.def","15_3_5_3.sol", 300, 100);
      run("15_6_10_6.def","15_6_10_6.sol", 300, 100);
      run("15_9_12_9.def","15_9_12_9.sol", 300, 100);
      runForTest("10_3_5_3.def","10_3_5_3.sol");

  }

}
