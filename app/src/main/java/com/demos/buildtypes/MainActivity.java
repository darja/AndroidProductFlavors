package com.demos.buildtypes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.demos.buildtypes.purchase.IabListener;
import com.demos.buildtypes.purchase.PurchaseFlow;
import com.demos.buildtypes.purchase.PurchaseFlowFactory;
import com.demos.buildtypes.util.DPLog;

public class MainActivity extends AppCompatActivity {
    private PurchaseFlow mPurchaseFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mPurchaseFlow != null) {
            mPurchaseFlow.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startPurchaseFlow(View view) {
        mPurchaseFlow = PurchaseFlowFactory.createPurchaseFlow(this, mIabListener);
        mPurchaseFlow.purchase(getString(R.string.sku));
    }


    private IabListener mIabListener = new IabListener() {
        @Override
        public void onServiceUnavailable() {
            DPLog.w("InApp billing service unavailable");
        }

        @Override
        public void onPurchaseFinished() {
            DPLog.d("Purchase finished");
            Toast.makeText(MainActivity.this, "Purchase finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPurchaseFailed() {

        }
    };
}
