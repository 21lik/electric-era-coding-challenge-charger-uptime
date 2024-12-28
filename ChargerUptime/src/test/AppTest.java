package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.launcher.TestExecutionListener;

import main.App;
import main.App.Report;

public class AppTest {
  public static final PrintStream STDOUT = System.out;
  public static final PrintStream STDERR = System.err;

  public static final File OUTPUT_FILE = new File("stdout_file.txt");
  public static final File ERROR_FILE = new File("stderr_file.txt");

  private static PrintStream outputStream;
  private static PrintStream errorStream;

  private BufferedReader outputReader;
  private BufferedReader errorReader;

  /**
   * Modify the relative file path from the test directory into a relative file
   * path from the main directory to the same file. This is needed to test
   * `App.constructReader(String)`, which is located in the main package.
   *
   * @param testFilePath the relative file path from `src/test`
   * @return the relative file path from `src/main`
   */
  private static String getMainFilePath(String testFilePath) {
    return "../test/" + testFilePath;
  }

  @BeforeAll
  public static void switchStreams() throws FileNotFoundException {
    outputStream = new PrintStream(OUTPUT_FILE);
    errorStream = new PrintStream(ERROR_FILE);
    System.setOut(outputStream);
    System.setErr(errorStream);
  }

  @BeforeEach
  public void constructStreamReaders() throws FileNotFoundException {
    outputReader = new BufferedReader(new FileReader(OUTPUT_FILE));
    errorReader = new BufferedReader(new FileReader(ERROR_FILE));
  }

  @AfterEach
  public void closeReadersAndClearFiles() throws IOException {
    // Close readers
    outputReader.close();
    errorReader.close();

    // Clear files by using PrintWriter and writing an empty string
    PrintWriter writer = new PrintWriter(OUTPUT_FILE);
    writer.write("");
    writer.close();
    writer = new PrintWriter(ERROR_FILE);
    writer.write("");
    writer.close();
  }

  @AfterAll
  public static void closeAndResetStreams() {
    outputStream.close();
    errorStream.close();

    System.setOut(STDOUT);
    System.setErr(STDERR);
  }

  // App.printError(String)

  @Test
  public void printErrorWithNullMessage() {
    App.printError(null);
    assertDoesNotThrow(() -> {
      assertEquals("ERROR", outputReader.readLine());
      assertNull(outputReader.readLine());
      assertNull(errorReader.readLine());
    });
  }

  @Test
  public void printErrorWithEmptyMessage() {
    // TODO: implement
    App.printError("");
    // TODO: assert "ERROR" in stdout and "" (or "\n") in stderr
  }

  @Test
  public void printErrorWithMessage() {
    // TODO: implement
    int testMessageLength = (int) (Math.random() * 256);
    char[] testMessageCharArray = new char[testMessageLength];
    for (int i = 0; i < testMessageLength; i++) {
      // Generate random alphanumeral character
      char charVal = (char) (Math.random() * 62);
      if (charVal < 10)
        charVal += '0';
      else if (charVal < 36)
        charVal += 'A' - 10;
      else
        charVal += 'a' - 36;
      testMessageCharArray[i] += charVal;
    }
    String testMessage = new String(testMessageCharArray);
    App.printError(testMessage);
    // TODO: assert "ERROR" in stdout and testMessage (or testMessage + "\n") in
    // stderr
    
    assertDoesNotThrow(() -> {
      assertEquals("ERROR", outputReader.readLine());
      assertNull(outputReader.readLine());
      assertEquals("", errorReader.readLine());
    });
  }

  // App.constructReader(String)

  @Test
  public void constructReaderForNullFilePrintsError() {
    Object reader = App.constructReader(null);
    // TODO: assert error message
    
    assertDoesNotThrow(() -> {
      assertEquals("ERROR", outputReader.readLine());
      assertNull(outputReader.readLine());
      assertEquals("Null input file not allowed.", errorReader.readLine());
    });

    assertNull(reader);
  }

  @Test
  public void constructReaderForNonexistentFilePrintsError() {
    String fileName = getMainFilePath("file-does-not-exist.txt");
    Object reader = App.constructReader(fileName);
    // TODO: assert error message

    assertNull(reader);
  }

  @Test
  public void constructReaderForDirectoryPrintsError() {
    String fileName = getMainFilePath("empty_dir");
    Object reader = App.constructReader(fileName);
    // TODO: assert error message

    assertNull(reader);
  }

  @Test
  public void constructReaderForReadableFileReturnsReader() {
    String fileName = getMainFilePath("empty_file.txt");
    final BufferedReader reader = App.constructReader(fileName);
    assertNotNull(reader);
    assertDoesNotThrow(() -> {
      reader.close();
    });
  }

  // App.readStationsSection(BufferedReader)

  @Test
  public void readStationsSectionForEmptyFilePrintsError() {
    String fileName = getMainFilePath("empty_file.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithoutHeadersPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_without_headers.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithoutStationsSectionPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_without_stations_section.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithoutNewlineSeparationPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_without_newline_separation.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithNonNumberStationIDsPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_with_non_number_station_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithNonNumberChargerIDsPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_with_non_number_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithNegativeStationIDsPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_with_negative_station_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithNegativeChargerIDsPrintsError() {
    // TODO: implement

    String fileName = getMainFilePath("file_with_negative_charger_ids.txt");

    assertDoesNotThrow(() -> {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));

      Object map = App.readStationsSection(reader);
      // TODO: assert error message

      assertNull(map);
      reader.close();
    });
  }

  @Test
  public void readStationsSectionForFileWithLongStationIDsPrintsError() {
    // TODO: implement

  }

  @Test
  public void readStationsSectionForFileWithLongChargerIDsPrintsError() {
    // TODO: implement

  }

  @Test
  public void readStationsSectionForFileWithValidIDs() {
    // TODO: implement

  }

  // App.readChargerAvailabilityReportsSection(BufferedReader, HashMap<Integer,
  // Integer>)

  // TODO: methods

  // App.computeStationUptime(List<Report>)

  public void computeStationUptimeClearsReportList() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, 2, true));
    reportList.add(new Report(5, 6, false));
    App.computeStationUptime(reportList);
    assertTrue(reportList.isEmpty());
  }

  public void computeStationUptimeReturnsZeroOnEmptyList() {
    List<Report> reportList = new ArrayList<>();
    int output = App.computeStationUptime(reportList);
    assertEquals(0, output);
  }

  public void computeStationUptimeReturnsZeroOnNoUptime() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, 100, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(0, output);
  }

  public void computeStationUptimeReportsIgnoresOverlappingDowntime() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, 7, true));
    reportList.add(new Report(3, 4, false));
    reportList.add(new Report(6, 10, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(100, output);
  }

  public void computeStationUptimeReportsMergesOverlappingUptime() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, 7, true));
    reportList.add(new Report(3, 4, true));
    reportList.add(new Report(6, 10, true));
    int output = App.computeStationUptime(reportList);
    assertEquals(100, output);
  }

  public void computeStationUptimeCountsUnreportedTimeBetweenReportsAsDowntime() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, 4, true));
    reportList.add(new Report(5, 6, false));
    reportList.add(new Report(9, 10, true));
    int output = App.computeStationUptime(reportList);
    assertEquals(50, output);
  }

  public void computeStationUptimeIgnoresUnreportedTimeBeforeFirstStart() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(1, 2, true));
    reportList.add(new Report(2, 3, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(50, output);
  }

  public void computeStationUptimeTruncatesOutput() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, 2, true));
    reportList.add(new Report(2, 3, false));
    int output = App.computeStationUptime(reportList);
    assertEquals(66, output);
  }

  public void computeStationUptimeHandlesLargeUnsignedLong() {
    List<Report> reportList = new ArrayList<>();
    reportList.add(new Report(0, Long.MAX_VALUE + 1, true));
    int output = App.computeStationUptime(reportList);
    assertEquals(100, output);
  }

  // TODO: methods

  // App.computeStationUptimes(HashMap<Integer, List<Report>>)

  // TODO: methods

  // App.printStationUptimes(int[][])

  // TODO: methods
}