package com.lynhillsoftwares.likeboost.instagramlogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.lynhillsoftwares.likeboost.databinding.ActivityInstaAuthBinding;
import com.lynhillsoftwares.likeboost.instagramlogin.pojo.SocialUser;
import com.lynhillsoftwares.likeboost.utils.Constant;
import com.lynhillsoftwares.likeboost.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class instaAuthActivity extends SimpleAuthActivity {

    /*TODO view Binding*/
    private ActivityInstaAuthBinding vb;

    private static final String TAG = instaAuthActivity.class.getSimpleName();

    private StringRequest instloginReQest, userDeailRequest, UserOtherDetailRequest;
    private StringRequest longLiveAccessTokenRequest;

    /*TODO instagram Auth  Url*/
    private static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/?client_id=%1$s&redirect_uri=%2$s&response_type=code&scope=%3$s";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    private static final String PAGE_LINK = "https://www.instagram.com/%1$s/";
    private static final String INSTALINK_USER = "https://graph.instagram.com/";
    private static final String INSTA_USER_DETAILURL = "https://www.instagram.com/";
    private static final String LONG_LIVE_TOKEN_URL = "https://graph.instagram.com/access_token";
//    https://www.instagram.com/lynhill/?__a=1 For Feting instagram User details

    /*TODO instagram Variable*/
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private ProgressDialog loadingDialog;


    public static void start(Context context) {
        Intent intent = new Intent(context, instaAuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityInstaAuthBinding.inflate(getLayoutInflater());
        clientId = Constant.INSTA_APP_ID;
        clientSecret = Constant.INSTA_SECRET;
        redirectUrl = Constant.INSTA_REDIRECT_URL;

        loadingDialog = DialogFactory.createLoadingDialog(this);

        Log.e(TAG, "onCreate: " + getAuthData().getScopes());
        String scopes = TextUtils.join("+", getAuthData().getScopes());

        String url = String.format(AUTH_URL, clientId, redirectUrl, scopes);
        Log.e(TAG, "onCreate:_________  " + url);

        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.dismiss();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(redirectUrl)) {
                    Log.e(TAG, "shouldOverrideUrlLoading: " + url);
                    getCode(Uri.parse(url));
                    return true;
                }
                return false;
            }
        });


        setContentView(webView);
    }

    @Override
    protected AuthData getAuthData() {
        return AuthDataHolder.getInstance().instagramAuthData;
    }


    /*TODO get Code from uri*/
    private void getCode(Uri uri) {
        String code = uri.getQueryParameter("code");
        if (code != null) {
//            getAccessToken(code);
            requestvolley(code);
        } else if (uri.getQueryParameter("error") != null) {
            String errorMsg = uri.getQueryParameter("error_description");
            handleError(new Throwable(errorMsg));
        }
    }


    /*TODO volley call for data*/
    private void requestvolley(String code) {

        instloginReQest = new StringRequest(com.android.volley.Request.Method.POST
                , TOKEN_URL,
                response -> {
                    Log.e(TAG, "requestvolley:response " + response);


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        final SocialUser user = new SocialUser();
                        user.accessToken = jsonObject.getString("access_token");
                        user.userId = jsonObject.getString("user_id");

                        /*TODO generate long live access token  */
                        getLongLiveToken(user);


                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                },
                error -> {

                    responseError(error);

                    if (error instanceof NetworkError) {
                        showToast("Network Error please check internet connection");
                    } else if (error instanceof ServerError) {
                        //handle if server error occurs with 5** status code
                        showToast("Server side error");
                    } else if (error instanceof AuthFailureError) {
                        //handle if authFailure occurs.This is generally because of invalid credentials
                        showToast("please check your credentials ");
                    } else if (error instanceof ParseError) {
                        //handle if the volley is unable to parse the response data.
                        showToast(" Unable to parse the response data ");
                    } else if (error instanceof NoConnectionError) {
                        //handle if no connection is occurred
                        showToast(" No Connection to server ");
                    } else if (error instanceof TimeoutError) {
                        showToast("Time out error Please restart the app  ");
                        //handle if socket time out is occurred.
                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> login_token = new HashMap<>();
                login_token.put("client_id", clientId);
                login_token.put("client_secret", clientSecret);
                login_token.put("grant_type", "authorization_code");
                login_token.put("redirect_uri", redirectUrl);
                login_token.put("code", code);


                return login_token;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(instloginReQest);
    }

    /*TODO generate long live access token  */
    private void getLongLiveToken(SocialUser user) {


   /*     "https://graph.instagram.com/access_token
                ?grant_type=ig_exchange_token
                &client_secret={instagram-app-secret}
                &access_token={short-lived-access-token}"*/

        String longlive_accessLink = LONG_LIVE_TOKEN_URL
                + "?grant_type=ig_exchange_token&client_secret=" + Constant.INSTA_SECRET
                + " &access_token=" + user.accessToken;

        longLiveAccessTokenRequest = new StringRequest(Request.Method.GET,
                longlive_accessLink,
                response -> {
                    Log.e(TAG, "getLongLiveToken: " + response);

                    try {
                        JSONObject getLongLiveTokenobj = new JSONObject(response);

                        user.accessToken = getLongLiveTokenobj.getString("access_token");
                        user.tokenExpires_in = getLongLiveTokenobj.getString("expires_in");


                        /*TODO Get User Detail from instagram*/
                        getUserDetail(user);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {

                });

        VolleySingleton.getInstance(this).addToRequestQueue(longLiveAccessTokenRequest);
    }


    /*TODO Volley Call for User details*/
    private void getUserDetail(SocialUser user) {

        String userDetailURL = INSTALINK_USER + user.userId + "?fields=id,username&access_token=" + user.accessToken;

        userDeailRequest = new StringRequest(com.android.volley.Request.Method.GET
                , userDetailURL,
                response -> {

                    Log.e(TAG, "getUserDetail: response---> " + response);
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        user.username = jsonObject.getString("username");


                        /*TODO fetch user profile pic and detail using this api https://www.instagram.com/{Username}/?__a=1*/
                        fetchOtherDetail(user);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {
                    Log.e(TAG, "getUserDetail:error-> " + error.getMessage());
                    responseError(error);


                    if (error instanceof NetworkError) {
                        showToast("Network Error please check internet connection");
                    } else if (error instanceof ServerError) {
                        //handle if server error occurs with 5** status code
                        showToast("Server side error");
                    } else if (error instanceof AuthFailureError) {
                        //handle if authFailure occurs.This is generally because of invalid credentials
                        showToast("please check your credentials ");
                    } else if (error instanceof ParseError) {
                        //handle if the volley is unable to parse the response data.
                        showToast(" Unable to parse the response data ");
                    } else if (error instanceof NoConnectionError) {
                        //handle if no connection is occurred
                        showToast(" No Connection to server ");
                    } else if (error instanceof TimeoutError) {
                        showToast("Time out error Please restart the app  ");
                        //handle if socket time out is occurred.
                    }

                });

        Log.e(TAG, "getUserDetail:url detail->  " + userDeailRequest.getUrl());
        VolleySingleton.getInstance(this).addToRequestQueue(userDeailRequest);
    }

    /*TODO fetch user profile pic and detail using this api https://www.instagram.com/{Username}/?__a=1*/
    private void fetchOtherDetail(SocialUser user) {
        String URLWithUsername = INSTA_USER_DETAILURL + user.username + "/?__a=1";
        UserOtherDetailRequest = new StringRequest(Request.Method.GET,
                URLWithUsername,
                response -> {

                    Log.e(TAG, "fetchOtherDetail:^^^^^^^^^^^ " + response);


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject USER_OBJECT = jsonObject.getJSONObject("graphql").getJSONObject("user");

                        user.fullName = USER_OBJECT.getString("full_name");
                        user.profilePictureUrl = USER_OBJECT.getString("profile_pic_url");

                        /*TODO Completed the User Data Fetch*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.dismiss();
                                handleSuccess(user);
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {
                    Log.e(TAG, "getUserDetail:error-> " + error.getMessage());
                    responseError(error);


                    if (error instanceof NetworkError) {
                        showToast("Network Error please check internet connection");
                    } else if (error instanceof ServerError) {
                        //handle if server error occurs with 5** status code
                        showToast("Server side error");
                    } else if (error instanceof AuthFailureError) {
                        //handle if authFailure occurs.This is generally because of invalid credentials
                        showToast("please check your credentials ");
                    } else if (error instanceof ParseError) {
                        //handle if the volley is unable to parse the response data.
                        showToast(" Unable to parse the response data ");
                    } else if (error instanceof NoConnectionError) {
                        //handle if no connection is occurred
                        showToast(" No Connection to server ");
                    } else if (error instanceof TimeoutError) {
                        showToast("Time out error Please restart the app  ");
                        //handle if socket time out is occurred.
                    }

                });

        VolleySingleton.getInstance(this).addToRequestQueue(UserOtherDetailRequest);
    }


    /*TODO response error */
    private void responseError(VolleyError e) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                handleError(e);
            }
        });
    }


    /*TODO Show Toast*/
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /*TODO User detail */
    private static class IgUser {
        @SerializedName("access_token")
        String accessToken;
        @SerializedName("user")
        IgUser.User user;

        static class User {
            @SerializedName("id")
            String id;
            @SerializedName("username")
            String username;
            @SerializedName("full_name")
            String fullName;
            @SerializedName("profile_picture")
            String profilePicture;
        }
    }


    /*TODO onback press*/

    @Override
    public void onBackPressed() {
        handCancel();
    }

}