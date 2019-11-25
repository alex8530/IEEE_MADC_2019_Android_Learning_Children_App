package com.arapeak.adkya.model.socialLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("street_first")
    @Expose
    private Object streetFirst;
    @SerializedName("street_second")
    @Expose
    private Object streetSecond;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("zipcode")
    @Expose
    private Object zipcode;
    @SerializedName("country")
    @Expose
    private Country country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Object getStreetFirst() {
        return streetFirst;
    }

    public void setStreetFirst(Object streetFirst) {
        this.streetFirst = streetFirst;
    }

    public Object getStreetSecond() {
        return streetSecond;
    }

    public void setStreetSecond(Object streetSecond) {
        this.streetSecond = streetSecond;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
