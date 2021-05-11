package com.lynhillsoftwares.likeboost.pojo.alljobpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil Chhetri on 10,May,2021
 */
public class Cursors {
    @SerializedName("before")
    @Expose
    private String before;
    @SerializedName("after")
    @Expose
    private String after;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

}
