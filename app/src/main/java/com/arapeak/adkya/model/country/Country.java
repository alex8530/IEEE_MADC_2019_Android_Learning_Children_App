package com.arapeak.adkya.model.country;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("image")
    @Expose
    private String image;

    public Country() {
    }

    public Country(Integer id, String name, String code, String image) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }


}
