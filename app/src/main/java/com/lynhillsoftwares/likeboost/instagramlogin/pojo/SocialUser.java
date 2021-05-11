package com.lynhillsoftwares.likeboost.instagramlogin.pojo;

/**
 * Created by Sushil Chhetri on 07,May,2021
 */

import android.os.Parcel;
import android.os.Parcelable;

public class SocialUser implements Parcelable {

    public String userId;
    public String accessToken;
    public String profilePictureUrl;
    public String username;
    public String fullName;
    public String email;
    public String pageLink;
    public String tokenExpires_in;

    public SocialUser() {
    }

    public SocialUser(SocialUser other) {
        this.userId = other.userId;
        this.accessToken = other.accessToken;
        this.profilePictureUrl = other.profilePictureUrl;
        this.username = other.username;
        this.fullName = other.fullName;
        this.email = other.email;
        this.pageLink = other.pageLink;
    }

    protected SocialUser(Parcel in) {
        userId = in.readString();
        accessToken = in.readString();
        profilePictureUrl = in.readString();
        username = in.readString();
        fullName = in.readString();
        email = in.readString();
        pageLink = in.readString();
        tokenExpires_in = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(accessToken);
        dest.writeString(profilePictureUrl);
        dest.writeString(username);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(pageLink);
        dest.writeString(tokenExpires_in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SocialUser> CREATOR = new Creator<SocialUser>() {
        @Override
        public SocialUser createFromParcel(Parcel in) {
            return new SocialUser(in);
        }

        @Override
        public SocialUser[] newArray(int size) {
            return new SocialUser[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

     SocialUser that = (SocialUser) o;

        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SocialUser {").append("\n\n");
        sb.append("userId=").append(userId).append("\n\n");
        sb.append("username=").append(username).append("\n\n");
        sb.append("fullName=").append(fullName).append("\n\n");
        sb.append("email=").append(email).append("\n\n");
        sb.append("profilePictureUrl=").append(profilePictureUrl).append("\n\n");
        sb.append("pageLink=").append(pageLink).append("\n\n");
        sb.append("accessToken=").append(accessToken).append("\n\n");
        sb.append("expires_in=").append(tokenExpires_in).append("\n\n");
        sb.append('}');
        return sb.toString();
    }


}