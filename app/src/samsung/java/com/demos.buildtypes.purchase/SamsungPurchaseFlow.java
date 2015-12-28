package com.demos.buildtypes.purchase;

import android.app.Activity;
import com.demos.buildtypes.util.DPLog;
import com.samsung.android.sdk.iap.lib.helper.SamsungIapHelper;
import com.samsung.android.sdk.iap.lib.listener.OnPaymentListener;
import com.samsung.android.sdk.iap.lib.vo.ErrorVo;
import com.samsung.android.sdk.iap.lib.vo.PurchaseVo;

public class SamsungPurchaseFlow extends PurchaseFlow {
    public SamsungPurchaseFlow(Activity activity, IabListener iabListener) {
        super(activity, iabListener);
    }

    @Override
    public void purchase(String sku) {
        SamsungIapHelper iapHelper = SamsungIapHelper.getInstance(mActivity, SamsungIapHelper.IAP_MODE_TEST_SUCCESS);
        iapHelper.startPayment(sku, true, mOnPaymentListener);
    }

    private OnPaymentListener mOnPaymentListener = new OnPaymentListener()
    {
        @Override
        public void onPayment(ErrorVo errorVo, PurchaseVo purchaseVo)
        {
            StringBuilder sb = new StringBuilder();

            if (errorVo != null) {
                sb.append(errorVo.dump());
                sb.append("\n\n");
            }

            if (purchaseVo != null) {
                sb.append(purchaseVo.dump());
            }

            DPLog.e("Purchase result [%s]", sb.toString());

            if (errorVo != null && mIabListener != null) {
                if (errorVo.getErrorCode() == SamsungIapHelper.IAP_ERROR_NONE) {
                    mIabListener.onPurchaseFinished();
                } else {
                    mIabListener.onPurchaseFailed();
                }
            }
        }
    };
}
