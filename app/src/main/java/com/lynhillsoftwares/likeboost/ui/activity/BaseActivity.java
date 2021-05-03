package com.lynhillsoftwares.likeboost.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * Created by Sushil Chhetri on 03,May,2021
 */
public abstract class BaseActivity extends AppCompatActivity {

    /*TODO calligraphy init*/
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    /*TODO show snackBar*/
    public void showSimplesnackBar(String errorString) {

        View parentLayout = findViewById(android.R.id.content);

        Snackbar.make(parentLayout, errorString, Snackbar.LENGTH_LONG).show();
    }


    /*TODO hide key Board*/
    public static void hideKeyBoard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*TODO vibrate */
    public static void vibrate(Context c) {
        Vibrator v = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

}
