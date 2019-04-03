package LongRun;
import org.junit.Test;

import java.util.Random;

public class LongTest extends BaseTest{

    //Random rand = new Random();

    @Test
    public void LongTest(){
        //client.startLoggingDevice(System.getProperty("user.dir"));
       // for(int j=0;j<100;j++) {
          for (int i = 0; i < 100; i++) {
              client.launch("chrome:https://www.bing.com/", false, false);
              client.elementSendText("WEB", "id=sb_form_q", 0, "a");
              client.click("WEB", "id=sbBtn", 0, 1);
          }
          for (int i = 0; i < 100; i++) {
              client.launch("chrome:https://www.bing.com/", false, false);
              client.elementSendText("WEB", "id=sb_form_q", 0, "b");
              client.click("WEB", "id=sbBtn", 0, 1);
          }
          for (int i = 0; i < 100; i++) {
              client.launch("chrome:https://www.bing.com/", false, false);
              client.elementSendText("WEB", "id=sb_form_q", 0, "c");
              client.click("WEB", "id=sbBtn", 0, 1);
          }
     // }
//        int num = rand.nextInt(10) + 1;
//        System.out.println(num);
//        if(num%2==0){
//            client.stopLoggingDevice();
//        }
    }

}
