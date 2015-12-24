package com.samsung.android.sdk.iap.lib.listener;

import java.util.ArrayList;

import com.samsung.android.sdk.iap.lib.helper.SamsungIapHelper;
import com.samsung.android.sdk.iap.lib.vo.ErrorVo;
import com.samsung.android.sdk.iap.lib.vo.InboxVo;

/**
 * Callback Interface used with
 * {@link SamsungIapHelper.GetInboxListTask}
 */
public interface OnGetInboxListener
{
    /**
     * Callback method to be invoked 
     * when {@link SamsungIapHelper.GetInboxListTask} has been finished. 
     */
    void onGetItemInbox( ErrorVo _errorVO, ArrayList<InboxVo> _inboxList );
}
