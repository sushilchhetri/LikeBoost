package com.lynhillsoftwares.likeboost.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Sushil Chhetri on 10,May,2021
 */
public class SinglePost_detailPOJO implements Parcelable {
    public String id;
    public String media_type;
    public String media_url;
    public String username;
    public Date timestamp;

    public SinglePost_detailPOJO() {
    }


    protected SinglePost_detailPOJO(Parcel in) {
        id = in.readString();
        media_type = in.readString();
        media_url = in.readString();
        username = in.readString();
    }

    public static final Creator<SinglePost_detailPOJO> CREATOR = new Creator<SinglePost_detailPOJO>() {
        @Override
        public SinglePost_detailPOJO createFromParcel(Parcel in) {
            return new SinglePost_detailPOJO(in);
        }

        @Override
        public SinglePost_detailPOJO[] newArray(int size) {
            return new SinglePost_detailPOJO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(media_type);
        dest.writeString(media_url);
        dest.writeString(username);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
