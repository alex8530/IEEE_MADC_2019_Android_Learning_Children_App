package com.arapeak.adkya.model.getSecondMaterial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SecondMaterialData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sections")
    @Expose
    private ArrayList<Section> sections = null;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
        this.sections = sections;
    }
}
