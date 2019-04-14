package Reports;

import LongRun.Device;
import LongRun.TestRunner;
import LongRun.Threads;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;



//singleton
public class Reporter extends ReportBasics {

    private static Reporter report = null;
    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    PrintWriter file;
    Device device;
    String FileContent;

    //singleton constructor (defined as private)
    private Reporter(String FileName, String projDir) throws FileNotFoundException {
        //Crete new report
        this.file=CreateReportFile(projDir, FileName, "csv");
        //print first line to this report
//        this.file.println("### Report: "+", "+FileName+" , "+Project.Main.startTime+" ###");
//        this.file.flush();

    }


    //Create report only once
    public static synchronized Reporter getInstance(String FileName , String projDir) throws FileNotFoundException {
        if(report == null)
            report=new Reporter(FileName, projDir);
        return report;
    }



    //T: add row to main report
    public synchronized void addRowToReport(PrintWriter dir, String testStatus, String testName, String deviceSN, String status,String testDuring,String deviceCategory, String deviceModel, String deviceName, String exception){
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = testStatus+","+
                currentTime+","+
                testName+","+
                deviceSN+","+
                status+","+
                deviceCategory+","+
                deviceModel+","+
                deviceName+","+
                testDuring+","+
                exception;
        System.out.println(line);
        dir.println(line);
        file.println(line);
        dir.write(System.lineSeparator());
        file.write(System.lineSeparator());
        dir.flush();
        file.flush();

    }

    public synchronized void WriteTotalResults(PrintWriter totalRes) {
        totalRes.write("Total Results - Passed: "+TestRunner.passCount+" Failed: "+ TestRunner.failCount);
        totalRes.write(System.lineSeparator());
        totalRes.write("-----------------------------------------------------");
        totalRes.write(System.lineSeparator());
        totalRes.flush();
    }
    public synchronized void WriteResults(PrintWriter totalRes, int passed, int failed, String SN) {
        totalRes.write(SN+" Passed: "+passed+" Failed: "+ failed);
        totalRes.write(System.lineSeparator());
        totalRes.write("-----------------------------------------------------");
        totalRes.write(System.lineSeparator());
        totalRes.flush();
    }

//    public synchronized void testDetails(String dir, String SessionID, String reportURL ){
//        String line;
//        line = SessionID+","+
//                reportURL;
//        System.out.println(line);
//        this.deviceFile=CreateReportFile(dir, "test", "csv");
//        deviceFile.println(line);
//        deviceFile.write(System.lineSeparator());
//        deviceFile.flush();
//    }
}
