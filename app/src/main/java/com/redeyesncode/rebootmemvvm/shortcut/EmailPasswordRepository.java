package com.redeyesncode.rebootmemvvm.shortcut;

import android.content.Context;

import com.redeyesncode.rebootmemvvm.base.CommonStatusMessageResponse;
import com.redeyesncode.rebootmemvvm.retrofit.ApiInterface;
import com.redeyesncode.rebootmemvvm.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailPasswordRepository {
    private static EmailPasswordRepository profileRepository;
    private final ApiInterface apiInterface;


    public static EmailPasswordRepository getInstance(){
        if (profileRepository==null){
            profileRepository = new EmailPasswordRepository();

        }
        return profileRepository;
    }
    public EmailPasswordRepository(){
        apiInterface = RetrofitService.createService(ApiInterface.class);

    }

    public void isUserNameAvailable(String accessToken, String userName, CommonListener commonListener){
        Call<CommonStatusMessageResponse> call = apiInterface.isUserNameAvailable(accessToken, userName);
        call.enqueue(new Callback<CommonStatusMessageResponse>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.code() == 200 ) {
                        commonListener.onCommonSuccessResponse((CommonStatusMessageResponse) response.body());
                    } else {
                        commonListener.onCommonError("Error");
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                commonListener.onCommonError("Error");
            }
        });
    }
}
