package com.arapeak.adkya.model.getQuestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
