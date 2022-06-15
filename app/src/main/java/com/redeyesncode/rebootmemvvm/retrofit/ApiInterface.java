package com.redeyesncode.rebootmemvvm.retrofit;


import com.redeyesncode.rebootmemvvm.base.CommonStatusMessageResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

// PLACE ALL YOUR API CALLS HERE.
public interface ApiInterface {
    @FormUrlEncoded
    @POST("is-available-username")
    Call<CommonStatusMessageResponse> isUserNameAvailable(@Header("Authorization") String accessToken, @Field("username") String userName);
}
