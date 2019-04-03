package LongRun;

import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Before;

import com.experitest.client.Client;

public class BaseTest {
    protected Client client = null;
    String  dir, deviceName;
    Boolean wasInstalled = true;
    protected GridClient grid = null;

    @Before
    public void setUp() {
        Threads current = (Threads) Threads.currentThread();
        String myCurrThreasdSN = "'" + current.getDeviceSN() + "'";
        grid = current.gridClient;
        client = grid.lockDeviceForExecution("Parallel", "@serialnumber="+myCurrThreasdSN, 480, 300000);
        client.setReporter("xml", "", "Parallel");
    }

    @After
    public void tearDown() {
        client.generateReport(false);
        //client.releaseClient();
    }

}
