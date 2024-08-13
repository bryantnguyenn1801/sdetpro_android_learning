package test_data;

import com.google.gson.Gson;
import test_data.models.LoginCred;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Arrays;

public class DataObjectBuilder {
    public static <T> T buildDataObject(String relativeFilePath, Class<T> dataType) {
        T returnedData;

        // Read the JSON content
        String absoluteFilePath = System.getProperty("user.dir").concat(relativeFilePath);
        try (
                Reader reader = Files.newBufferedReader(Paths.get(absoluteFilePath))
        ) {
            Gson gson = new Gson();
            returnedData = gson.fromJson(reader, dataType);
        } catch (NoSuchFileException noSuchFileException) {
            throw new RuntimeException("[ERR] Could not find the file: ".concat(absoluteFilePath));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        // Convert into type T
        return returnedData;
    }

    public static void main(String[] args) {
        String inValidFilePath = "src/test/java/test_data/authen/LoginCredData.json";
        String validFilePath = "/src/test/java/test_data/authen/LoginCredData.json";

//    LoginCred loginCred = buildDataObject(inValidFilePath, LoginCred.class);
        LoginCred[] loginCred = buildDataObject(validFilePath, LoginCred[].class);

        System.out.println(Arrays.toString(loginCred));
    }
}
