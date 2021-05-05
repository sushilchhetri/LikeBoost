package com.lynhillsoftwares.likeboost.ui.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.ActivityHomeBinding;
import com.lynhillsoftwares.likeboost.ui.fragment.Home_fragment;
import com.lynhillsoftwares.likeboost.ui.fragment.Likes_fragment;
import com.lynhillsoftwares.likeboost.ui.fragment.Settings_fragment;
import com.lynhillsoftwares.likeboost.ui.fragment.WithdrawLikes_fragment;

public class HomeActivity extends BaseActivity {

    /*TODO View binding*/
    private ActivityHomeBinding vb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*TODO init view Binding*/
        vb = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = vb.getRoot();
        setContentView(view);

        initBottomNavigation();
    }

    /*TODO init Bottom Navigation*/
    private void initBottomNavigation() {

        vb.bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        /*TODO Default Fragment*/
        transact(new Home_fragment());
    }


    /*TODO init navigation item selected listener*/
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.home:
                        transact(new Home_fragment());
                        return true;
                    case R.id.likes:
                        transact(new Likes_fragment());
                        return true;
                    case R.id.withdrawlike:
                        transact(new WithdrawLikes_fragment());
                        return true;
                    case R.id.setting:

                        transact(new Settings_fragment());
                        return true;

                }
                return false;
            };



    /*TODO transact Fragment*/
    private void transact(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(vb.fragmentcontainer.getId(), fragment);
        ft.commit();
    }

}