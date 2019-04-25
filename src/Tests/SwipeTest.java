package Tests;
import com.experitest.client.*;
import org.junit.*;
/**
 *
 */
public class SwipeTest {
    private String host = "http://192.168.2.212";

    private String accessKey = "eyJ4cC51IjoxLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMU5EY3pNell6TmpreE1BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NzAwOTM2MzYsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.xLl_k1Zkdt9f7U1eLnIbiP6P69jOg_SrdVGe7elgHGE";
    protected Client client = null;
    protected GridClient grid = null;
    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(accessKey, host);
        client = grid.lockDeviceForExecution("Swipe", "@serialnumber='17b3dcc17d43'", 10, 50000);
        client.setReporter("xml", "", "Swipe");

    }
    @Test
    public void Test1(){
        int num = 10;
        switch (num){
            case 1: //WEB - the element exist
                client.swipeWhileNotFound("Down", 100, 2000, "WEB", "xpath=//*[@name='&lpos=sitenavmobile+sports+x_games']", 0, 1000, 5, true);
                break;
            case 2: //WEB - the element does not exist
                client.swipeWhileNotFound("Down", 100, 2000, "WEB", "xpath=//*[@name='&lpos=sitenavmobile+sports+x_games///']", 0, 1000, 5, true);
                break;
            case 3: //WEB - the element exist
                client.elementSwipeWhileNotFound("WEB", "xpath=//*[@nodeName='UL' and ./*[./*[@nodeName='H1']]]", "Down", 100, 500, "WEB", "xpath=//*[@name='&lpos=sitenavmobile+sports+x_games']", 0, 1000, 5, true);
                break;
            case 4: //WEB - the element does not exist
                client.elementSwipeWhileNotFound("WEB", "xpath=//*[@nodeName='UL' and ./*[./*[@nodeName='H1']]]", "Down", 100, 500, "WEB", "xpath=//*[@name='&lpos=sitenavmobile+sports+x_games///']", 0, 1000, 5, true);
                break;
            case 5: //NATIVE - the element exist
                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", "//*[@text='Philippines']", 0, 1000, 5, true);
                break;
            case 6: //NATIVE - the element does not exist
                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", "//*[@text='Philippiness']", 0, 1000, 5, true);
                break;
            case 7: //NATIVE - the element exist
                //TODO
            case 8: //NATIVE - the element does not exist
                //TODO
            case 9:
                client.elementSwipeWhileNotFound("NATIVE","//*[@class='android.widget.ListView']","DOWN",0,500,"NATIVE","//*[@contentDescription='2023' or @contentDescription='2023 selected']",0,1000,10,true);
                break;
            case 10:
                client.swipeWhileNotFound("Down", 100, 500, "NATIVE", "//*[@contentDescription='2023' or @contentDescription='2023 selected']", 0, 1000, 10, true);
                break;


        }






    }
    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient(false);
    }

}
