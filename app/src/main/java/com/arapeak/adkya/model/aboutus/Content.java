package com.arapeak.adkya.model.aboutus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("our_vision")
    @Expose
    private String ourVision;
    @SerializedName("our_mission")
    @Expose
    private String ourMission;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOurVision() {
        return ourVision;
    }

    public void setOurVision(String ourVision) {
        this.ourVision = ourVision;
    }

    public String getOurMission() {
        return ourMission;
    }

    public void setOurMission(String ourMission) {
        this.ourMission = ourMission;
    }

}
