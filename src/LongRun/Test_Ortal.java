package LongRun;

import com.experitest.client.*;
import org.junit.*;
/**
 *
 */
public class Test_Ortal {
    private String host = "http://10.0.75.1";

    private String accessKey = "eyJ4cC51IjoxLCJ4cC5wIjoyLCJ4cC5tIjoiTVRVMU5EQXpORFExTlRnMk1RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NjkzOTQ0NTUsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.BezSQDDjEA4FAPGDPRdGF1GR7-f9BrpXojLhsfqyT2c";
    protected Client client = null;
    protected GridClient grid = null;
    @Before
    public void setUp(){
        Threads current = (Threads) Threads.currentThread();
        String myCurrThreasd = "'" + current.getDeviceName() + "'";
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(accessKey, host);
        client = grid.lockDeviceForExecution("Parallel", "@serialnumber="+myCurrThreasd, 480, 300000);
        //client = grid.lockDeviceForExecution("Parallel", "@serialnumber='ce051605686c683b03'", 480, 300000);

        System.out.println("On Test"+myCurrThreasd);
        client.setReporter("xml", "", "Parallel");

    }

    @Test
    public void ParallelCommands(){

        // This command "setDevice" is not applicable for GRID execution
        //client.setDevice("adb:"+LongRun.Threads.getDeviceName());
        client.deviceAction("Unlock");
        client.setLocation("36.296238", "-91.933594"); // USA
        client.install("cloud:com.experitest.ExperiBank", true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.closeKeyboard();
        client.applicationClose("com.experitest.ExperiBank/.LoginActivity");
        client.uninstall("cloud:com.experitest.ExperiBank");
        client.getDeviceLog();
        client.applicationClearData("com.experitest.ExperiBank/.LoginActivity");
        client.deviceAction("Home");
        client.reboot(240000);
//
//
//        client.elementSendText("NATIVE", "hint=Username", 0, "company");
//        client.elementSendText("NATIVE", "hint=Password", 0, "company");
//        client.click("NATIVE", "text=Login", 0, 1);
//        client.click("NATIVE", "text=Make Payment", 0, 1);
//        client.elementSendText("NATIVE", "hint=Phone", 0, "1234567");
//        client.elementSendText("NATIVE", "hint=Name", 0, "Jon Snow");
//        client.elementSendText("NATIVE", "hint=Amount", 0, "50");
//        client.click("NATIVE", "hint=Country", 0, 1);
//        client.click("NATIVE", "text=Select", 0, 1);
//        client.click("NATIVE", "text=Switzerland", 0, 1);
//        client.click("NATIVE", "text=Send Payment", 0, 1);
//        client.click("NATIVE", "text=Yes", 0, 1);
//        client.click("NATIVE", "text=Logout", 0, 1);
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        //client.releaseClient();
    }
}