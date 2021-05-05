package com.lynhillsoftwares.likeboost.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.FragmentHomeBinding;
import com.lynhillsoftwares.likeboost.databinding.FragmentSettingsBinding;


public class Settings_fragment extends Fragment {

    /*TODO View Binding*/
    private FragmentSettingsBinding vb;

    public Settings_fragment() {
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
        vb = FragmentSettingsBinding.inflate(inflater,container,false);


        return vb.getRoot();
    }
}