package com.arapeak.adkya.model.getQuestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultGetQuestion {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Question data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Question getData() {
        return data;
    }

    public void setData(Question data) {
        this.data = data;
    }
}
