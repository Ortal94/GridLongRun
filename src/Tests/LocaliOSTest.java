package Tests;

import com.experitest.client.*;
import org.junit.*;
/**
 *
 */
public class LocaliOSTest {
    private String host = "http://192.168.2.191";

    private String accessKey = "eyJ4cC51IjoxLCJ4cC5wIjoxLCJ4cC5tIjoiTVRVMU5qQXpNemcwT0RjM01RIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NzEzOTM4NDgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.uJExH1XH6HWUOlWj57KaJGdzWGBBxGg3dHhAw8CW24E";
    protected Client client = null;
    protected GridClient grid = null;
    @Before
    public void setUp(){
        // In case your user is assign to a single project you can provide an empty string,
        // otherwise please specify the project name
        grid = new GridClient(accessKey, host);
        client = grid.lockDeviceForExecution("Quick Start seetest iOS NATIVE Demo", "@serialnumber='00d064b580b7e36184819a9ce668f8c9f1d2413f'", 10, 50000);
        client.setReporter("xml", "", "Quick Start seetest iOS Native Demo");

    }

    @Test
    public void quickStartiOSNativeDemo() {
        client.elementSwipeWhileNotFound("NATIVE", "class=UITableView", "Up", 667, 100, "NATIVE", "accessibilityLabel=Tinted", 0, 500, 3, true);
    }

    @After
    public void tearDown(){
        // Generates a report of the test case.
        // For more information - https://docs.experitest.com/display/public/SA/Report+Of+Executed+Test
        client.generateReport(false);
        // Releases the client so that other clients can approach the agent in the near future.
        client.releaseClient();
    }
}
