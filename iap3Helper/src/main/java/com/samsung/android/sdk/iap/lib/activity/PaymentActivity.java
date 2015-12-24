package com.samsung.android.sdk.iap.lib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.SamsungIapHelper;
import com.samsung.android.sdk.iap.lib.listener.OnInitIapListener;
import com.samsung.android.sdk.iap.lib.vo.PurchaseVo;

public class PaymentActivity extends BaseActivity implements OnInitIapListener
{
    private static final String  TAG = PaymentActivity.class.getSimpleName();

    /** Item ID */
    private String  mItemId            = null;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        // 1. Save ItemId, ShowSuccessDialog passed by Intent
        // ====================================================================
        Intent intent = getIntent();
        
        if( intent != null && intent.getExtras() != null 
                && intent.getExtras().containsKey( "ItemId" ) )
        {
            Bundle extras = intent.getExtras();

            mItemId            = extras.getString( "ItemId" );
            mShowSuccessDialog = extras.getBoolean( "ShowSuccessDialog", true );
            mShowErrorDialog = extras.getBoolean( "ShowErrorDialog", true );
        }
        else
        {
            Toast.makeText( this, 
                            R.string.IDS_SAPPS_POP_AN_INVALID_VALUE_HAS_BEEN_PROVIDED_FOR_SAMSUNG_IN_APP_PURCHASE,
                            Toast.LENGTH_LONG ).show();
         
            // Set error to pass result to third-party application
            // ----------------------------------------------------------------
            mErrorVo.setError( SamsungIapHelper.IAP_ERROR_COMMON,
                               getString(R.string.IDS_SAPPS_POP_AN_INVALID_VALUE_HAS_BEEN_PROVIDED_FOR_SAMSUNG_IN_APP_PURCHASE) );
            // ----------------------------------------------------------------
            
            finish();
        }
        // ====================================================================

        //  2. Register {@link OnInitIapListener}
        // ====================================================================
        mSamsungIapHelper.setOnInitIapListener( this );
        // ====================================================================
        
        // 3. If IAP package is installed and valid, start SamsungAccount
        //    authentication activity to start purchase.
        // ====================================================================
        if( checkIapPackage() == true )
        {
            Log.i( TAG, "Samsung Account Login..." );
            mSamsungIapHelper.startAccountActivity( this );
        }
        // ====================================================================
    }
    
    /**
     * this method is invoked after IAPService is bound successfully.
     * This method initialize IAP. {@link PaymentMethodListActivity} in Main library is called.
     */
    protected void succeedBind()
    {
        if ( mSamsungIapHelper != null )
        {
            mSamsungIapHelper.safeInitIap( PaymentActivity.this, 
                                           mShowErrorDialog );
        }
    }

    /**
     * Handle SamsungAccount authentication result and purchase result.
     */
    @Override
    protected void onActivityResult
    (   
        int     _requestCode,
        int     _resultCode,
        Intent  _intent
    )
    {
        switch( _requestCode )
        {
            // 1. Handle result of purchase
            // ================================================================
            case SamsungIapHelper.REQUEST_CODE_IS_IAP_PAYMENT:
            {
                // 1) If payment is finished
                // ------------------------------------------------------------
                if( RESULT_OK == _resultCode )
                {
                    finishPurchase( _intent );
                }
                // ------------------------------------------------------------
                // 2) If payment is cancelled
                // ------------------------------------------------------------
                else if( RESULT_CANCELED == _resultCode )
                {
                    mErrorVo.setError( SamsungIapHelper.IAP_PAYMENT_IS_CANCELED,
                                       getString( R.string.IDS_SAPPS_POP_PAYMENT_CANCELLED ) );
                    
                    mSamsungIapHelper.showIapDialogIfNeeded( this,
                                                 getString( R.string.IDS_SAPPS_POP_SAMSUNG_IN_APP_PURCHASE ),
                                                 mErrorVo.getErrorString(),
                                                 true,
                                                 null,
                                                 mShowErrorDialog );
                    
                    break;
                }
                // ------------------------------------------------------------
                
                break;
            }
            // ================================================================
            
            // 2. Handle result of SamsungAccount authentication
            // ================================================================
            case SamsungIapHelper.REQUEST_CODE_IS_ACCOUNT_CERTIFICATION :
            {
                Log.i( TAG, "Samsung Account Result : " +  _resultCode );
                
                // 1) If SamsungAccount authentication is succeed 
                // ------------------------------------------------------------
                if( RESULT_OK == _resultCode )
                {
                    // bind to IAPService 
                    // --------------------------------------------------------
                    bindIapService();
                    // --------------------------------------------------------
                }
                // ------------------------------------------------------------
                // 2) If SamsungAccount authentication is cancelled
                // ------------------------------------------------------------
                else 
                {
                    mErrorVo.setError( SamsungIapHelper.IAP_PAYMENT_IS_CANCELED,
                                       getString( R.string.IDS_SAPPS_POP_PAYMENT_CANCELLED ) );
                    
                    mSamsungIapHelper.showIapDialogIfNeeded( this,
                                                 getString( R.string.IDS_SAPPS_POP_SAMSUNG_IN_APP_PURCHASE ),
                                                 getString( R.string.IDS_SAPPS_POP_PAYMENT_CANCELLED ),
                                                 true,
                                                 null,
                                                 mShowErrorDialog );
                }
                // ------------------------------------------------------------
                
                break;
            }
            // ================================================================
        }
    }

    @Override
    public void onSucceedInitIap()
    {
        // if initialization completed successfully,
        // call PurchaseMethodListActivity.
        // ====================================================================
        mSamsungIapHelper.startPaymentActivity( 
                                  PaymentActivity.this, 
                                  SamsungIapHelper.REQUEST_CODE_IS_IAP_PAYMENT, 
                                  mItemId );
        // ====================================================================
    }
    
    /**
     * Invoked when payment has been finished.
     * @param _intent
     */
    private void finishPurchase( Intent  _intent )
    {
        // 1. If there is bundle passed from IAP
        // ====================================================================
        if(  null != _intent && null != _intent.getExtras() )
        {
            Bundle extras = _intent.getExtras();
            
            mErrorVo.setError( extras.getInt( SamsungIapHelper.KEY_NAME_STATUS_CODE ), 
                               extras.getString( SamsungIapHelper.KEY_NAME_ERROR_STRING ) );
            
            // 1) If the purchase is successful,
            // ----------------------------------------------------------------
            if( mErrorVo.getErrorCode() == SamsungIapHelper.IAP_ERROR_NONE )
            {
                // a) Create PurcahseVo with data in Intent
                // ------------------------------------------------------------
                mPurchaseVo = new PurchaseVo( extras.getString(
                                   SamsungIapHelper.KEY_NAME_RESULT_OBJECT ) );
                // ------------------------------------------------------------
                
                // b) Validate the purchase
                // ------------------------------------------------------------
                mSamsungIapHelper.verifyPurchaseResult( PaymentActivity.this,
                                                        mPurchaseVo,
                                                        mShowSuccessDialog,
                                                        mShowErrorDialog );
                // ------------------------------------------------------------
            }
            // ----------------------------------------------------------------
            // 2) If the purchase is failed
            // ----------------------------------------------------------------
            else
            {
                mSamsungIapHelper.showIapDialogIfNeeded( this,
                                                 getString( R.string.IDS_SAPPS_POP_SAMSUNG_IN_APP_PURCHASE ),           
                                                 mErrorVo.getErrorString(),
                                                 true,
                                                 null,
                                                 mShowErrorDialog);
            }
            // ----------------------------------------------------------------
        }
        // ====================================================================
        // 2. If there is no bundle passed from IAP
        // ====================================================================
        else
        {
            mErrorVo.setError( SamsungIapHelper.IAP_ERROR_COMMON,
                  getString( R.string.IDS_SAPPS_POP_UNKNOWN_ERROR_OCCURRED ) );

            mSamsungIapHelper.showIapDialogIfNeeded( this,  
                                         getString( R.string.IDS_SAPPS_POP_SAMSUNG_IN_APP_PURCHASE ), 
                                         getString( R.string.IDS_SAPPS_POP_UNKNOWN_ERROR_OCCURRED )
                                             + "[Lib_Payment]",
                                         true,
                                         null,
                                         mShowErrorDialog );

            return;
        }
        // ====================================================================
    }
}