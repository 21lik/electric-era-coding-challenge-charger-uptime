package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.App;
import main.App.Report;

public class AppTest {
  public static final char ELECTRIC_ERA_CODING_CHALLENGE_CHARGER_UPTIME_DIR_ID = 'E';
  public static final char CHARGER_UPTIME_DIR_ID = 'C';
  public static final char SRC_DIR_ID = 'S';

  /**
   * Character constant denoting whether AppTest is being run from the
   * <code>electric-era-coding-challenge-charger-uptime/</code> parent
   * directory, the
   * <code>electric-era-coding-challenge-charger-uptime/ChargerUptime/</code>
   * directory, or the
   * <code>electric-era-coding-challenge-charger-uptime/ChargerUptime/src/</code>
   * subdirectory. Set to
   * <code>ELECTRIC_ERA_CODING_CHALLENGE_CHARGER_UPTIME_DIR_ID</code> if the
   * first, <code>CHARGER_UPTIME_DIR_ID</code> if the second, and
   * <code>SRC_DIR_ID</code> if the third. This is important for setting the
   * correct relative file path for the text files used for the unit tests
   * below.
   * <br>
   * </br>
   * It is assumed that the tester will not attempt to run AppTest from a
   * different directory (e.g.
   * <code>electric-era-coding-challenge-charger-uptime/ChargerUptime/src/test/</code>).
   */
  public static final char RUNNING_FROM_SUBDIRECTORY_ID = ELECTRIC_ERA_CODING_CHALLENGE_CHARGER_UPTIME_DIR_ID;

  /**
   * Get the relative path for the file with the given name, depending on where
   * the tester is running AppTest. See the documentation for
   * <code>RUNNING_FROM_SUBDIRECTORY_ID</code> for the three possible
   * locations.
   *
   * @param fileName the name of the file
   * @return the relative file path
   * @throws IllegalStateException if <code>RUNNING_FROM_SUBDIRECTORY_ID</code>
   *                               is not one of the three valid values
   */
  String getRelativeFilePath(String fileName) {
    switch (RUNNING_FROM_SUBDIRECTORY_ID) {
      case ELECTRIC_ERA_CODING_CHALLENGE_CHARGER_UPTIME_DIR_ID:
        return "ChargerUptime/src/test/" + fileName;
      case CHARGER_UPTIME_DIR_ID:
        return "src/test/" + fileName;
      case SRC_DIR_ID:
        return "test/" + fileName;
      default:
        throw new IllegalStateException("RUNNING_FROM_SUBDIRECTORY_ID is set to an invalid char "
            + RUNNING_FROM_SUBDIRECTORY_ID + ", please change to one of the three allowed options.");
    }
  }

  // App.readStationsSection(BufferedReader)

  @Test
  public void testReadStationsSectionForEmptyFileReturnsNull() {
    String fileName = getRelativeFilePath("empty_file.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithEmptySectionsReturnsEmptyMap() {
    String fileName = getRelativeFilePath("file_with_empty_sections.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      HashMap<Integer, Integer> map = App.readStationsSection(reader);
      reader.close();
      assertNotNull(map);
      assertTrue(map.isEmpty());
    });
  }

  @Test
  public void testReadStationsSectionForFileWithoutHeadersReturnsNull() {
    String fileName = getRelativeFilePath("file_without_headers.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithoutStationsSectionReturnsNull() {
    String fileName = getRelativeFilePath("file_without_stations_section.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithoutNewlineSeparationReturnsNull() {
    String fileName = getRelativeFilePath("file_without_newline_separation.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithNonNumberStationIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_non_number_station_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithNonNumberChargerIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_non_number_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithNegativeStationIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_negative_station_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithNegativeChargerIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_negative_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithLongStationIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_long_station_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithLongChargerIDsPrintsError() {
    String fileName = getRelativeFilePath("file_with_long_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      Object map = App.readStationsSection(reader);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadStationsSectionForFileWithValidIDs() {
    String fileName = getRelativeFilePath("file_with_valid_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      HashMap<Integer, Integer> map = App.readStationsSection(reader);
      reader.close();
      assertNotNull(map);
      assertEquals(4, map.size());
      assertEquals(Integer.valueOf(0), map.get(Integer.valueOf(1001)));
      assertEquals(Integer.valueOf(0), map.get(Integer.valueOf(1002)));
      assertEquals(Integer.valueOf(1), map.get(Integer.valueOf(1003)));
      assertEquals(Integer.valueOf(2), map.get(Integer.valueOf(1004)));
      map.clear();
    });
  }

  @Test
  public void testReadStationsSectionForFileWithValidUnsignedIntIDs() {
    String fileName = getRelativeFilePath("file_with_valid_unsigned_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      HashMap<Integer, Integer> map = App.readStationsSection(reader);
      reader.close();
      assertNotNull(map);
      assertEquals(4, map.size());

      int stationOffset = (int) 2147483648l;
      int chargerOffset = (int) 3000001000l;
      assertEquals(Integer.valueOf(stationOffset), map.get(Integer.valueOf(chargerOffset + 1)));
      assertEquals(Integer.valueOf(stationOffset), map.get(Integer.valueOf(chargerOffset + 2)));
      assertEquals(Integer.valueOf(stationOffset + 1), map.get(Integer.valueOf(chargerOffset + 3)));
      assertEquals(Integer.valueOf(stationOffset + 2), map.get(Integer.valueOf(chargerOffset + 4)));

      map.clear();
    });
  }

  // App.readChargerAvailabilityReportsSection(BufferedReader, HashMap<Integer,
  // Integer>)

  @Test
  public void testReadChargerAvailabilityReportsSectionForEmptyFileReturnsNull() {
    String fileName = getRelativeFilePath("empty_file.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithEmptySectionsReturnsEmptyMap() {
    String fileName = getRelativeFilePath("file_with_empty_sections.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();

      HashMap<Integer, List<Report>> map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      reader.close();
      assertNotNull(map);
      assertTrue(map.isEmpty());
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithoutHeadersReturnsNull() {
    String fileName = getRelativeFilePath("file_without_headers.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithoutChargerAvailabilityReportsSectionReturnsNull() {
    String fileName = getRelativeFilePath("file_without_charger_availability_reports_section.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithNonNumberChargerIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_non_number_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithNonNumberTimesReturnsNull() {
    String fileName = getRelativeFilePath("file_with_non_number_times.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithNegativeChargerIDsReturnsNull() {
    String fileName = getRelativeFilePath("file_with_negative_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(-1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(-1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(-1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(-1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithNegativeTimesReturnsNull() {
    String fileName = getRelativeFilePath("file_with_negative_times.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithLongChargerIDsPrintsError() {
    String fileName = getRelativeFilePath("file_with_long_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      int offset = (int) 10000000001000l;
      stationsMap.put(Integer.valueOf(offset + 1), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(offset + 2), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(offset + 3), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(offset + 4), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithTooLongTimesPrintsError() {
    String fileName = getRelativeFilePath("file_with_too_long_times.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithEmptyStationsMapReturnsNull() {
    String fileName = getRelativeFilePath("file_with_valid_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section, but leave stations map empty
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithValidIDsWithReaderInWrongSectionReturnsNull() {
    String fileName = getRelativeFilePath("file_with_valid_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Don't simulate reading Stations section, just get stations map
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      Object map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNull(map);
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithValidIDsAndCorrectStationsMap() {
    String fileName = getRelativeFilePath("file_with_valid_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      HashMap<Integer, List<Report>> map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNotNull(map);
      assertEquals(3, map.size());

      // Check each list and their reports, ensure expected behavior
      List<Report> list0 = map.get(Integer.valueOf(0));
      List<Report> list1 = map.get(Integer.valueOf(1));
      List<Report> list2 = map.get(Integer.valueOf(2));
      assertNotNull(list0);
      assertNotNull(list1);
      assertNotNull(list2);
      assertEquals(3, list0.size());
      assertEquals(1, list1.size());
      assertEquals(2, list2.size());

      assertEquals(new Report(0, 50000, true), list0.get(0));
      assertEquals(new Report(50000, 100000, true), list0.get(1));
      assertEquals(new Report(50000, 100000, true), list0.get(2));
      assertEquals(new Report(25000, 75000, false), list1.get(0));
      assertEquals(new Report(0, 50000, true), list2.get(0));
      assertEquals(new Report(100000, 200000, true), list2.get(1));

      list0.clear();
      list1.clear();
      list2.clear();

      map.clear();
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithValidUnsignedIntIDsAndCorrectStationsMap() {
    String fileName = getRelativeFilePath("file_with_valid_unsigned_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      int stationOffset = (int) 2147483648l;
      int chargerOffset = (int) 3000001000l;
      stationsMap.put(Integer.valueOf(chargerOffset + 1), Integer.valueOf(stationOffset));
      stationsMap.put(Integer.valueOf(chargerOffset + 2), Integer.valueOf(stationOffset));
      stationsMap.put(Integer.valueOf(chargerOffset + 3), Integer.valueOf(stationOffset + 1));
      stationsMap.put(Integer.valueOf(chargerOffset + 4), Integer.valueOf(stationOffset + 2));

      HashMap<Integer, List<Report>> map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNotNull(map);
      assertEquals(3, map.size());

      // Check each list and their reports, ensure expected behavior
      List<Report> list0 = map.get(Integer.valueOf(stationOffset));
      List<Report> list1 = map.get(Integer.valueOf(stationOffset + 1));
      List<Report> list2 = map.get(Integer.valueOf(stationOffset + 2));
      assertNotNull(list0);
      assertNotNull(list1);
      assertNotNull(list2);
      assertEquals(3, list0.size());
      assertEquals(1, list1.size());
      assertEquals(2, list2.size());

      assertEquals(new Report(0, 50000, true), list0.get(0));
      assertEquals(new Report(50000, 100000, true), list0.get(1));
      assertEquals(new Report(50000, 100000, true), list0.get(2));
      assertEquals(new Report(25000, 75000, false), list1.get(0));
      assertEquals(new Report(0, 50000, true), list2.get(0));
      assertEquals(new Report(100000, 200000, true), list2.get(1));

      list0.clear();
      list1.clear();
      list2.clear();

      map.clear();
    });
  }

  @Test
  public void testReadChargerAvailabilityReportsSectionForFileWithValidIDsAndUnsignedLongTimesAndCorrectStationsMap() {
    String fileName = getRelativeFilePath("file_with_unsigned_long_times.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      // Simulate reading Stations section and getting stations map
      for (String nextLine = reader.readLine(); nextLine != null && !nextLine.isBlank(); nextLine = reader.readLine())
        ;
      HashMap<Integer, Integer> stationsMap = new HashMap<>();
      stationsMap.put(Integer.valueOf(1001), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1002), Integer.valueOf(0));
      stationsMap.put(Integer.valueOf(1003), Integer.valueOf(1));
      stationsMap.put(Integer.valueOf(1004), Integer.valueOf(2));

      HashMap<Integer, List<Report>> map = App.readChargerAvailabilityReportsSection(reader, stationsMap);
      stationsMap.clear();
      reader.close();
      assertNotNull(map);
      assertEquals(3, map.size());

      // Check each list and their reports, ensure expected behavior
      List<Report> list0 = map.get(Integer.valueOf(0));
      List<Report> list1 = map.get(Integer.valueOf(1));
      List<Report> list2 = map.get(Integer.valueOf(2));
      assertNotNull(list0);
      assertNotNull(list1);
      assertNotNull(list2);
      assertEquals(3, list0.size());
      assertEquals(1, list1.size());
      assertEquals(2, list2.size());

      long timeOffset = Long.parseUnsignedLong("10000000000000000000");

      assertEquals(new Report(timeOffset, timeOffset + 50000, true), list0.get(0));
      assertEquals(new Report(timeOffset + 50000, timeOffset + 100000, true), list0.get(1));
      assertEquals(new Report(timeOffset + 50000, timeOffset + 100000, true), list0.get(2));
      assertEquals(new Report(timeOffset + 25000, timeOffset + 75000, false), list1.get(0));
      assertEquals(new Report(timeOffset, timeOffset + 50000, true), list2.get(0));
      assertEquals(new Report(timeOffset + 100000, timeOffset + 200000, true), list2.get(1));

      list0.clear();
      list1.clear();
      list2.clear();

      map.clear();
    });
  }

  // App.computeStationUptime(List<Report>)

  @Test
  public void testComputeStationUptimeClearsReportList() {
    List<Report> reportList = new ArrayList<>(2);
    reportList.add(new Report(0, 2, true));
    reportList.add(new Report(5, 6, false));
    App.computeStationUptime(reportList);
    assertTrue(reportList.isEmpty());
  }

  @Test
  public void testComputeStationUptimeReturnsZeroOnEmptyList() {
    List<Report> reportList = new ArrayList<>(0);
    int output = App.computeStationUptime(reportList);
    assertEquals(0, output);
  }

  @Test
  public void testComputeStationUptimeReturnsZeroOnNoUptime() {
    List<Report> reportList = new ArrayList<>(1);
    reportList.add(new Report(0, 100, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(0, output);
  }

  @Test
  public void testComputeStationUptimeIgnoresOverlappingDowntime() {
    List<Report> reportList = new ArrayList<>(3);
    reportList.add(new Report(0, 7, true));
    reportList.add(new Report(3, 4, false));
    reportList.add(new Report(6, 10, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(70, output);
  }

  @Test
  public void testComputeStationUptimeMergesOverlappingUptime() {
    List<Report> reportList = new ArrayList<>(3);
    reportList.add(new Report(0, 7, true));
    reportList.add(new Report(3, 4, true));
    reportList.add(new Report(6, 10, true));
    int output = App.computeStationUptime(reportList);
    assertEquals(100, output);
  }

  @Test
  public void testComputeStationUptimeHandlesUnsortedReports() {
    List<Report> reportList = new ArrayList<>(4);
    reportList.add(new Report(2, 10, true));
    reportList.add(new Report(0, 2, false));
    reportList.add(new Report(16, 20, true));
    reportList.add(new Report(10, 16, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(60, output);
  }

  @Test
  public void testComputeStationUptimeCountsUnreportedTimeBetweenReportsAsDowntime() {
    List<Report> reportList = new ArrayList<>(3);
    reportList.add(new Report(0, 4, true));
    reportList.add(new Report(5, 6, false));
    reportList.add(new Report(9, 10, true));
    int output = App.computeStationUptime(reportList);
    assertEquals(50, output);
  }

  @Test
  public void testComputeStationUptimeIgnoresUnreportedTimeBeforeFirstStart() {
    List<Report> reportList = new ArrayList<>(2);
    reportList.add(new Report(1, 2, true));
    reportList.add(new Report(2, 3, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(50, output);
  }

  @Test
  public void testComputeStationUptimeTruncatesOutput() {
    List<Report> reportList = new ArrayList<>(2);
    reportList.add(new Report(0, 2, true));
    reportList.add(new Report(2, 3, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(66, output);
  }

  @Test
  public void testComputeStationUptimeHandlesLargeUnsignedLongTotalTime() {
    List<Report> reportList = new ArrayList<>(2);
    reportList.add(new Report(0, Long.MIN_VALUE >>> 1, true));
    reportList.add(new Report(Long.MIN_VALUE >>> 1, Long.MIN_VALUE, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(50, output);
  }

  @Test
  public void testComputeStationUptimeHandlesLargeUnsignedLongUptime() {
    List<Report> reportList = new ArrayList<>(2);
    reportList.add(new Report(0, Long.MIN_VALUE, true));
    reportList.add(new Report(Long.MIN_VALUE, -1, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(50, output);
  }

  // App.computeStationUptimes(HashMap<Integer, List<Report>>)

  @Test
  public void testComputeStationUptimesReturnsEmptyArraysOnEmptyHashMap() {
    HashMap<Integer, List<Report>> map = new HashMap<>(0);
    int[][] output = App.computeStationUptimes(map);
    assertNotNull(output);
    assertEquals(0, output.length);
  }

  @Test
  public void testComputeStationUptimesOutputLengthEqualsHashMapSize() {
    int size = (int) (256 * Math.random());
    HashMap<Integer, List<Report>> map = new HashMap<>(size << 1 + 1);
    for (int i = 0; i < size; i++)
      map.put(Integer.valueOf(i), new ArrayList<>(0));
    int[][] output = App.computeStationUptimes(map);
    assertNotNull(output);
    assertEquals(size, output.length);
  }

  @Test
  public void testComputeStationUptimesOutputContainsStationIDsAndUptimesInAscendingOrder() {
    HashMap<Integer, List<Report>> map = new HashMap<>();
    List<Report> list1 = new ArrayList<>(); // 100
    list1.add(new Report(0, 1, true));
    List<Report> list4 = new ArrayList<>(); // 25
    list4.add(new Report(0, 3, false));
    list4.add(new Report(3, 4, true));
    List<Report> list5 = new ArrayList<>(); // 50
    list5.add(new Report(0, 5, true));
    list5.add(new Report(5, 10, false));
    map.put(Integer.valueOf(5), list5);
    map.put(Integer.valueOf(1), list1);
    map.put(Integer.valueOf(4), list4);
    int[][] output = App.computeStationUptimes(map);
    list1.clear();
    list4.clear();
    list5.clear();
    map.clear();
    assertNotNull(output);
    assertEquals(1, output[0][0]);
    assertEquals(100, output[0][1]);
    assertEquals(4, output[1][0]);
    assertEquals(25, output[1][1]);
    assertEquals(5, output[2][0]);
    assertEquals(50, output[2][1]);
  }
}