package graycode.sdproject.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Satyendra.Ranjan on 14-08-2018.
 */

public class User {
    @SerializedName("name")
    public String name ;

    @SerializedName("image")
    public String image;

    @SerializedName("items")
    public List<String> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }



}
