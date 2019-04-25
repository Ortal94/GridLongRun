package LongRun;
import org.junit.*;

import java.util.Random;

public class Test_Ortal extends BaseTest{

    Random rand = new Random();

    @Test
    public void ParallelCommands(){
        //client.startLoggingDevice(device.getDeviceFolderPath());
        client.deviceAction("Unlock");
        client.setLocation("36.296238", "-91.933594"); // USA
        client.install("cloud:com.experitest.ExperiBank", true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.sleep(500);
        Assert.assertTrue(client.isElementFound("native", "//*[@text='Login']"));
        client.closeKeyboard();
        client.applicationClose("com.experitest.ExperiBank/.LoginActivity");
        client.uninstall("cloud:com.experitest.ExperiBank");
        client.getDeviceLog();
        client.applicationClearData("com.experitest.ExperiBank/.LoginActivity");
        client.deviceAction("Home");
//        client.reboot(240000);
//        int num = rand.nextInt(10) + 1;
//        System.out.println("The number: "+num);
//        System.out.println("Device SN: "+myCurrThreasdSN);
//
//        if(num%2==0){
//            System.out.println("Stop Logging Device");
           //client.stopLoggingDevice();


        // }
    }

}