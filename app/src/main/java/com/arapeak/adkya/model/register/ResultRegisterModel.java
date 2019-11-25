package com.arapeak.adkya.model.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultRegisterModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Data data;

    public ResultRegisterModel() {
    }

    public ResultRegisterModel(Boolean status, Data data) {
        this.status = status;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
