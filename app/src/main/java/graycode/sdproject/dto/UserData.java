package graycode.sdproject.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Satyendra.Ranjan on 13-08-2018.
 */

public class UserData {
    @SerializedName("users")
    public List<User> users;
    @SerializedName("has_more")
    public boolean has_more ;
}
