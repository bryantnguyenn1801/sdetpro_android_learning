package config;

import models.devices.Devices;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.google.gson.JsonObject;
import utils.JsonUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevicesManager {
    //Device Map is to check the device free or not
    private Map<String, Boolean> devicePool = new HashMap<>();
    //Device Info is to get Capabilities for one time in BeforeSuite
    private Map<String, DesiredCapabilities> deviceInfo = new HashMap<>();
    //Hold the name of device to free it in the end
    private static ThreadLocal<String> deviceUsing = new ThreadLocal<>();
    public static Map<String,Boolean> deviceIsRemote = new HashMap<>();

    //load the device pool
    public void loadDevicePool(String deviceJson){
        JsonObject map = JsonUtils.readJsonFile("devices/" + deviceJson);
        List<String> yourList = JsonUtils.parseJsonArrayToListString(map, "devicePool");
        System.out.println("Found devices");
        for (String s : yourList){
            System.out.println(s);
            //set default to false
            try{
                Devices device = new Devices().getDeviceInformationFrom(s);
                DesiredCapabilities cap = CapabilityGenerate.withProperties(device);
                deviceInfo.put(s, cap);
                deviceIsRemote.put(cap.getCapability("deviceName").toString(),device.isIS_ON_REMOTE_MACHINE());
                //try catch to ignore when failed to load device capabilities
                devicePool.put(s, false);
            } catch (IOException e){
                e.printStackTrace();
                System.out.println("Failed to load :" + s);
            }
        }
    }
    //load the device pool
    public void loadDevicePoolNew(String deviceJson){
        JsonObject map = JsonUtils.readJsonFile("devices/" + deviceJson);
        List<String> yourList = JsonUtils.parseJsonArrayToListString(map, "devicePool");
        System.out.println("Found devices");
        for (String s : yourList){
            System.out.println(s);
//            try{
//                Devices device = new Devices().getDeviceInformationFrom(s);
//                DesiredCapabilities cap = CapabilityGenerate.withPropertiesNew(device);
//                deviceInfo.put(s, cap);
//                deviceIsRemote.put(cap.getCapability("deviceName").toString(),device.isIS_ON_REMOTE_MACHINE());
//                //try catch to ignore when failed to load device capabilities
//                devicePool.put(s, false);
//            } catch (IOException e){
//                e.printStackTrace();
//                System.out.println("Failed to load :" + s);
//            }
        }
    }

    //Check the device free and return the Capabilities
    // return empty if not found any
    public synchronized DesiredCapabilities getDeviceFreeFromPool() throws InterruptedException{
        String deviceName = "";
        DesiredCapabilities cap = null;
        int attempt = 0;

        while (deviceName.equals("") && attempt < 30){
            System.out.println("Trying to find free devices:" + attempt);
            deviceName = findFreeDevice();
            attempt++;
            if (deviceName.equals("")){
                Thread.sleep(5000);
            }
            else{
                cap = deviceInfo.get(deviceName);
                deviceUsing.set(deviceName);
                devicePool.replace(deviceUsing.get(), true);
            }
        }
        return cap;
    }

    private String findFreeDevice(){
        String deviceName = "";
        for (Map.Entry<String, Boolean> stringBooleanEntry : devicePool.entrySet()){
            if (!stringBooleanEntry.getValue()){
                deviceName = stringBooleanEntry.getKey();
                devicePool.put(deviceName,true);
                System.out.println("Found free device " + deviceName);
                break;
            }
        }
        return deviceName;
    }

    public void releaseDevice(){
        if (!deviceUsing.get().equals("")){
            System.out.println("Release device " + deviceUsing.get());
            devicePool.put(deviceUsing.get(), false);
            deviceUsing.set("");
        }
    }
}
