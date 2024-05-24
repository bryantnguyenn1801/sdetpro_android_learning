package utils;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DataUser {
    @SerializedName("validEmail")
    public String validEmail;
    @SerializedName("validPassword")
    public String validPassword;
    @SerializedName("invalidEmail")
    public String invalidEmail;
    @SerializedName("invalidPassword")
    public String invalidPassword;

}
