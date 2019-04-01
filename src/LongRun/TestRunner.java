package LongRun;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.experitest.client.GridClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;



public class TestRunner {
    protected static boolean chooseSpesificDevices; //Choose specific devices or run on all connected devices
    protected static List<Device> devices = new ArrayList<>(); // the devices list which we run on
    public static String PrintDevicesInfo, PrintDeviceSN;
    public static final String delimiter = "\r\n";
    public static int TimeToRun = 10*60*60*1000;//Seconds * minutes * hours
    public static long startTime = System.currentTimeMillis();
    public static long endTime  = System.currentTimeMillis() + TimeToRun;

    public static void main(String[] args) throws Exception  {

        String host = "localhost";
        int port = 8889;
        Date date = new Date();
        List<Class<?>> classlistAndroid = new ArrayList<>();
        Threads myThread = null;
        HashMap<String, Class<?>> hashMap = new HashMap<String, Class<?>>();
        hashMap.put("LongRun.Test_1_android", Test_Ortal.class);
        GridClient gridClient = new GridClient("admin","Aa123456","","http://oyona-pc");
        long time = date.getTime();
        File file = new File("Run_" + time);
        file.mkdirs();
        String path = file.getName();
        String fileRes = file.getName() + File.separator + "results.txt";


        // classlistAndroid.add(LongRun.Test_Ortal.class);
        classlistAndroid.add(Test_Ortal.class);


            try {
                devices = getDevices(gridClient.getDevicesInformation());
                PrintDevicesInfo = "";
                PrintDeviceSN = "";
                for (int i = 0; i < devices.size(); i++) {//devices.size()
                    PrintDevicesInfo += "#" + (i + 1) + " " + devices.get(i).toString() + delimiter + delimiter;
                    PrintDeviceSN += devices.get(i).getSerialnumber() + delimiter;
                    myThread = new Threads(devices.get(i),path, devices.get(i).getSerialnumber(), classlistAndroid, false, null, fileRes, true, "'android'", gridClient);
                    myThread.start();
                }
                System.out.println(PrintDeviceSN);
                System.out.println(PrintDevicesInfo);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    //}
    //get devices and enter them to hash map
    public static List<Device> getDevices(String xml) throws Exception {

        //The user can chose if he want to run on specific devices or on all connected devices:
        //1. If the user want to run on all connected devices:
        //2. if the user wants to run on specific devices:
        List<Device> devicesMap = new ArrayList<>();
        NodeList nodeList = parseDevices(xml); //parse the xml and get back nodeList with all the devices

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            Element eElement = (Element) nNode;
            Device device = new Device(
                    eElement.getAttribute("serialnumber"),
                    eElement.getAttribute("name"),
                    eElement.getAttribute("os"),
                    eElement.getAttribute("version"),
                    eElement.getAttribute("model"),
                    eElement.getAttribute("category"),
                    eElement.getAttribute("manufacture"),
                    eElement.getAttribute("remote"),
                    eElement.getAttribute("reservedtoyou"));
//            System.out.println(eElement.getAttribute("serialnumber"));

            //if we don't want to use all devices, check if the device is in the list of device
//            if (chooseSpesificDevices) {
//
//                for (String DeviceSN : Choosedevices) {
//                    if (eElement.getAttribute("serialnumber").equalsIgnoreCase(DeviceSN)
//                            || eElement.getAttribute("name").toLowerCase().contains(DeviceSN.toLowerCase())) {
//                        //Create new folder for this device
//                        device.setDeviceFolderPath(Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
//                        devicesMap.add(device);
//
//
//                    }
//                }
//            } else { //if use all devices is true
                //Create new folder for this device
                //device.setDeviceFolderPath(createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
                devicesMap.add(device);

            //}
        }


        return devicesMap;

    }
    //Parse the devices XML from getDevicesInformation and returns A nodeList Element
    private static NodeList parseDevices(String xml) throws Exception {
        //Using JDOM to parse the devices xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(xml);
        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);
        doc.getDocumentElement().normalize();
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/devices/device";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        System.out.println("Number of Devices connected: " + nodeList.getLength());

        return nodeList;

    }

    //create new directory, get the directory path and the new folder name
    public static String createNewDir(String path, String folderName) {
        File newDir = new File(path + "\\" + folderName);
        String createdPath = path + "\\" + folderName;
        //create
        if (!newDir.exists()) {
            System.out.println("creating directory: " + newDir.getName());
            try {
                newDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        return createdPath;
    }
}