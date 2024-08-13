package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

public class FileUtils {
    public void writeFile(String fileName, String responseBody, String outputPath) {
        try {
            Files.write(Paths.get(outputPath, fileName), responseBody.getBytes(), new OpenOption[0]);
        } catch (IOException var5) {
            IOException x = var5;
            System.err.format("IOException: %s%n", x);
        }

    }
}
