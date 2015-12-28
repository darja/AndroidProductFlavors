package com.demos.buildtypes.purchase;

import android.app.Activity;
import android.content.Context;
import com.amazon.device.iap.PurchasingListener;
import com.amazon.device.iap.PurchasingService;
import com.amazon.device.iap.model.ProductDataResponse;
import com.amazon.device.iap.model.PurchaseResponse;
import com.amazon.device.iap.model.PurchaseUpdatesResponse;
import com.amazon.device.iap.model.UserDataResponse;
import com.demos.buildtypes.util.DPLog;

public class AmazonPurchaseFlow extends PurchaseFlow implements PurchasingListener {
    public AmazonPurchaseFlow(Activity activity, IabListener iabListener) {
        super(activity, iabListener);
    }

    @Override
    public void purchase(String sku) {
        PurchasingService.purchase(sku);
    }

    @Override
    public void onActivityCreate(Context context) {
        PurchasingService.registerListener(context, this);
    }

    @Override
    public void onUserDataResponse(UserDataResponse r) {
        if (r == null) {
            DPLog.w("Empty user data response");
        } else {
            DPLog.d("User data response: %s", r.toString());
        }
    }

    @Override
    public void onProductDataResponse(ProductDataResponse r) {
        if (r == null) {
            DPLog.w("Empty product data response");
        } else {
            DPLog.d("Product data response: %s", r.toString());
        }
    }

    @Override
    public void onPurchaseResponse(PurchaseResponse r) {
        if (r == null) {
            DPLog.w("Empty purchase response");
            return;
        } else {
            DPLog.d("Purchase response: %s", r.toString());
        }

        if (mIabListener != null) {
            if (r.getRequestStatus() == PurchaseResponse.RequestStatus.SUCCESSFUL) {
                mIabListener.onPurchaseFinished();
            } else {
                mIabListener.onPurchaseFailed();
            }
        }
    }

    @Override
    public void onPurchaseUpdatesResponse(PurchaseUpdatesResponse purchaseUpdatesResponse) {

    }
}
