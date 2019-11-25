package com.arapeak.adkya.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private User user;


    public Data() {
    }

    public Data(String apiToken, User user) {
        this.apiToken = apiToken;
        this.user = user;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
