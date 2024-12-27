import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ChargerUptime {
  static class Report implements Comparable<Report> {
    long startTime, endTime;
    boolean up;

    Report(long startTime, long endTime, boolean up) {
      this.startTime = startTime;
      this.endTime = endTime;
      this.up = up;
    }

    @Override
    public int compareTo(Report other) {
      // Will help when merging overlapping uptime reports
      return Long.signum(startTime == other.startTime ? endTime - other.endTime : startTime - other.startTime);
    }
  }

  /**
   * Print "ERROR" in `stdout` and the respective message in `stderr`. This
   * method should be called after any error, and the program should exit after
   * calling this method.
   *
   * @param message the error message
   */
  static void printError(String message) {
    System.out.println("ERROR");
    System.err.println(message);
    return;
  }

  /**
   * Construct a BufferedReader object for the file given in the relative path.
   * Prints an error and returns `null` if the file path is invalid.
   *
   * @param relativePath the file path
   * @return the BufferedReader, or null if file not found
   */
  static BufferedReader constructReader(String relativePath) {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(relativePath));
    } catch (FileNotFoundException e) {
      printError("Input file " + relativePath + " not found.");
    }
    return reader;
  }

  /**
   * Read and process the Stations section of the file, mapping each charger to
   * its respective station. Prints an error and returns `null` if the file
   * format is invalid.
   *
   * @param reader the BufferedReader for the given file
   * @return a map of each charger ID to its station ID
   */
  static HashMap<Integer, Integer> readStationsSection(BufferedReader reader) {
    try {
      if (!reader.readLine().equals("[Stations]")) {
        printError("Input file is formatted incorrectly.");
        reader.close();
        return null;
      }

      HashMap<Integer, Integer> output = new HashMap<>(); // charger -> station

      // Read each line
      for (String nextLine = reader.readLine(); !nextLine.isBlank(); nextLine = reader.readLine()) {
        String[] tokens = nextLine.split(" ");
        try {
          Integer stationId = Integer.valueOf(tokens[0]); // TODO: what if unsigned?
          for (int i = 1; i < tokens.length; i++) {
            output.put(Integer.valueOf(tokens[i]), stationId); // TODO: what if unsigned?
          }
        } catch (NumberFormatException e) {
          printError("Station and charger IDs must be unsigned 32-bit integers.");
          // TODO: make sure above works with unsigned (2,147,483,648 or larger)
          reader.close();
          output.clear();
          return null;
        }
      }

      return output;
    } catch (IOException e) {
      printError("File cannot be read.");
      return null;
    }
  }

  /**
   * Read and process the Charger Availability Reports section of the file,
   * mapping
   * each station to its reported uptime/downtime intervals. Prints an error
   * and returns `null` if the file format is invalid.
   *
   * @param reader     the BufferedReader for the given file
   * @param stationMap a map of each charger ID to its station ID
   * @return a map of each station ID to its reported time intervals
   */
  static HashMap<Integer, List<Report>> readChargerAvailabilityReportsSection(BufferedReader reader,
      HashMap<Integer, Integer> stationMap) {
    try {
      if (!reader.readLine().equals("[Charger Availability Reports]")) {
        printError("Input file is formatted incorrectly.");
        reader.close();
        return null;
      }

      HashMap<Integer, List<Report>> output = new HashMap<>();

      // Read each line
      for (String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine()) {
        String[] tokens = nextLine.split(" ");

        // Get the charger ID
        Integer chargerId;
        try {
          chargerId = Integer.valueOf(tokens[0]); // TODO: what if unsigned?
        } catch (NumberFormatException e) {
          printError("Charger IDs must be unsigned 32-bit integers.");
          // TODO: make sure above works with unsigned (2,147,483,648 or larger)
          reader.close();
          output.clear();
          return null;
        }

        // Get the charger's station ID
        Integer stationId = stationMap.get(chargerId);
        if (stationId == null) {
          printError("Each charger must be present at a station.");
          reader.close();
          output.clear();
          return null;
        }

        // Get station's availability reports
        List<Report> thisStationTimes;
        if (output.containsKey(stationId))
          thisStationTimes = output.get(stationId);
        else {
          thisStationTimes = new ArrayList<>();
          output.put(stationId, thisStationTimes);
        }

        // Put new time interval into station's time interval list
        long startTime, endTime; // TODO: what if unsigned?
        try {
          startTime = Long.parseLong(tokens[1]); // TODO: what if unsigned?
          endTime = Long.parseLong(tokens[2]); // TODO: what if unsigned?
        } catch (NumberFormatException e) {
          printError("Start and end times must be unsigned 64-bit integers");
          reader.close();
          output.clear();
          return null;
        }
        boolean up = Boolean.parseBoolean(tokens[3]);
        Report thisReport = new Report(startTime, endTime, up);
        thisStationTimes.add(thisReport);
      }

      return output;
    } catch (IOException e) {
      printError("File cannot be read.");
      return null;
    }
  }

  /**
   * Compute the uptimes for the station given its reported time intervals.
   *
   * @param stationTimeReports a list of the station's reported time intervals
   * @return the station uptime, as a truncated percentage
   */
  static int computeStationUptime(List<Report> stationTimeReports) {
    if (stationTimeReports.isEmpty())
      return 0; // no reported time

    // Get station's total reported time
    long start = Long.MAX_VALUE, end = 0;
    for (Report report : stationTimeReports) {
      if (report.startTime < start)
        start = report.startTime;
      if (report.endTime > end)
        end = report.endTime;
    }
    long totalTime = end - start;

    // Delete downtime, merge overlapping uptime
    List<Report> uptimeReports = new ArrayList<>();
    for (Report report : stationTimeReports) {
      if (report.up)
        uptimeReports.add(report);
    }
    stationTimeReports.clear(); // no longer needed, use as auxilliary list
    if (uptimeReports.isEmpty())
      return 0; // no reported time

    Collections.sort(uptimeReports);
    Report lastReport = uptimeReports.get(0);
    stationTimeReports.add(lastReport);
    for (int i = 1; i < uptimeReports.size(); i++) {
      // Due to natural ordering, each subsequent report is guaranteed to
      // start no earlier than previous reports.
      // We just need to check if the start of the current report overlaps
      // with the end of the last report.
      Report thisReport = uptimeReports.get(i);
      if (thisReport.startTime <= lastReport.endTime)
        lastReport.endTime = thisReport.endTime;
      else {
        stationTimeReports.add(thisReport);
        lastReport = thisReport;
      }
    }

    // Find total uptime, compute percentage
    long uptime = 0;
    for (Report report : stationTimeReports)
      uptime += report.endTime - report.startTime;
    return (int) (100 * (1.0 * uptime / totalTime)); // prevent long integer overflow
  }

  /**
   * Compute the uptimes for the stations, given the reported time intervals of
   * their respective chargers.
   *
   * @param stationReportsMap a map of each station ID to its reported time
   *                          intervals
   * @return the station uptimes, where the first entry of each nested array
   *         represents the station ID and the second represents the uptime
   */
  static int[][] computeStationUptimes(HashMap<Integer, List<Report>> stationReportsMap) {
    int[][] output = new int[stationReportsMap.size()][2];
    int outputIndex = 0;
    for (Entry<Integer, List<Report>> station : stationReportsMap.entrySet()) {
      output[outputIndex][0] = station.getKey().intValue();
      output[outputIndex][1] = computeStationUptime(station.getValue());
    }
    return output;
  }

  /**
   * Print the uptimes for each station. The method assumes that the stations
   * are sorted in ascending order, each containing the station ID and its
   * uptime, in that order.
   *
   * @param stationUptimes the station uptimes
   */
  static void printStationUptimes(int[][] stationUptimes) {
    for (int[] station : stationUptimes)
      System.out.printf("%d %d\n", station[0], station[1]);
    return;
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      printError("Please enter exactly one argument.");
      return;
    }

    BufferedReader reader = constructReader(args[0]);
    if (reader == null)
      return; // Error in constructing reader for file

    HashMap<Integer, Integer> stationsMap = readStationsSection(reader);
    if (stationsMap == null)
      return; // Error in Stations section

    HashMap<Integer, List<Report>> stationReportsMap = readChargerAvailabilityReportsSection(reader, stationsMap);
    if (stationsMap.isEmpty())
      return; // No stations, nothing to output
    stationsMap.clear();
    if (stationReportsMap == null)
      return; // Error in Charger Availability Reports section

    try {
      reader.close();
    } catch (IOException e) {
      printError("Closing reader failed.");
      stationReportsMap.clear();
      return;
    }

    int[][] stationUptimes = computeStationUptimes(stationReportsMap);
    Arrays.sort(stationUptimes, (arg0, arg1) -> arg0[0] - arg1[0]);
    printStationUptimes(stationUptimes);
    stationReportsMap.clear();
    return;
  }
}