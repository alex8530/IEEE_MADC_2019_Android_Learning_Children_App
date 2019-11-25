package com.arapeak.adkya.model.socialLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("api_token")
    @Expose
    private String apiToken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
