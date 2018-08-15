package graycode.sdproject.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Satyendra.Ranjan on 13-08-2018.
 */

public class UserDTO {
@SerializedName("status")
 public boolean status;
@SerializedName("message")
 public String message;
@SerializedName("data")
 public UserData data ;
}
