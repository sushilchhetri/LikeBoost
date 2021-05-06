package com.lynhillsoftwares.likeboost.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sushil Chhetri on 06,May,2021
 */
public class Post_pojo implements Parcelable {

    private String postid;
    private String post_img_url;
    private String post_by_Id;
    private String post_by_name;
    private String post_by_email;
    private String want_like;
    private String gotten_like;
    private String time_stamp;

    /*TODO default constructor */
    public Post_pojo() {
    }

    protected Post_pojo(Parcel in) {
        postid = in.readString();
        post_img_url = in.readString();
        post_by_Id = in.readString();
        post_by_name = in.readString();
        post_by_email = in.readString();
        want_like = in.readString();
        gotten_like = in.readString();
        time_stamp = in.readString();
    }

    public static final Creator<Post_pojo> CREATOR = new Creator<Post_pojo>() {
        @Override
        public Post_pojo createFromParcel(Parcel in) {
            return new Post_pojo(in);
        }

        @Override
        public Post_pojo[] newArray(int size) {
            return new Post_pojo[size];
        }
    };

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPost_img_url() {
        return post_img_url;
    }

    public void setPost_img_url(String post_img_url) {
        this.post_img_url = post_img_url;
    }

    public String getPost_by_Id() {
        return post_by_Id;
    }

    public void setPost_by_Id(String post_by_Id) {
        this.post_by_Id = post_by_Id;
    }

    public String getPost_by_name() {
        return post_by_name;
    }

    public void setPost_by_name(String post_by_name) {
        this.post_by_name = post_by_name;
    }

    public String getPost_by_email() {
        return post_by_email;
    }

    public void setPost_by_email(String post_by_email) {
        this.post_by_email = post_by_email;
    }

    public String getWant_like() {
        return want_like;
    }

    public void setWant_like(String want_like) {
        this.want_like = want_like;
    }

    public String getGotten_like() {
        return gotten_like;
    }

    public void setGotten_like(String gotten_like) {
        this.gotten_like = gotten_like;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postid);
        dest.writeString(post_img_url);
        dest.writeString(post_by_Id);
        dest.writeString(post_by_name);
        dest.writeString(post_by_email);
        dest.writeString(want_like);
        dest.writeString(gotten_like);
        dest.writeString(time_stamp);
    }
}
