package com.lynhillsoftwares.likeboost.ui.likeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.lynhillsoftwares.likeboost.R;
import com.lynhillsoftwares.likeboost.databinding.FragmentLikesBinding;
import com.lynhillsoftwares.likeboost.pojo.Post_pojo;
import com.lynhillsoftwares.likeboost.utils.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Likes_fragment extends Fragment {

    private static final String TAG = Likes_fragment.class.getSimpleName();

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
        vb = FragmentLikesBinding.inflate(inflater, container, false);

        initOnClickListener();

        return vb.getRoot();

    }

    /*TODO init On click Listener*/
    private void initOnClickListener() {

        vb.sendLikeID.setOnClickListener(v -> {
            fetchAllpost();
        });

    }

    private void fetchAllpost() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constant.ALL_POST);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildren() == null) {
                    return;

                }
                Log.e(TAG, "onDataChange: " + snapshot);
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    autoLikePost(postSnapshot);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /*TODO Auto Like All Post image*/
    private void autoLikePost(DataSnapshot snapshot) {
        Log.e(TAG, "autoLikePost: %%%% " + snapshot.getKey());
        Post_pojo post_pojo = snapshot.getValue(Post_pojo.class);
        int likecount = post_pojo.getGotten_like();

        Log.e(TAG, "autoLikePost:@@@@@@@@@@@@ " + post_pojo);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constant.ALL_POST).child(Objects.requireNonNull(snapshot.getKey()));

        post_pojo.setGotten_like(likecount + 1);
        Map newData = new HashMap();
        newData.put("gotten_like", (likecount + 1));

        if(likecount != post_pojo.getWant_like()){

            databaseReference.updateChildren(newData);
        }



//        Log.e(TAG, "autoLikePost: ######### "+databaseReference );

//
//        databaseReference.runTransaction(new Transaction.Handler() {
//            @NonNull
//            @Override
//            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
//                Post_pojo p = currentData.getValue(Post_pojo.class);
//
//                Log.e(TAG, "doTransaction:datafile " + p+"   ----------   "+currentData);
//                return null;
//            }
//
//            @Override
//            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
//
//            }
//        });
    }


}