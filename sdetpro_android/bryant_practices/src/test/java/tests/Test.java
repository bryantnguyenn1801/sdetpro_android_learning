package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static String getAVDName(String uuid) {
        String avdName = "";
        try {
            String command = "adb -s " + uuid + " emu avd name";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            avdName = reader.readLine().trim();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avdName;
    }

    public static void main(String[] args) {
        String uuid = "emulator-5554";  // Replace with your actual emulator UUID
        String avdName = getAVDName(uuid);
        System.out.println("AVD Name: " + avdName);
    }
}

