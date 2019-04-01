package LongRun;

import org.junit.*;

import java.util.Scanner;

public class Test_1_android extends BaseTest {

    @Test
    public void testUntitled() {

        String apps = client.getInstalledApplications();
        if (!apps.contains("com.experitest.ExperiBank")) {
            wasInstalled = false;

            client.install(dir + "\\apps\\eribank.apk", true, true);

        }
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        String csvUserName;
        String csvPassword;
        Scanner inputStream = null;
        String version = client.getDeviceProperty("app.version");
        deviceName = client.getDeviceProperty("device.name");
        double deviceversion = Double.parseDouble(version);

        inputStream.nextLine();// ignore the first line - Headlines

        while (inputStream.hasNext()) {
            String data = inputStream.nextLine(); // Read line
            String[] values = data.split(","); // Split the line to an array
            csvUserName = values[0];
            csvPassword = values[1];
            if (csvUserName.isEmpty()) {
                client.elementSendText("NATIVE", "hint=Username", 0, " "); // send the text from the csv file to "user
                // name"
            } else {
                client.elementSendText("NATIVE", "hint=Username", 0, csvUserName); // send the text from the csv file to
                // "user name"

            }
            if (csvPassword.isEmpty()) {
                client.elementSendText("NATIVE", "hint=Password", 0, " "); // send the text from the csv file to
                // "password"

            } else {
                client.elementSendText("NATIVE", "hint=Password", 0, csvPassword); // send the text from the csv file to
                // "password"
            }
            client.closeKeyboard();

            client.click("NATIVE", "text=Login", 0, 1);
            if (client.isElementFound("NATIVE", "xpath=//*[@text='Invalid username or password!']", 0)) // check if user
                // name or
                // password are
                // not correct.
                client.click("NATIVE", "text=Close", 0, 1);
            else
                break;
        }
        client.getElementCountIn("NATIVE", "xpath=//*[@id='scrollView1']", 0, "Inside", "NATIVE",
                "class=android.widget.Button", 0, 0);
        String oldBalance = client.getTextIn("NATIVE", "id=makePaymentButton", 0, "WEB", "Up", 0, 0);
        String oldAmount = oldBalance.substring(17, (oldBalance.length() - 3));

        client.isElementFound("NATIVE", "xpath=//*[@text='Make Payment']", 0); // check if the user is log in
        inputStream.close(); // close the connection to the csv file
        client.click("NATIVE", "text=Make Payment", 0, 1);
        client.elementSendText("NATIVE", "hint=Name", 0, "company");
        client.elementSendText("NATIVE", "hint=Amount", 0, "5");
        String amount = client.elementGetText("NATIVE", "xpath=//*[@id='amountTextField']", 0);
        client.click("NATIVE", "hint=Country", 0, 1);
        client.deviceAction("BKSP");
        client.click("NATIVE", "text=Select", 0, 1);
        client.click("NATIVE", "text=USA", 0, 1);
        client.elementSendText("NATIVE", "hint=Phone", 0, "0");
        client.click("NATIVE", "text=Send Payment", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        String newBalance = client.getTextIn("NATIVE", "id=makePaymentButton", 0, "WEB", "Up", 0, 0);
        String newAmount = newBalance.substring(17, (newBalance.length() - 3));
        double X1 = Double.parseDouble(oldAmount);
        double X2 = Double.valueOf(newAmount);
        int amount1 = Integer.valueOf(amount);
        double calc = X1 - amount1;
        if (calc == X2) {
            client.report("The Amount is equal", true);
        } else {

            client.report("The Amount is not equal \"failed\"", false);
        }
        String appName = client.getCurrentApplicationName();
        System.out.println("app name:" + appName);
        client.click("NATIVE", "text=Logout", 0, 1);
        client.applicationClearData("com.experitest.ExperiBank/.LoginActivity");
        client.deviceAction("Menu");
        client.hybridClearCache(true, true);
        client.clearLocation();
        String count = client.getCounter("battery");
        System.out.println(count);
        client.stopLoggingDevice();
        client.addTestProperty("somePropertyNameTest", "value");
        if (!wasInstalled) {
            client.uninstall("com.experitest.ExperiBank");
        }

    }

}
