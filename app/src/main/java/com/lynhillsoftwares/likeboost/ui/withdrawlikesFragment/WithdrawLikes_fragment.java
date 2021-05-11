package com.lynhillsoftwares.likeboost.ui.withdrawlikesFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lynhillsoftwares.likeboost.databinding.FragmentWithdrawLikesBinding;
import com.lynhillsoftwares.likeboost.instagramlogin.pojo.SocialUser;
import com.lynhillsoftwares.likeboost.pojo.SinglePost_detailPOJO;
import com.lynhillsoftwares.likeboost.pojo.alljobpost.AllPost_POJO;
import com.lynhillsoftwares.likeboost.pojo.Post_pojo;
import com.lynhillsoftwares.likeboost.pojo.alljobpost.Cursors;
import com.lynhillsoftwares.likeboost.pojo.alljobpost.Datum;
import com.lynhillsoftwares.likeboost.pojo.alljobpost.Paging;
import com.lynhillsoftwares.likeboost.ui.withdrawlikesFragment.adapter.AllPostRV_Adpater;
import com.lynhillsoftwares.likeboost.utils.Constant;
import com.lynhillsoftwares.likeboost.utils.VolleySingleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;


public class WithdrawLikes_fragment extends Fragment {

    private static final String TAG = WithdrawLikes_fragment.class.getSimpleName();
    /*TODO View Binding*/
    private FragmentWithdrawLikesBinding vb;

    /*TODO demo code*/
    private static final int IMG_CODE = 101;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private Uri img_url = null;

    /*TODO Social User*/
    private SocialUser loginUser;

    /*TODO Adapter*/
    private AllPostRV_Adpater allPostRV_adpater;

    /*TODO volley request */
    private StringRequest fetchAllPost_Request, fetchSinglepostdetail_request;


    /*TODO instagram API Urls*/
    private String INSTA_URL = "https://graph.instagram.com/";


    public WithdrawLikes_fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginUser = Paper.book().read(Constant.LOGINUSER);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*TODO init view Binding */
        vb = FragmentWithdrawLikesBinding.inflate(inflater, container, false);

        initfirebase();
        initOnclickListener();
        initRecyclerview();
        return vb.getRoot();
    }

    /*TODO init recyclerview*/
    private void initRecyclerview() {
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        vb.imageRV.setLayoutManager(horizontalLayoutManagaer);

         allPostRV_adpater = new AllPostRV_Adpater(new ArrayList<>(),getContext());
        vb.imageRV.setAdapter(allPostRV_adpater);


    }

    @Override
    public void onResume() {
        super.onResume();
        allPostRV_adpater.clearArrayList();
        showLoadinglayout();
        fetchingAllPostID();
    }


    /*TODO onDestroy*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(fetchAllPost_Request != null){
            fetchAllPost_Request.cancel();
        }

        if(fetchSinglepostdetail_request != null){
            fetchSinglepostdetail_request.cancel();
        }
    }



    /*TODO loading animation show*/
    private void showLoadinglayout() {

        vb.rvLl.setVisibility(View.GONE);
        vb.loadinglayoutRL.setVisibility(View.VISIBLE);
        vb.loadingRipple.startRippleAnimation();

    }

    /*TODO hide loading animation*/
    private void hideLoadinglayout(){

        vb.rvLl.setVisibility(View.VISIBLE);
        vb.loadinglayoutRL.setVisibility(View.GONE);
        vb.loadingRipple.stopRippleAnimation();
    }

    /*TODO init onclick listener */
    private void initOnclickListener() {

        vb.uploadImgclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getimageFromGallery();
            }
        });

        vb.sendRequestid.setOnClickListener(v -> {
            sendRequstToFirebase();
        });


    }


    /*TODO Fetching all post from instagram Api*/
    private void fetchingAllPostID() {

        String instaAllPostID = INSTA_URL + loginUser.userId + "/media?fields=id,caption&access_token=" + loginUser.accessToken;

        fetchAllPost_Request = new StringRequest(Request.Method.GET,
                instaAllPostID,
                response -> {

                    Log.e(TAG, "fetchingAllPostID: " + response);

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        AllPost_POJO allPost_pojo = new AllPost_POJO();

                        /*TODO DATum Array*/
                        JSONArray datumJSONArray = jsonObject.getJSONArray("data");
                        ArrayList<Datum> datumArray = new ArrayList<>();
                        for (int i = 0; i < datumJSONArray.length(); i++) {

                            JSONObject jsonObject1 = (JSONObject) datumJSONArray.get(i);
                            Datum datum = new Datum();
                            datum.setId(jsonObject1.getString("id"));
//                            jsonObject1.has("caption");
                            if (jsonObject1.has("caption")) {

                                datum.setCaption(jsonObject1.getString("caption"));
                            }
                            datumArray.add(datum);
                        }

                        /*TODO */
                        JSONObject pagingObject = jsonObject.getJSONObject("paging").getJSONObject("cursors");
                        String before = pagingObject.getString("before");
                        String after = pagingObject.getString("after");
                        String next="";
                        if(jsonObject.getJSONObject("paging").has("next")){

                            next = jsonObject.getJSONObject("paging").getString("next");
                        }

                        Cursors cursors = new Cursors();
                        cursors.setAfter(after);
                        cursors.setBefore(before);

                        Paging paging = new Paging();
                        paging.setNext(next);
                        paging.setCursors(cursors);


                        allPost_pojo.setData(datumArray);
                        allPost_pojo.setPaging(paging);
                        Log.e(TAG, "fetchingAllPostID:######### " + allPost_pojo.getData());

                        /*TODO check if user have any post yet*/
                        if(datumArray.size()<=0){

                        }
                        else{
                            hideLoadinglayout();
                        }

                        /*TODO get Single post*/
                        for (int i = 0; i < datumArray.size(); i++) {

                            getSinglePost(datumArray.get(i));

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {

                    Log.e(TAG, "fetchingAllPostID: " + error);

                }
        );

        VolleySingleton.getInstance(getContext()).addToRequestQueue(fetchAllPost_Request);

    }


    /*TODO get Single post */
    private void getSinglePost(Datum datum) {

        String singlePostUrl = INSTA_URL + datum.getId() + "?fields=id,media_type,media_url,username,timestamp&access_token=" +
                loginUser.accessToken;

        fetchSinglepostdetail_request = new StringRequest(Request.Method.GET,
                singlePostUrl,
                response -> {
                    Log.e(TAG, "getSinglePost:456245 " + response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String id = jsonObject.getString("id");
                        String media_type = jsonObject.getString("media_type");
                        String media_url = jsonObject.getString("media_url");
                        String username = jsonObject.getString("username");
                        String timestamp = jsonObject.getString("timestamp");

                        SinglePost_detailPOJO singlePostPOjo = new SinglePost_detailPOJO();
                        singlePostPOjo.setId(id);
                        singlePostPOjo.setMedia_type(media_type);
                        singlePostPOjo.setMedia_url(media_url);
                        singlePostPOjo.setUsername(username);
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
                        Date result = df.parse(timestamp) ;
                        singlePostPOjo.setTimestamp(result);

                        /*TODO populate allPostRV_adpater rv*/
                        allPostRV_adpater.addSinglePostDetail(singlePostPOjo);

                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(fetchSinglepostdetail_request);

    }


    /*TODO send request to firebase DataBase All_post*/
    private void sendRequstToFirebase() {

        if (img_url != null && !TextUtils.isEmpty(vb.likecountEnter.getText())) {

            uploading_LoadingShow();
            uploadImageToFireBase(img_url);
        } else {
            Toast.makeText(getContext(), "Please fill the detail ", Toast.LENGTH_SHORT).show();
        }

    }


    /*TODO get Image From Gallery*/
    private void getimageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");

//        intent.putExtra("crop", "true");
//        intent.putExtra("scale", true);
//        intent.putExtra("outputX", 256);
//        intent.putExtra("outputY", 256);
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("return-data", true);
        startActivityForResult(intent, IMG_CODE);


    }


    /*TODO init firebase Object*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initfirebase() {

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        databaseReference = FirebaseDatabase.getInstance().getReference(Constant.ALL_POST);
    }


    /*TODO on Activity Result*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == IMG_CODE) {

            img_url = data.getData();

            vb.imageUploadingId.setImageURI(img_url);

            Log.e(TAG, "onActivityResult:image url  " + img_url);

        }
    }

    /*TODO uploading loading process show*/
    private void uploading_LoadingShow() {

        vb.sendRequestid.setText("Sending Request please Wait...");
        vb.uploadImgclick.setClickable(false);
        vb.uploadingProcesshide.setVisibility(View.VISIBLE);

    }

    /*TODO uploading loading process hide*/
    private void uploading_LoadingHide() {
        vb.sendRequestid.setText("Send Request");
        vb.uploadImgclick.setClickable(true);
        vb.uploadingProcesshide.setVisibility(View.GONE);
        vb.imageUploadingId.setImageURI(null);
        vb.likecountEnter.setText("");
    }


    /*TODO get single image firebase url  */
    private void uploadImageToFireBase(Uri imageuri) {

        Bitmap originalImage = null;

        try {
            originalImage = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageuri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        assert originalImage != null;
        originalImage.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] data = outputStream.toByteArray();
        Calendar calendar = Calendar.getInstance();
        /*TODO get timemilli for image unique name*/
        long timeMilli2 = calendar.getTimeInMillis();
        StorageReference reference = storageReference.child("post_image")
                .child("post_" + String.valueOf(timeMilli2) + ".jpg");

        reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override

                    public void onSuccess(Uri uri) {
                        Log.e(TAG, "onSuccess:imag uri " + uri);
                        saveToAllPost(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }
                })
                ;


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }
        });
    }

    /*TODO Save To All Post*/
    private void saveToAllPost(String imageurl) {

        Post_pojo post_pojo = new Post_pojo();
        post_pojo.setPost_by_email("fakeEmail@gmail.com");
        post_pojo.setTime_stamp(new Date().toString());
        post_pojo.setPost_img_url(imageurl);
        if (TextUtils.isEmpty(vb.likecountEnter.getText())) {

            post_pojo.setWant_like(5);

        } else {
            post_pojo.setWant_like(Integer.parseInt(vb.likecountEnter.getText().toString()));
        }

        Calendar calendar = Calendar.getInstance();
        /*TODO get timemilli for image unique name*/
        long timeMilli2 = calendar.getTimeInMillis();

        databaseReference.child("Post_" + String.valueOf(timeMilli2)).setValue(post_pojo).addOnCompleteListener(success -> {

            if (success.isSuccessful()) {
                uploading_LoadingHide();
                Toast.makeText(getContext(), "Successfully send request", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(error -> {
            uploading_LoadingHide();
            Toast.makeText(getContext(), "Failed to send request try again after some time", Toast.LENGTH_SHORT).show();
        });


    }
}