package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Main application class for the Electric Era Coding Challenge for the
 * Software Engineer (New Grad) position.
 *
 * @author Kevin Li
 */
public class App {
  public static class Report implements Comparable<Report> {
    long startTime, endTime; // unsigned long
    boolean up;

    /**
     * Create a new Report object. It is assumed that <code>endTime</code> is
     * larger than <code>startTime</code>, in unsigned longs; that is,
     * <code>Long.compareUnsigned(endTime, startTime) > 0</code>; and no report
     * is reused for multiple stations.
     *
     * @param startTime the starting time, in nanoseconds
     * @param endTime   the ending time, in nanoseconds
     * @param up        true if uptime, false if downtime
     */
    public Report(long startTime, long endTime, boolean up) {
      this.startTime = startTime;
      this.endTime = endTime;
      this.up = up;
    }

    @Override
    public boolean equals(Object other) {
      if (other == null || !(other instanceof Report))
        return false;
      Report otherReport = (Report) other;
      return (startTime == otherReport.startTime) && (endTime == otherReport.endTime) && (up == otherReport.up);
    }

    @Override
    public int compareTo(Report other) {
      // Will help when merging overlapping uptime reports
      return startTime == other.startTime ? Long.compareUnsigned(endTime, other.endTime)
          : Long.compareUnsigned(startTime, other.startTime);
    }
  }

  /**
   * Read and process the Stations section of the file, mapping each charger to
   * its respective station. Prints an error and returns <code>null</code> if
   * the file format is invalid.
   *
   * @param reader        the BufferedReader for the given file
   * @param emptyStations an auxiliary list to store station IDs with no
   *                      charger IDs
   * @return a map of each charger ID to its station ID
   */
  public static HashMap<Integer, Integer> readStationsSection(BufferedReader reader, List<Integer> emptyStations) {
    try {
      String nextLine = reader.readLine();
      if (nextLine == null || !nextLine.equals("[Stations]")) {
        System.out.println("ERROR");
        System.err.println("Input file is formatted incorrectly.");
        reader.close();
        return null;
      }

      HashMap<Integer, Integer> output = new HashMap<>(); // charger -> station

      // Read each line
      for (nextLine = reader.readLine(); !nextLine.isBlank(); nextLine = reader.readLine()) {
        String[] tokens = nextLine.split(" ");
        try {
          Integer stationId = Integer.valueOf(Integer.parseUnsignedInt(tokens[0]));
          if (tokens.length == 1) {
            // No Charger IDs found for this station, proceed
            emptyStations.add(stationId);
            continue;
          }
          for (int i = 1; i < tokens.length; i++) {
            output.put(Integer.valueOf(Integer.parseUnsignedInt(tokens[i])), stationId);
          }
        } catch (NumberFormatException e) {
          System.out.println("ERROR");
          System.err.println("Station and charger IDs must be unsigned 32-bit integers.");
          reader.close();
          output.clear();
          return null;
        }
      }

      return output;
    } catch (IOException e) {
      System.out.println("ERROR");
      System.err.println("File cannot be read.");
      return null;
    }
  }

  /**
   * Read and process the Charger Availability Reports section of the file,
   * mapping each station to its reported uptime/downtime intervals. Prints an
   * error and returns <code>null</code> if the file format is invalid. It is
   * assumed that <code>stationMap</code> is not null.
   *
   * @param reader        the BufferedReader for the given file
   * @param stationMap    a map of each charger ID to its station ID
   * @param emptyStations an auxiliary list containing station IDs with no
   *                      charger IDs
   * @return a map of each station ID to its reported time intervals
   */
  public static HashMap<Integer, List<Report>> readChargerAvailabilityReportsSection(BufferedReader reader,
      HashMap<Integer, Integer> stationMap, List<Integer> emptyStations) {
    try {
      String nextLine = reader.readLine();
      if (nextLine == null || !nextLine.equals("[Charger Availability Reports]")) {
        System.out.println("ERROR");
        System.err.println("Input file is formatted incorrectly.");
        reader.close();
        return null;
      }

      HashMap<Integer, List<Report>> output = new HashMap<>();

      // Put empty stations in output, no reports
      for (Integer emptyStation : emptyStations)
        output.put(emptyStation, new ArrayList<>(0));

      // Read each line
      for (nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine()) {
        String[] tokens = nextLine.split(" ");

        // Get the charger ID
        Integer chargerId;
        try {
          chargerId = Integer.valueOf(Integer.parseUnsignedInt(tokens[0]));
        } catch (NumberFormatException e) {
          System.out.println("ERROR");
          System.err.println("Charger IDs must be unsigned 32-bit integers.");
          reader.close();
          output.clear();
          return null;
        }

        // Get the charger's station ID
        Integer stationId = stationMap.get(chargerId);
        if (stationId == null) {
          System.out.println("ERROR");
          System.err.println("Each charger must be present at a station.");
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
        long startTime, endTime;
        try {
          startTime = Long.parseUnsignedLong(tokens[1]);
          endTime = Long.parseUnsignedLong(tokens[2]);
        } catch (NumberFormatException e) {
          System.out.println("ERROR");
          System.err.println("Start and end times must be unsigned 64-bit integers.");
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
      System.out.println("ERROR");
      System.err.println("File cannot be read.");
      return null;
    }
  }

  /**
   * Compute the uptimes for the station given its reported time intervals. The
   * list of reports is cleared after the function call to free memory storage.
   * It is assumed that <code>stationTimeReports</code> is not null.
   *
   * @param stationTimeReports a list of the station's reported time intervals
   * @return the station uptime, as a truncated percentage
   */
  public static int computeStationUptime(List<Report> stationTimeReports) {
    if (stationTimeReports.isEmpty())
      return 0; // no reported time

    // Get station's total reported time
    long start = Long.MAX_VALUE, end = 0;
    for (Report report : stationTimeReports) {
      if (Long.compareUnsigned(report.startTime, start) < 0)
        start = report.startTime;
      if (Long.compareUnsigned(report.endTime, end) > 0)
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
      // We just need to skip any reports that end before the last report and
      // check if the start of the current report overlaps with the end of the
      // last report.
      Report thisReport = uptimeReports.get(i);
      if (thisReport.endTime < lastReport.endTime)
        continue;
      else if (thisReport.startTime <= lastReport.endTime)
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
    stationTimeReports.clear();

    // Since we're using unsigned long values for times, we need unsigned
    // string and BigInteger to prevent overflow.
    String uptimeString = Long.toUnsignedString(uptime);
    String totalTimeString = Long.toUnsignedString(totalTime);
    BigInteger numerator = new BigInteger(uptimeString + "00");
    BigInteger denominator = new BigInteger(totalTimeString);
    return numerator.divide(denominator).intValue();
  }

  /**
   * Compute the uptimes for the stations, given the reported time intervals of
   * their respective chargers. It is assumed that
   * <code>stationReportsMap</code> is not null.
   *
   * @param stationReportsMap a map of each station ID to its reported time
   *                          intervals
   * @return the station uptimes, where the first entry of each nested array
   *         represents the station ID and the second represents the uptime
   */
  public static int[][] computeStationUptimes(HashMap<Integer, List<Report>> stationReportsMap) {
    int[][] output = new int[stationReportsMap.size()][2];
    int outputIndex = 0;
    for (Entry<Integer, List<Report>> station : stationReportsMap.entrySet()) {
      output[outputIndex][0] = station.getKey().intValue();
      output[outputIndex++][1] = computeStationUptime(station.getValue());
    }
    return output;
  }

  /**
   * Print the uptimes for each station. The method assumes that the station
   * uptimes are non-null and sorted in ascending order, each containing the
   * station ID and its uptime, in that order.
   *
   * @param stationUptimes the station uptimes
   */
  public static void printStationUptimes(int[][] stationUptimes) {
    for (int[] station : stationUptimes)
      System.out.printf("%d %d\n", station[0], station[1]);
    return;
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("ERROR");
      System.err.println("Please enter exactly one argument.");
      return;
    }

    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(args[0]));
    } catch (FileNotFoundException e) {
      System.out.println("ERROR");
      System.err.println("Input file " + args[0] + " not found.");
      return;
    }

    List<Integer> emptyStations = new ArrayList<>();
    HashMap<Integer, Integer> stationsMap = readStationsSection(reader, emptyStations);
    if (stationsMap == null) {
      emptyStations.clear();
      return; // Error in Stations section
    }

    HashMap<Integer, List<Report>> stationReportsMap = readChargerAvailabilityReportsSection(reader, stationsMap,
        emptyStations);
    emptyStations.clear();
    if (stationsMap.isEmpty())
      return; // No stations, nothing to output
    stationsMap.clear();
    if (stationReportsMap == null)
      return; // Error in Charger Availability Reports section

    try {
      reader.close();
    } catch (IOException e) {
      System.out.println("ERROR");
      System.err.println("Closing reader failed.");
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