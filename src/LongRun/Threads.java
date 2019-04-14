package LongRun;

import java.io.*;
import java.util.List;

import Reports.Reporter;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import com.experitest.client.GridClient;

public class Threads extends Thread {

    Reporter reporter;
    Device device;
    List<Class<?>> testsList;
    Result result;
    String writer = null, deviceSN, runFolder, direction, pathToFolder = System.getProperty("user.dir");
    GridClient gridClient;
    PrintWriter testFile, totalRes;
    int passed, failed;

    public Threads(Device device, String RunFolder, String SN, List<Class<?>> classlist, String fileWriter, GridClient gridClient, Reporter reporter, PrintWriter totalRes) throws FileNotFoundException {
        this.device = device;
        this.runFolder = RunFolder;
        this.deviceSN = SN;
        this.testsList = classlist;
        this.writer = fileWriter;
        this.gridClient = gridClient;
        this.totalRes = totalRes;
        direction = pathToFolder + File.separator + runFolder + File.separator + deviceSN;
        new File(direction).mkdirs();
        File dirToTestReport  = new File(direction + File.separator + "results.csv");
        testFile = new PrintWriter(dirToTestReport);
        device.setDeviceFolderPath(direction);
        this.reporter = Reporter.getInstance("AllResults",pathToFolder + File.separator + runFolder);
        this.passed=0;
        this.failed=0;
    }

    @Override
    public void run() {
        while (TestRunner.startTime < TestRunner.endTime) {
            for (Class<?> test : testsList) {
                long startTime = System.currentTimeMillis();
                System.out.println("Start:" + startTime);
                String res = RunTest(test);
                long endTime = System.currentTimeMillis();
                System.out.println("End:" + endTime);
                if(res.equals("[]")){
                    reporter.addRowToReport(testFile,"Passed",test.getName(), deviceSN, device.getVersion(), calculteDuration(startTime, endTime),device.getCategory(),device.getModel(),device.getDeviceName(), res);
                    TestRunner.passCount++;
                    passed++;
                }
                else{
                    reporter.addRowToReport(testFile, "Failed",test.getName(), deviceSN, device.getVersion(), calculteDuration(startTime, endTime),device.getCategory(),device.getModel(),device.getDeviceName(), res);
                    TestRunner.failCount++;
                    failed++;
                }
            }
            reporter.WriteResults(totalRes,passed,failed, deviceSN);
            passed=0;
            failed=0;

        }
        reporter.WriteTotalResults(totalRes);

    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public String RunTest(Class<?> test) {
        result = JUnitCore.runClasses(test);
        return result.getFailures().toString();
    }

    public String calculteDuration(long start, long end) {
        long duration = (end - start);
        long miillis = TimeUnit.MILLISECONDS.toMinutes(duration);
        String durationTime = String.valueOf(miillis);
        return durationTime;
    }

    public void WriteResults(PrintWriter totalRes, String testName) {

        totalRes.write(testName+ "Passed: "+TestRunner.passCount+"Failed: "+ TestRunner.failCount);
        totalRes.write(System.lineSeparator());
        totalRes.flush();
//        try (FileWriter fileWriter = new FileWriter(writer);//TODO
//             PrintWriter printWriter = new PrintWriter(fileWriter)) {
//            printWriter.write("Results for: " + deviceSN);
//            printWriter.write(System.lineSeparator());
//            printWriter.write("Successful:" + (result.getRunCount() - result.getFailureCount() - result.getIgnoreCount()));
//            printWriter.write("  Failed: " + result.getFailureCount());
//            printWriter.write(System.lineSeparator());
//            for (Failure failure : result.getFailures()) {
//                printWriter.write("The Exception: " + failure.toString());
//                printWriter.write(System.lineSeparator());
//            }
//            printWriter.write(System.lineSeparator());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
