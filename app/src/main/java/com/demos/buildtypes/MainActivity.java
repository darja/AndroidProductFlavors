package com.demos.buildtypes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
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

        mPurchaseFlow = PurchaseFlowFactory.createPurchaseFlow(this, mIabListener);
        mPurchaseFlow.onActivityCreate(this);

        TextView flavorTitleView = (TextView) findViewById(R.id.flavor_title);
        flavorTitleView.setText(Html.fromHtml(getString(R.string.app_flavor_title_format,
            getString(R.string.version_type), getString(R.string.inapp_type))));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mPurchaseFlow != null) {
            mPurchaseFlow.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void startPurchaseFlow(View view) {
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
            DPLog.e("Purchase failed");
            Toast.makeText(MainActivity.this, "Purchase failed", Toast.LENGTH_SHORT).show();
        }
    };
}
