package com.lynhillsoftwares.likeboost.pojo.alljobpost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lynhillsoftwares.likeboost.pojo.alljobpost.Datum;
import com.lynhillsoftwares.likeboost.pojo.alljobpost.Paging;

import java.util.List;

/**
 * Created by Sushil Chhetri on 10,May,2021
 */
public class AllPost_POJO {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("paging")
    @Expose
    private Paging paging;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}



