package com.lynhillsoftwares.likeboost.ui.withdrawlikesFragment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lynhillsoftwares.likeboost.pojo.Post_pojo;
import com.lynhillsoftwares.likeboost.ui.repository.FirebaseRepository;

import java.util.List;

/**
 * Created by Sushil Chhetri on 06,May,2021
 */
public class Post_ViewModel extends AndroidViewModel {

    private FirebaseRepository firebaseRepository;
    public LiveData<List<Post_pojo>> mPostList;

    public Post_ViewModel(@NonNull Application application) {
        super(application);
        firebaseRepository = FirebaseRepository.getRepoInstance();
    }


    /*TODO getAll_Post*/
    public void get_allPost(){
        firebaseRepository.get_allPost();
    }
}
