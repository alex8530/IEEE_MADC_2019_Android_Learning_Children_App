package com.arapeak.adkya.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIErrorLogin {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("errors")
    @Expose
    private List<List<String>> errors = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<List<String>> getErrors() {
        return errors;
    }

    public void setErrors(List<List<String>> errors) {
        this.errors = errors;
    }
}
