package com.demos.productflavors.purchase;

public interface IabListener {
    void onServiceUnavailable();
    void onPurchaseFinished();
    void onPurchaseFailed();
}
