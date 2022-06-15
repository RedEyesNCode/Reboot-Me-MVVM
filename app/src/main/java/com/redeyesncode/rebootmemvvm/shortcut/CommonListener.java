package com.redeyesncode.rebootmemvvm.shortcut;

import com.redeyesncode.rebootmemvvm.base.CommonStatusMessageResponse;

public interface CommonListener {
    void onCommonSuccessResponse(CommonStatusMessageResponse commonStatusMessageResponse);
    void onCommonError(String error);

}
