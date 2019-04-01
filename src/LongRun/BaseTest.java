package LongRun;

import java.io.FileWriter;
import java.util.Date;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;

import com.experitest.client.Client;

public class BaseTest {
    protected Client client = null;
    Date date = new Date();
    FileWriter writer;
    Scanner sc = null;
    String myCurrThreasd, myCurrThreasdPathToDevice, deviceNameFromConf, myCurrProfile, profile, dir, deviceName,
            folderName, path;
    Boolean reboot = false, wasInstalled = true, specDevice;

    @Before
    public void setUp() {
        Threads current = (Threads) Threads.currentThread();
        client = current.client;
        myCurrThreasd = current.getDeviceName();
        myCurrThreasdPathToDevice = Threads.getPathName();
        myCurrProfile = Threads.getProfile();
        dir = System.getProperty("user.dir");
        path = dir + "\\" + myCurrThreasdPathToDevice + "\\" + myCurrThreasd;
        //client.setReporter("xml", path, this.getClass().getName());
    }

    @After
    public void tearDown() {
        client.generateReport(false);
    }

}
