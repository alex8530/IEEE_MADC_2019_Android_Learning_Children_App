package com.arapeak.adkya.model.getQuestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("fraction")
    @Expose
    private String fraction;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("retype_answer")
    @Expose
    private String retypeAnswer;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;

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

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRetypeAnswer() {
        return retypeAnswer;
    }

    public void setRetypeAnswer(String retypeAnswer) {
        this.retypeAnswer = retypeAnswer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
