package com.redeyesncode.rebootmemvvm.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment {
    private ProgressDialog progressDialog;

    Context context;
    Activity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
        // Intialzing the common dialog box in onAttach

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        progressDialog = new ProgressDialog(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void hideLoader() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showLoader() {
        try {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(context);
            }
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showErrorLog(String messsage){
        Log.i("ERROR_ASHU",messsage);
    }

}
