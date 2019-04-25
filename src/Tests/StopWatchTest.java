package Tests;

import LongRun.BaseTest;
import org.junit.Test;

public class StopWatchTest  extends BaseTest {

    @Test
    public void Test_StopWatch(){

//
            client.launch("com.hybrid.stopwatch/.MainActivity", false, true);
            if (client.isElementFound("NATIVE", "//*[@text='LATER']")) {
                client.click("NATIVE", "//*[@text='LATER']", 0, 1);
            }
            client.waitForElement("Native", "//*[@id='txt_stopwatch_time']", 0, 40000);
            String time1 = client.elementGetText("Native", "//*[@id='txt_stopwatch_time']", 0);
            client.sleep(1000);
            String time2 = client.elementGetText("Native", "//*[@id='txt_stopwatch_time']", 0);
            if (time1.equals(time2)) {
                client.waitForElement("Native", "//*[@id='start_pause_button_sw']", 0, 40000);
                client.click("Native", "//*[@id='start_pause_button_sw']", 0, 1);
            }

            for (int j = 0; j < 20; j++) {
                if (client.isElementFound("Native", "//*[@id='txt_stopwatch_time']")) {
                    String elementGetText = client.elementGetText("Native", "//*[@id='txt_stopwatch_time']", 0);
                    client.report(elementGetText, true);
                } else {
                    client.report("Cannot find element //*[@id='txt_stopwatch_time']", false);
                    if (client.isElementFound("NATIVE", "//*[@text='LATER']")) {
                        client.click("NATIVE", "//*[@text='LATER']", 0, 1);
                    }
                }
            }

    }
}
