package com.lynhillsoftwares.likeboost.pojo.alljobpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil Chhetri on 10,May,2021
 */
public class Datum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("caption")
    @Expose
    private String caption;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
