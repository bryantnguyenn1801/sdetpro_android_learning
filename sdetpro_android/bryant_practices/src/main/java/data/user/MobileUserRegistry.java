package data.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import utils.JsonUtils;
import utils.DataUser;
import utils.ObjectMappingUtils;

public class MobileUserRegistry {
    public static DataUser USER_LOGIN_WITH_INVALID_EMAIL;
    public static DataUser USER_LOGIN_WITH_INVALID_PASSWORD;
    public static DataUser USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD;

    public MobileUserRegistry(String platform) {
        JsonObject map = null;

        /*TODO: Need better logic to handle mobile and env platform */
        if (platform.equalsIgnoreCase("android")) {
            map = JsonUtils.readJsonFile("users/android_user_data.json");
        } else {
            map = JsonUtils.readJsonFile("users/ios_user_data.json");
        }

        USER_LOGIN_WITH_INVALID_EMAIL = (DataUser) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("user_login_with_invalid_email").toString(), DataUser.class);
        USER_LOGIN_WITH_INVALID_PASSWORD = (DataUser) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("user_login_with_invalid_password").toString(), DataUser.class);
        USER_LOGIN_WITH_VALID_EMAIL_AND_PASSWORD = (DataUser) ObjectMappingUtils.parseJsonToModel(map.getAsJsonObject("user_login_with_valid_email_and_password").toString(), DataUser.class);
    }
}
