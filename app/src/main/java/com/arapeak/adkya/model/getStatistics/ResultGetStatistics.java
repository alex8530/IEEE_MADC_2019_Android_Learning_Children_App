package com.arapeak.adkya.model.getStatistics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultGetStatistics {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private Statistic data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Statistic getData() {
        return data;
    }

    public void setData(Statistic data) {
        this.data = data;
    }



}
