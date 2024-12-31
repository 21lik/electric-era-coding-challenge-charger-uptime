# electric-era-coding-challenge-charger-uptime



## Compiling and Running

To compile the main application, run

```bash
javac ChargerUptime/src/main/App.java
```

and then run with

```bash
java ChargerUptime/src/main/App relative/path/to/input/file
```

To compile the test application, run

```bash
javac -cp "ChargerUptime/lib/junit-platform-console-standalone-1.8.2.jar" ChargerUptime/src/test/AppTest.java
```

and then run with

```bash
java -jar ChargerUptime/lib/junit-platform-console-standalone-1.8.2.jar -cp "ChargerUptime/src/" --select-class test.AppTest
```

Note: you must compile App before compiling AppTest, or compile both simultaneously with

```bash
javac -cp "ChargerUptime/lib/junit-platform-console-standalone-1.8.2.jar" ChargerUptime/src/main/App.java ChargerUptime/src/test/AppTest.java
```

Moreover, due to the nature of relative paths and they way they're hardcoded in AppTest, the unit tests are by default only guaranteed to work if you run them from the `electric-era-coding-challenge-charger-uptime` directory. If you wish to run them directly in the `src` subdirectory, modify the boolean constant `RUNNING_FROM_TEST_SUBDIRECTORY` in `AppTest.java` to `true`, then run
```bash
javac -cp "../lib/junit-platform-console-standalone-1.8.2.jar" main/App.java test/AppTest.java
java -jar ../lib/junit-platform-console-standalone-1.8.2.jar -cp "." --select-class test.AppTest
```
instead of the commands above.