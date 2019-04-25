package LongRun;

import Reports.Reporter;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;

import com.experitest.client.Client;

public class BaseTest {
    protected Client client = null;
    String  dir, deviceName;
    Boolean wasInstalled = true;
    protected GridClient grid = null;
    String myCurrThreasdSN, testName;
    public String sessionID;
    Reporter reporter;
    Device device;
    public String path;
    long startTime, endTime;
    Threads current;
    @Before
    public void setUp() {
        //startTime = System.currentTimeMillis();
        System.out.println("Start:" + startTime);
        current = (Threads) Threads.currentThread();
        myCurrThreasdSN = "'" + current.getDeviceSN() + "'";
        grid = current.gridClient;
        testName = "Parallel";
        device = current.device;
        client = grid.lockDeviceForExecution(testName, "@serialnumber="+myCurrThreasdSN, 480, 300000);
        path = client.setReporter("xml", "", testName);
        sessionID = client.getSessionID();
        reporter = current.reporter;
    }

    @After
    public void tearDown() {
        //endTime = System.currentTimeMillis();
//        client.collectSupportData(current.direction,"",device.getDeviceName(),"","","");
        System.out.println("End:" + endTime);
        client.generateReport(false);
        client.releaseClient();
        //reporter.testDetails(myCurrThreasdSN,sessionID,path);
    }

}

