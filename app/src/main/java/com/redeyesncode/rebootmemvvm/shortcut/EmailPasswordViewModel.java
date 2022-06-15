package com.redeyesncode.rebootmemvvm.shortcut;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.redeyesncode.rebootmemvvm.base.CommonStatusMessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailPasswordViewModel extends ViewModel {

    private MutableLiveData<EmailNumberFormState> emailNumberFormState = new MutableLiveData<>();
    public MutableLiveData<CommonStatusMessageResponse> loginResult = new MutableLiveData<>();
    // private API_CALL_REPOSITORY_NAME = new API_CALL_REPOSITORY_NAME();

    private final MutableLiveData<String> isFailed = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isConnecting = new MutableLiveData<>();
    public LiveData<String> getIsFailed() {
        return isFailed;
    }
    public LiveData<Boolean> getIsConnecting() {
        return isConnecting;
    }


    // Now , lets observe the Email-Password feilds livedata.
    public LiveData<EmailNumberFormState> getFormState(){
        return emailNumberFormState;
    }
    public LiveData<CommonStatusMessageResponse> getApiCallResponse(){
        return loginResult;
    }

    // Create 2 methods one for calling the api from the repository
    // Another for validation of the forms this methods gets called in the view.

    public void loginFormValidation(String email,String mobileNumber){
        // TODO :: THIS WILL NOTIFY THE OBSERVER LIKE CRAZY
        if (email.isEmpty() || email.length()>6){
            if(email.isEmpty()){
                emailNumberFormState.setValue(new EmailNumberFormState("Email is Empty !",null));
            }else{
                emailNumberFormState.setValue(new EmailNumberFormState("Email is too Long !",null));
            }

        }else if(mobileNumber.isEmpty() || mobileNumber.length()<=11){
            if(email.isEmpty()){
                emailNumberFormState.setValue(new EmailNumberFormState(null,"Please enter password"));
            }else{
                emailNumberFormState.setValue(new EmailNumberFormState(null,"Mobile number length should be at-least 11"));
            }

        }else {
            emailNumberFormState.setValue(new EmailNumberFormState(true));
        }
    }

    // Second method for the api call.
    public void callApi(String userName, String mobileNumber){
        // You can create this instance intially also.
        EmailPasswordRepository emailPasswordRepository = new EmailPasswordRepository();
        emailPasswordRepository.isUserNameAvailable(userName,mobileNumber,listener);

    }
    CommonListener listener = new CommonListener() {
        @Override
        public void onCommonSuccessResponse(CommonStatusMessageResponse commonStatusMessageResponse) {
            loginResult.postValue(commonStatusMessageResponse);

        }

        @Override
        public void onCommonError(String error) {
            isFailed.setValue(error);
            // If you are posting null value please apply a check in the view for it in the observer whereever you are setting the data.
            loginResult.postValue(null);
        }
    };





}
