package com.lynhillsoftwares.likeboost.ui.withdrawlikesFragment.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lynhillsoftwares.likeboost.databinding.AllpostRvLayoutBinding;
import com.lynhillsoftwares.likeboost.pojo.SinglePost_detailPOJO;

import java.util.ArrayList;

/**
 * Created by Sushil Chhetri on 10,May,2021
 */
public class AllPostRV_Adpater extends RecyclerView.Adapter<AllPostRV_Adpater.AllPostViewHolder> {


    private ArrayList<SinglePost_detailPOJO> singlepostArrayList;
    private Context context;

    public AllPostRV_Adpater(ArrayList<SinglePost_detailPOJO> singlepostArrayList, Context context) {
        this.singlepostArrayList = singlepostArrayList;
        this.context = context;
    }

    /*TODO add single post to array*/
    public void addSinglePostDetail(SinglePost_detailPOJO singlePost_detailPOJO){
        Log.e("sushil", "addSinglePostDetail: " );
        singlepostArrayList.add(singlePost_detailPOJO);
        notifyDataSetChanged();
    }


    /*TODO Clear All Data*/
    public void clearArrayList(){
        singlepostArrayList.clear();
    }


    @NonNull
    @Override
    public AllPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllPostViewHolder(AllpostRvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllPostViewHolder holder, int position) {

        Glide.with(context).load(singlepostArrayList.get(position).getMedia_url()).into(holder.vb.postImgIV);

        /*TODO set onclick Listener*/
        holder.vb.postImgIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return singlepostArrayList.size();
    }

    static class AllPostViewHolder extends RecyclerView.ViewHolder {

        /*TODO view binding*/
        AllpostRvLayoutBinding vb;

        public AllPostViewHolder(AllpostRvLayoutBinding vb) {
            super(vb.getRoot());
            this.vb = vb;
        }
    }
}
