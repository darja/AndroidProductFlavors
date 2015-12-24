package com.demos.buildtypes.purchase;

import android.text.TextUtils;

@SuppressWarnings("unused")
public abstract class IabResult {
    protected int mCode;
    protected String mMessage;

    protected abstract String getResponseDescription(int code);
    protected abstract boolean isSuccess();

    public IabResult(int code) {
        this(code, null);
    }

    public IabResult(int code, String message) {
        mCode = code;
        if (TextUtils.isEmpty(message)) {
            mMessage = getResponseDescription(code);
        } else {
            mMessage = String.format("%s (response: %s)", message, getResponseDescription(code));
        }
    }

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isFailure() {
        return !isSuccess();
    }

    public String toString() {
        return String.format("InApp Billing Result: [%s] [%s]", mCode, mMessage);
    }
}
