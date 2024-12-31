# electric-era-coding-challenge-charger-uptime

Last updated: Dec 31, 2024

This program was written in Java 21 by Kevin Li for the Electric Era Coding Challenge, found here:
https://gitlab.com/electric-era-public/coding-challenge-charger-uptime. A private GitHub repository was used for version control.

This program was submitted on December 31, 2024. Thank you again Electric Era for considering me for your position, and I hope you have a Merry Christmas and a Happy New Year!

## Compiling and Running

To compile the main application from the `electric-era-coding-challenge-charger-uptime` directory, run

```bash
javac ChargerUptime/src/main/App.java
```

and then run with

```bash
java ChargerUptime/src/main/App.java relative/path/to/input/file
```

To compile the test application from the `electric-era-coding-challenge-charger-uptime` directory, run

```bash
javac -cp "ChargerUptime/lib/junit-platform-console-standalone-1.8.2.jar" ChargerUptime/src/main/App.java ChargerUptime/src/test/AppTest.java
```

and then run with

```bash
java -jar ChargerUptime/lib/junit-platform-console-standalone-1.8.2.jar -cp "ChargerUptime/src/" --select-class test.AppTest
```

Note: due to the nature of relative paths and they way they're hardcoded in AppTest, the unit tests are by default only guaranteed to work if you run them from the `electric-era-coding-challenge-charger-uptime` parent directory. If you wish to run them directly in the `ChargerUptime` directory, modify the char constant `RUNNING_FROM_SUBDIRECTORY_ID` in `AppTest.java` to `CHARGER_UPTIME_DIR_ID`, then run
```bash
javac -cp "lib/junit-platform-console-standalone-1.8.2.jar" src/main/App.java src/test/AppTest.java
java -jar lib/junit-platform-console-standalone-1.8.2.jar -cp "src/" --select-class test.AppTest
```
instead of the commands above. To run App.java from this directory, run
```bash
javac src/main/App.java
java src/main/App.java relative/path/to/input/file
```

If you wish to run the tests directly in the `src` subdirectory, modify the constant `RUNNING_FROM_SUBDIRECTORY_ID` in `AppTest.java` to `SRC_DIR_ID`, then run
```bash
javac -cp "../lib/junit-platform-console-standalone-1.8.2.jar" main/App.java test/AppTest.java
java -jar ../lib/junit-platform-console-standalone-1.8.2.jar -cp "." --select-class test.AppTest
```
instead of the commands above. To run App.java from this directory, run
```bash
javac main/App.java
java main/App.java relative/path/to/input/file
```

In both cases you will have to modify your relative path to the input file.

## Error Handling and Preconditions

In case of errors due to improper inputs or unmet, the program does not formally throw an Exception. Instead, `ERROR` is printed to `stdout` and a short statement explaining the error is printed to `stderr`.

The program assumes that the `up` value for each charger is `true` or `false`, using `Boolean.parseBoolean(String)` to assign the proper boolean value to each report. This function ignores case (i.e. "True" is assigned to `true`), but any non-boolean string like "yes", "okay", "3", "undefined", and "-69" is assumed to be `false`. The program does not output an error in this case.

The program also assumes that all Station IDs are unique, all Charger IDs are unique, and there are no typos in the input file. Program behavior is unknown if these preconditions are not met. It also assumes that the input file is formatted exactly as specified: headers for the Stations and the Charger Availability Reports sections, separated by a newline, and the tokens of each line in each section are separated by exactly one whitespace character. If this formatting precondition fails, the program will print an error and exit.

The program checks to ensure that the user enters exactly one argument representing the relative path to an existing and readable input file, all Station and Charger IDs are unsigned 32-bit integers, and the start/end times are unsigned 64-bit (long) integers. If any of these preconditions fail, the program will print an error and exit.

The program does *not* assume that the report entries are contiguous or in any order whatsoever, nor does it assume that the Station and Charger IDs are sorted in ascending (or any) order in the input file. Sorting is used when computing the uptime to handle these cases, and the Station IDs are guaranteed to be printed in *ascending order* upon program success.

The program also does *not* assume that every station contains at least one charger, nor does it assume that any stations or reports are present. However, nothing would be printed if no stations are present, provided the program does not encounter an error.

The program assumes that the input file is small enough to avoid an `OutOfMemoryException`.

## Algorithm Complexity

If $s$ represents the number of stations, $c$ represents the number of chargers, and $r$ represents the number of reports, then the application has $O(sr\log{(r)}+c)$ time complexity and $O(s+c+r)$ space complexity.

* `readStationsSection(BufferedReader, List<Integer>)`: $O(s+c)$ time complexity, $O(s+c)$ space complexity
* `readChargerAvailabilityReportsSection(BufferedReader, HashMap<Integer, Integer>, List<Integer>)`: $O(s+r)$ time complexity, $O(s+r)$ space complexity
* `computeStationUptime(List<Report>)`: $O(r\log{r})$ time complexity, $O(r)$ space complexity
* `computeStationUptimes(HashMap<Integer, List<Report>>)`: $O(sr\log{r})$, $O(s+r)$ space complexity
* `printStationUptimes(int[][])`: $O(s)$ time complexity, $O(1)$ space complexity