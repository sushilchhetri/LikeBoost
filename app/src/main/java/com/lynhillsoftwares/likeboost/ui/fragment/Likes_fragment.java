package com.lynhillsoftwares.likeboost.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.FragmentLikesBinding;
import com.lynhillsoftwares.likeboost.databinding.FragmentSettingsBinding;


public class Likes_fragment extends Fragment {

    /*TODO View Binding*/
    private FragmentLikesBinding vb;

    public Likes_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*TODO init view Binding */
        vb = FragmentLikesBinding.inflate(inflater,container,false);


        return vb.getRoot();

    }
}