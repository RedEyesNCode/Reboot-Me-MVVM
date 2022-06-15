package com.redeyesncode.rebootmemvvm.shortcut;

import androidx.annotation.Nullable;

public class EmailNumberFormState {

    @Nullable
    private String usernameError;
    @Nullable
    private String passwordError;
    private boolean isDataValid;

    // Here we are making two constructors of the application. // If checking for one field set the other fields to be null.
    EmailNumberFormState(@Nullable String usernameError, @Nullable String passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    // This is set to true when the validations are successfull

    // Set the button to be only enabled in the view when the isDataValid Turns to be true.
    EmailNumberFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public String getUsernameError() {
        return usernameError;
    }

    @Nullable
    public String getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
