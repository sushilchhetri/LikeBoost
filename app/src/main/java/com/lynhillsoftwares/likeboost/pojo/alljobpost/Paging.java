package com.lynhillsoftwares.likeboost.pojo.alljobpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Sushil Chhetri on 10,May,2021
 */
public class Paging {
    @SerializedName("cursors")
    @Expose
    Cursors cursors;
    @SerializedName("next")
    @Expose
    private String next;

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
