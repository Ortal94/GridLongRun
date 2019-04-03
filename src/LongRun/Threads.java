package LongRun;

import java.io.*;
import java.util.List;

import Reports.Reporter;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class Threads extends Thread {

    public Client client;
    Reporter reporter;
    Device device;
    static String runFolder;
    List<Class<?>> testsList;
    Result result;
    String writer = null, deviceSN, direction, pathToFolder = System.getProperty("user.dir");
    GridClient gridClient;

    public Threads(Device device,String RunFolder, String SN, List<Class<?>> classlist, String fileWriter, GridClient gridClient){
        this.device=device;
        this.runFolder = RunFolder;
        this.deviceSN = SN;
        this.testsList = classlist;
        this.writer = fileWriter;
        this.gridClient = gridClient;
        direction = pathToFolder + File.separator + runFolder + File.separator + deviceSN;
        new File(direction).mkdirs();
        reporter = Reporter.getInstance("Results",pathToFolder + File.separator + runFolder);
    }

    @Override
    public void run() {
//        if(specDevice){
//            while (TestRunner.startTime < TestRunner.endTime) {
//                long startTime = System.currentTimeMillis();
//                String res = RunTest(testName);
//                long endTime = System.currentTimeMillis();
//                reporter.addRowToReport(testName.getName(), deviceSN, "", calculteDuration(startTime, endTime), "", "", res);
//            }
//        }
//        else {
            while (TestRunner.startTime < TestRunner.endTime) {
                for (Class<?> test : testsList) {
                    long startTime = System.currentTimeMillis();
                    System.out.println("Start:" + startTime);
                    String res = RunTest(test);
                    long endTime = System.currentTimeMillis();
                    System.out.println("End:" + endTime);
                    reporter.addRowToReport(test.getName(), deviceSN, device.getVersion(), calculteDuration(startTime,endTime), "", "", res);
                }
            }
        //}
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public String RunTest(Class<?> test) {
        result = JUnitCore.runClasses(test);
        return result.getFailures().toString();
    }

    public String calculteDuration(long start, long end){
        long duration = (end - start);
        long miillis = TimeUnit.MILLISECONDS.toMinutes(duration);
        String durationTime = String.valueOf(miillis);
        return durationTime;
    }

    public void WriteResults() {

        try (FileWriter fileWriter = new FileWriter(writer);//TODO
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.write("Results for: " + deviceSN);
            printWriter.write(System.lineSeparator());
            printWriter.write("Successful:" + (result.getRunCount() - result.getFailureCount() - result.getIgnoreCount()));
            printWriter.write("  Failed: " + result.getFailureCount());
            printWriter.write(System.lineSeparator());
            for (Failure failure : result.getFailures()) {
                printWriter.write("The Exception: " + failure.toString());
                printWriter.write(System.lineSeparator());
            }
            printWriter.write(System.lineSeparator());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
