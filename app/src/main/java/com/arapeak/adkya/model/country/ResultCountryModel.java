package com.arapeak.adkya.model.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultCountryModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Country> countries = null;


    public ResultCountryModel() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
