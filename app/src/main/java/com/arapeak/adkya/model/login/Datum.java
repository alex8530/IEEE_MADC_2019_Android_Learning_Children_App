package com.arapeak.adkya.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("name")
    @Expose
    private List<String> name = null;

    public List<String> getEmail() {
        return email;
    }
    public List<String> getName() {
        return name;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }
}
