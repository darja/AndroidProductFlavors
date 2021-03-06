package com.demos.productflavors.purchase;

import android.app.Activity;

public class PurchaseFlowFactory {
    public static PurchaseFlow createPurchaseFlow(Activity activity, IabListener iabListener) {
        return new GooglePurchaseFlow(activity, iabListener);
    }
}
