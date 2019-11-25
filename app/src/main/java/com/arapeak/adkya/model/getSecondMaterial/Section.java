package com.arapeak.adkya.model.getSecondMaterial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }


    @SerializedName("is_complete")
    @Expose
    public Boolean is_complete ;

    public Boolean getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(Boolean is_complete) {
        this.is_complete = is_complete;
    }

    private Integer parent_position;

    public Integer getParent_position() {
        return parent_position;
    }

    public void setParent_position(Integer parent_position) {
        this.parent_position = parent_position;
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

    public Object getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
