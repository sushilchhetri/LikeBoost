package com.lynhillsoftwares.likeboost.ui.homeFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.FragmentHomeBinding;
import com.lynhillsoftwares.likeboost.instagramlogin.pojo.SocialUser;
import com.lynhillsoftwares.likeboost.utils.Constant;

import io.paperdb.Paper;

public class Home_fragment extends Fragment {


    private static final String TAG = Home_fragment.class.getSimpleName();
    /*TODO View Binding*/
    private FragmentHomeBinding vb;

    private SocialUser loginuser;


    public Home_fragment() {
        //TODO Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*TODO fetch login user from paper library*/
        loginuser = Paper.book().read(Constant.LOGINUSER);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        /*TODO init view Binding */
        vb = FragmentHomeBinding.inflate(inflater, container, false);
        if(loginuser != null){

            setValue();
        }

        return vb.getRoot();
    }

    /*TODO setValue */
    private void setValue() {

        Glide.with(this)
                .load(loginuser.profilePictureUrl)
                .fitCenter()
                .into(vb.userImgCI);

        vb.userNameTV.setText(loginuser.fullName);

    }

}