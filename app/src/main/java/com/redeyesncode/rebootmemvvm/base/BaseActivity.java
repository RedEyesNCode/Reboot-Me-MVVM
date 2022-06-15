package com.redeyesncode.rebootmemvvm.base;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    protected void onPause() {
        super.onPause();
        // REMOVED THE ANDROID~WINDOW LEAKED ERROR
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // REMOVED THE ANDROID~WINDOW LEAKED ERROR
    }
    //Implementing other common methods here.

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }





}
