package com.samsung.android.sdk.iap.lib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.samsung.android.sdk.iap.lib.R;
import com.samsung.android.sdk.iap.lib.helper.SamsungIapHelper;

public class InboxActivity extends BaseActivity
{
    @SuppressWarnings("unused")
    private static final String TAG = InboxActivity.class.getSimpleName();
    
    private int     mInboxApiType    = SamsungIapHelper.INBOX_TYPE_ALL_ITEMS;
    
    // It is used when mOpenApiType is OPEN_API_TYPE_ITEM_GROUPS
    // ========================================================================
    private int     mStartNum       = 0;
    private int     mEndNum         = 0;
    private String  mStartDate      = "";
    private String  mEndDate        = "";
    // ========================================================================

    // It is used when mOpenApiType is OPEN_API_TYPE_ITEM_IDS
    // ========================================================================
    private String  mItemIds        = "";
    // ========================================================================
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        
        // 1. Save StartNum, EndNum, StartDate and EndDate passed by Intent
        // ====================================================================
        Intent intent = getIntent();
        
        if( intent != null && intent.getExtras() != null )
        {
            Bundle extras = intent.getExtras();
            
            mInboxApiType = extras.getInt( "OpenApiType", 
                                           SamsungIapHelper.INBOX_TYPE_NONE );
            
            if( mInboxApiType == SamsungIapHelper.INBOX_TYPE_SELECTED_ITEMS )
            {
                if( extras.containsKey( "ItemIds" ) )
                {
                    mItemIds = extras.getString( "ItemIds" );
                    mShowErrorDialog = extras.getBoolean( 
                                                     "ShowErrorDialog", true );
                }
                else
                {
                    showWrongParamMsgAndFinish();
                }
            }
            else if( mInboxApiType == SamsungIapHelper.INBOX_TYPE_ALL_ITEMS )
            {
                if( extras.containsKey( "StartNum" )    &&
                    extras.containsKey( "EndNum" )      &&
                    extras.containsKey( "StartDate" )   &&
                    extras.containsKey( "EndDate" ) )
                {
                    mStartNum        = extras.getInt( "StartNum" );        
                    mEndNum          = extras.getInt( "EndNum" );
                    mStartDate       = extras.getString( "StartDate" );         
                    mEndDate         = extras.getString( "EndDate" );
                    mShowErrorDialog = extras.getBoolean( 
                                                     "ShowErrorDialog", true );
                }
                else
                {
                    showWrongParamMsgAndFinish();
                }
            }
            else
            {
                showWrongParamMsgAndFinish();
            }
        }
        else
        {
            showWrongParamMsgAndFinish();
        }
        // ====================================================================
        
        // 2. If IAP package is installed and valid, Start SamsungAccount
        //    authentication step to load purchased item list.
        // ====================================================================
        if( true == checkIapPackage() )
        {
            mSamsungIapHelper.startAccountActivity( this );
        }
        // ====================================================================
    }
    
    private void showWrongParamMsgAndFinish()
    {
        Toast.makeText( this, 
                R.string.IDS_SAPPS_POP_AN_INVALID_VALUE_HAS_BEEN_PROVIDED_FOR_SAMSUNG_IN_APP_PURCHASE,
                Toast.LENGTH_LONG ).show();

        // Set error to return result to third-party application
        // --------------------------------------------------------------------
        mErrorVo.setError( SamsungIapHelper.IAP_ERROR_COMMON,
                           getString(R.string.IDS_SAPPS_POP_AN_INVALID_VALUE_HAS_BEEN_PROVIDED_FOR_SAMSUNG_IN_APP_PURCHASE) );
        // --------------------------------------------------------------------
        
        finish();
    }
    
    /**
     * handle result of SamsungAccount authentication.
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
            // Handle authentication result of SamsungAccount
            // ================================================================
            case SamsungIapHelper.REQUEST_CODE_IS_ACCOUNT_CERTIFICATION :
            {
                // 1) If SamsungAccount authentication is successful
                // ------------------------------------------------------------
                if( RESULT_OK == _resultCode )
                {
                    // bind to IAPService. 
                    // Once bound, invoke succeedBind()
                    // method to load purchased list.
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
                    
                    mSamsungIapHelper.showIapDialogIfNeeded( InboxActivity.this,
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
    
    /**
     * If binding to IAPService was successful, this method is invoked.
     * This Method loads the purchased list through IAPService.
     */
    protected void succeedBind()
    {
        // TODO IapMode를 전달할 수 없어서 IapMode를 변경하여도 처음 설정된 값이
        //      유지되는 문제가 있다. 수정이 필요하다.
        if( mInboxApiType == SamsungIapHelper.INBOX_TYPE_SELECTED_ITEMS )
        {
            mSamsungIapHelper.safeGetItemInboxTask( InboxActivity.this, 
                                                    mItemIds,
                                                    mShowErrorDialog );
        }
        else
        {
            mSamsungIapHelper.safeGetItemInboxTask( InboxActivity.this, 
                                                    mStartNum,
                                                    mEndNum,
                                                    mStartDate,
                                                    mEndDate,
                                                    mShowErrorDialog );
        }
    }
}