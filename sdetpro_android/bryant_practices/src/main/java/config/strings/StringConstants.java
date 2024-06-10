package config.strings;

import javax.annotation.Nullable;
import java.util.Arrays;

public class StringConstants {

    public static final String LOGIN_AND_SIGNUP_TITLE = "Login / Sign up Form";
    public static final String POP_UP_SIGNED_UP_MSG = "You successfully signed up!";
    public static final String POP_UP_LOGGED_IN_MSG = "You are logged in!";
    public static final String INVALID_EMAIL_WARNING_MSG = "Please enter a valid email address";
    public static final String INVALID_PASSWORD_WARNING_MSG = "Please enter at least 8 characters";
    public static final String FORMS_COMPONENTS_TITLE = "Form components";
    public static final String TURN_ON_SWITCH_MESSAGE = "Click to turn the switch ON";
    public static final String TURN_OFF_SWITCH_MESSAGE = "Click to turn the switch OFF";
    public static final String[] DROPDOWN = {"webdriver.io is awesome", "Appium is awesome", "This app is awesome"};
    public static final String POP_UP_ACTIVE_BTN_MSG = "This button is active";

    public enum DropDownValues {
        WEB_DRIVER_IO(DROPDOWN[0]),
        APPIUM(DROPDOWN[1]),
        THIS_APP(DROPDOWN[2]);

        private String value;

        DropDownValues(String value) {
            this.value = value;
        }

        @Nullable
        public static DropDownValues of(String value) {
            return Arrays.stream(values()).filter(stt -> stt.toString().equalsIgnoreCase(value.trim())).findAny()
                    .orElse(null);
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
