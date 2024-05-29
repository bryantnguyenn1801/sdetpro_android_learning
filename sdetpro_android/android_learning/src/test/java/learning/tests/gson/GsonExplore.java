package learning.tests.gson;

import com.google.gson.Gson;
import test_data.models.LoginCred;

public class GsonExplore {
    public static void main(String[] args) {
        String jsonObject = "{\n"
                + "  \"email\": \"teo@\",\n"
                + "  \"password\": \"12345678\"\n"
                + "}";

        Gson gson = new Gson();
        // Convert from JSON String into Java Object
        LoginCred loginCred = gson.fromJson(jsonObject, LoginCred.class);
        System.out.println(loginCred);

        // Convert from Java object into JSON string format
        System.out.println(gson.toJson(loginCred));
    }
}
