package LongRun;

import java.io.*;
import java.util.List;

import Reports.Reporter;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.File;
import java.io.PrintWriter;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class Threads extends Thread {

    public Client client;
    Reporter reporter;
    Device device;
    static String path;
    List<Class<?>> testsList;
    Result result;
    Class<?> testName;
    String writer = null, deviceName, direction;
    static Boolean reboot, isRunning = true, repeatedly = true, specDevice;


    public Threads(Device device,String path, String name, List<Class<?>> classlist, Boolean specDevice, Class<?> test,
                   String fileWriter, Boolean grid, String os, GridClient gridClient){
        this.device=device;
        this.path = path;
        this.deviceName = name;
        this.testsList = classlist;
        this.specDevice = specDevice;
        this.testName = test;
        this.writer = fileWriter;
        this.reboot = false;
        direction = System.getProperty("user.dir") + "\\" + path + "\\" + deviceName;
        new File(direction).mkdirs();
        reporter = Reporter.getInstance("Test_Ortal",System.getProperty("user.dir") + "\\" + path);
    }

    @Override
    public void run() {
        while (TestRunner.startTime < TestRunner.endTime) {
            for (Class<?> test : testsList) {
                long startTime = System.nanoTime();
                RunTest(test);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime)/1000000;
                String durationTime = String.valueOf(duration);
                reporter.addRowToReport("null",test.getName(),deviceName,device.getVersion(),durationTime,"null","","");
            }
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

    public static String getPathName() {
        return path;
    }

    public static Boolean ifSpecDevice() {
        return specDevice;
    }

    public static Boolean getReboot() {
        return reboot;
    }

    public void RunTest(Class<?> test) {

        result = JUnitCore.runClasses(test);
    }
    public void WriteResults() {

        try (FileWriter fileWriter = new FileWriter(writer);//TODO
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.write("Results for: " + deviceName);
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
