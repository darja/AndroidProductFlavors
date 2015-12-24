package com.demos.buildtypes.purchase;

public interface IabListener {
    void onServiceUnavailable();
    void onPurchaseFinished();
    void onPurchaseFailed();
}
