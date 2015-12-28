package com.demos.buildtypes.purchase;

import android.app.Activity;

public class PurchaseFlowFactory {
    public static PurchaseFlow createPurchaseFlow(Activity activity, IabListener iabListener) {
        return new AmazonPurchaseFlow(activity, iabListener);
    }
}
