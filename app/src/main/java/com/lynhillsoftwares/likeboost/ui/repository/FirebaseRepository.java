package com.lynhillsoftwares.likeboost.ui.repository;

/**
 * Created by Sushil Chhetri on 06,May,2021
 */
/*TODO singleton*/
public class FirebaseRepository {

    private static final String TAG = FirebaseRepository.class.getSimpleName();
    private static FirebaseRepository repoInstance;

    private FirebaseRepository(){

    }

    public static FirebaseRepository getRepoInstance() {
        if(repoInstance != null){
            repoInstance = new FirebaseRepository();
        }

        return repoInstance;
    }


    /*TODO get All post from firebase */
    public void get_allPost() {
    }
}
