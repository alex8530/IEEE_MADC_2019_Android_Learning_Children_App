package com.arapeak.adkya.model.getSecondMaterial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultGetSecondMaterial {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<SecondMaterialData> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<SecondMaterialData> getData() {
        return data;
    }

    public void setData(List<SecondMaterialData> data) {
        this.data = data;
    }

}
