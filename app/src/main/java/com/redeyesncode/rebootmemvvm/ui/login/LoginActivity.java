package com.redeyesncode.rebootmemvvm.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.redeyesncode.rebootmemvvm.R;
import com.redeyesncode.rebootmemvvm.base.BaseActivity;
import com.redeyesncode.rebootmemvvm.base.CommonStatusMessageResponse;
import com.redeyesncode.rebootmemvvm.shortcut.EmailNumberFormState;
import com.redeyesncode.rebootmemvvm.shortcut.EmailPasswordViewModel;
import com.redeyesncode.rebootmemvvm.ui.login.LoginViewModel;
import com.redeyesncode.rebootmemvvm.ui.login.LoginViewModelFactory;
import com.redeyesncode.rebootmemvvm.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;
    private EmailPasswordViewModel emailPasswordViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        emailPasswordViewModel = new ViewModelProvider(this).get(EmailPasswordViewModel.class);
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;


        // ADDING THE VALIDATION ON TEXT CHANGES ON BOTH THE FIELDS

        binding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                // NOTIFTY THE VIEW MODEL ABOUT THE CHANGE AND IT WILL HANDLE THE VALIDATION ITSELF.
                // TODO :: THIS WILL NOTIFY THE OBSERVER LIKE CRAZY
                // TODO :: THIS WILL NOTIFY THE OBSERVER LIKE CRAZY  JUST SEE THE POWER OF MVVM - VALIDATION !! ;)

                emailPasswordViewModel.loginFormValidation(binding.edtName.getText().toString(),binding.edtNumber.getText().toString());
            }
        });

        // OBSERVING THE VALIDATION RESPONSE >> THIS WILL HELP TO UPDATE  AND VALIDATE SO SO FAST !!
        emailPasswordViewModel.getFormState().observe(this, new Observer<EmailNumberFormState>() {
            @Override
            public void onChanged(EmailNumberFormState emailNumberFormState) {
                if (emailNumberFormState == null) {
                    return;
                }
                if(emailNumberFormState.isDataValid()){
                    // Call the api for enable the button now
                }
                if(emailNumberFormState.getPasswordError()!=null){
                    binding.edtName.setText(emailNumberFormState.getPasswordError());
                    showToast(emailNumberFormState.getPasswordError());
                }
                if(emailNumberFormState.getUsernameError()!=null){
                    binding.edtName.setText(emailNumberFormState.getUsernameError());
                    showToast(emailNumberFormState.getUsernameError());
                }
            }
        });

        // OBSERVER FOR THE API CALL
        emailPasswordViewModel.getApiCallResponse().observe(this, new Observer<CommonStatusMessageResponse>() {
            @Override
            public void onChanged(CommonStatusMessageResponse commonStatusMessageResponse) {

            }
        });




        // Here the view is observing the form data or the form feilds in the ui declated in the view model.
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                // We only enable the button once the data is valid from the observer
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}