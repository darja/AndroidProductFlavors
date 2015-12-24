package com.demos.buildtypes.purchase;

public class GoogleIabResult extends IabResult {
    public GoogleIabResult(ResponseCode rc) {
        this(rc.code);
    }

    public GoogleIabResult(int code) {
        super(code, null);
    }

    @Override
    protected String getResponseDescription(int code) {
        for (ResponseCode rc : ResponseCode.values()) {
            if (rc.code == code) {
                return rc.message;
            }
        }

        switch (code) {
            case BILLING_RESPONSE_RESULT_OK: return "OK";
            case BILLING_RESPONSE_RESULT_USER_CANCELED : return "User canceled";
            case BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE: return "Billing Unavailable";
            case BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE: return "Item unavailable";
            case BILLING_RESPONSE_RESULT_DEVELOPER_ERROR: return "Developer Error";
            case BILLING_RESPONSE_RESULT_ERROR: return "Error";
            case BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED: return "Item Already Owned";
            case BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED: return "Item not owned";
            default: return "Unknown error";
        }
    }

    @Override
    protected boolean isSuccess() {
        return mCode == BILLING_RESPONSE_RESULT_OK;
    }

    public enum ResponseCode {
        REMOTE_EXCEPTION(-1001, "Remote exception during initialization"),
        BAD_RESPONSE(-1002, "Bad response"),
        VERIFICATION_FAILED(-1003, "Purchase signature verification failed"),
        SEND_INTENT_FAILED(-1004, "Send intent failed"),
        USER_CANCELLED(-1005, "User cancelled"),
        UNKNOWN_PURCHASE_RESPONSE(-1006, "Unknown purchase response"),
        MISSING_TOKEN(-1007, "Missing token"),
        UNKNOWN_ERROR(-1008, "Unknown error"),
        SUBSCRIPTIONS_NOT_AVAILABLE(-1009, "Subscriptions not available"),
        INVALID_CONSUMPTION(-1010, "Invalid consumption attempt");

        int code;
        String message;

        ResponseCode(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;
}
