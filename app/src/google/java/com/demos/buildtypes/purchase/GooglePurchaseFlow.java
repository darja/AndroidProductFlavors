package com.demos.buildtypes.purchase;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService;
import com.demos.buildtypes.util.DPLog;

import java.util.UUID;

public class GooglePurchaseFlow extends PurchaseFlow {
    private static final String TAG = DPLog.createTag("GooglePurchase");

    private static final int REQUEST_CODE = 1001;

    // Connection to the service
    private IInAppBillingService mService;
    private ServiceConnection mServiceConn;

    public GooglePurchaseFlow(Activity activity, IabListener iabListener) {
        super(activity, iabListener);
    }

    @Override
    public void purchase(final String sku) {
        mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DPLog.dt(TAG, "Billing service connected");
                mService = IInAppBillingService.Stub.asInterface(service);

                try {
                    Bundle buyIntentBundle = mService.getBuyIntent(3, mActivity.getPackageName(), sku, "inapp", UUID.randomUUID().toString());
                    PendingIntent pi = buyIntentBundle.getParcelable("BUY_INTENT");

                    if (pi != null) {
                        mActivity.startIntentSenderForResult(pi.getIntentSender(), REQUEST_CODE, new Intent(), 0, 0, 0);
                    }

                } catch (RemoteException | IntentSender.SendIntentException e) {
                    DPLog.e(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                DPLog.dt(TAG, "Billing service disconnected");
                mService = null;
            }
        };

        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        if (!mActivity.getPackageManager().queryIntentServices(serviceIntent, 0).isEmpty()) {
            // service available to handle that Intent
            mActivity.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        } else {
            // no service available to handle that Intent
            if (mIabListener != null) {
                mIabListener.onServiceUnavailable();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");

            if (resultCode == Activity.RESULT_OK) {
                DPLog.d("Purchase finished: %s", purchaseData);
                if (mIabListener != null) {
                    mIabListener.onPurchaseFinished();
                }
            }

            mActivity.unbindService(mServiceConn);
        }
    }
}
