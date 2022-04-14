// Copyright 2016 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.vipheyue.simulateclick;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;

import android.os.*;

import com.vipheyue.simulateclick.send.SendDataIntentService;

public class GlobalActionBarService extends AccessibilityService {
    public static final String TAG = "无障碍";
    public static int currentPhoneRingState = TelephonyManager.CALL_STATE_IDLE;


    @Override
    protected void onServiceConnected() {
        Log.e(TAG, "onServiceConnected--->  无障碍 helper ok 15");
//        Toast.makeText(this, "safeEntry helper ok", Toast.LENGTH_SHORT).show();
        // 注册监听器
        TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        //设置监听
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void toggleNotificationListenerService(Context context) {
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(context, QHBNotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(context, QHBNotificationService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.e(TAG, "事件--->" + event);


        String pkn = String.valueOf(event.getPackageName());

//        Log.e(TAG, "事件--->" + pkn + " class: " + event.getClassName().toString());
        toggleNotificationListenerService(this); // 通知栏监听经常掉线强制打开


        if (pkn.equals("com.xiaomi.aiasst.service") && "androidx.recyclerview.widget.RecyclerView".equals(event.getClassName().toString())) {


//        if (pkn.equals("com.xiaomi.aiasst.service")) {
            AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
            if (nodeInfo == null) {
                Log.e(TAG, "rootWindow为空");
                return;
            }

            if (currentPhoneRingState == TelephonyManager.CALL_STATE_OFFHOOK) {
                XiaoMiTelHelper.findAllNode(nodeInfo, "0");
            }

        }

    }


    @Override
    public void onInterrupt() {
        Log.e(TAG, "     onInterrupt--->   safeEntry模拟点击中断  请到->设置->无障碍->已下载->TemperatureService 开启权限 ");

    }

    PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            currentPhoneRingState = state;

            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.e(GlobalActionBarService.TAG, "挂断电话");
                    Intent intent = new Intent(getApplicationContext(), SendDataIntentService.class);
                    startService(intent);

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.e(GlobalActionBarService.TAG, "通话中");

                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.e(GlobalActionBarService.TAG, "来电 响铃");
                    break;
            }
        }
    };

}
