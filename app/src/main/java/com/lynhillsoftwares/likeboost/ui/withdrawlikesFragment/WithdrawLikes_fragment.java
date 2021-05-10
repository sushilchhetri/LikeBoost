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

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lynhillsoftwares.likeboost.databinding.FragmentWithdrawLikesBinding;
import com.lynhillsoftwares.likeboost.pojo.Post_pojo;
import com.lynhillsoftwares.likeboost.utils.Constant;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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
    private Uri img_url=null;


    public WithdrawLikes_fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*TODO init view Binding */
        vb = FragmentWithdrawLikesBinding.inflate(inflater,container,false);
        initfirebase();
        initOnclickListener();
        return vb.getRoot();
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


    /*TODO send request to firebase DataBase All_post*/
    private void sendRequstToFirebase() {

        if(img_url != null && !TextUtils.isEmpty(vb.likecountEnter.getText())){

            uploading_LoadingShow();
            uploadImageToFireBase(img_url);
        }
        else{
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
        if(requestCode == IMG_CODE){

           img_url = data.getData();

            vb.imageUploadingId.setImageURI(img_url);

            Log.e(TAG, "onActivityResult:image url  "+img_url );

        }
    }

    /*TODO uploading loading process*/
    private void uploading_LoadingShow() {

        vb.sendRequestid.setText("Sending Request please Wait...");
        vb.uploadImgclick.setClickable(false);
        vb.uploadingProcesshide.setVisibility(View.VISIBLE);

    }

    private void uploading_LoadingHide(){
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
                        Log.e(TAG, "onSuccess:imag uri "+uri );
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
        if(TextUtils.isEmpty(vb.likecountEnter.getText())){

            post_pojo.setWant_like(5);

        }
        else{
            post_pojo.setWant_like(Integer.parseInt(vb.likecountEnter.getText().toString()));
        }

        Calendar calendar = Calendar.getInstance();
        /*TODO get timemilli for image unique name*/
        long timeMilli2 = calendar.getTimeInMillis();

        databaseReference.child("Post_"+String.valueOf(timeMilli2)).setValue(post_pojo).addOnCompleteListener(success->{

            if(success.isSuccessful()){
                uploading_LoadingHide();
                Toast.makeText(getContext(), "Successfully send request", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(error->{
            uploading_LoadingHide();
            Toast.makeText(getContext(), "Failed to send request try again after some time", Toast.LENGTH_SHORT).show();
        });


    }
}