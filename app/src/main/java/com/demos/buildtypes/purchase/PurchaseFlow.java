package com.demos.buildtypes.purchase;

import android.app.Activity;
import android.content.Intent;

public abstract class PurchaseFlow {
    protected Activity mActivity;
    protected IabListener mIabListener;

    public PurchaseFlow(Activity activity, IabListener iabListener) {
        mActivity = activity;
        mIabListener = iabListener;
    }

    public abstract void purchase(String sku);
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
