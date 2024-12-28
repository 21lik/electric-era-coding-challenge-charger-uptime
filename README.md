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