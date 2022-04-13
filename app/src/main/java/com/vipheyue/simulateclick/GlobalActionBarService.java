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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;
import android.os.*;

public class GlobalActionBarService extends AccessibilityService {
    private static final String TAG = "无障碍";
    private static final String COVID_no_test_status = "No test status";
    private static final String COVID_cleared = "Cleared";
    private static final String COVID_not_cleared = "Not cleared";


    @Override
    protected void onServiceConnected() {
        servicema
        Log.e(TAG, "onServiceConnected--->  safeEntry helper ok");
//        Toast.makeText(this, "safeEntry helper ok", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.e(TAG, "事件--->" + event);


        String pkn = String.valueOf(event.getPackageName());
        if (pkn.equals("sg.gov.tech.safeentry")) {
//            Log.e(TAG, "事件--->" + event);
            AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
            if (nodeInfo == null) {
                Log.w(TAG, "rootWindow为空");
                return;
            }

            AccessibilityNodeInfo targetNode = AccessibilityHelper.findNodeInfosByText(nodeInfo, "Vaccination/COVID");

            if (targetNode == null) {
                Log.w(TAG, "当前safeentry 不是人员信息页面");
                return;
            } else {
                Log.e(TAG, "当前safeentry 是人员信息页面 遍历信息");

                /*
                *
 index : 0   text: Vaccination/COVID test result for
index : 1   text: S****007B
index : 2   text: Vaccination status
index : 3   text: Vaccinated
index : 4   text: COVID test result
index : 5   text: No test status
index : 6   text: Is visitor entering the venue?
index : 7   text: null
index : 8   text: null

                * */
                AccessibilityNodeInfo parent = targetNode.getParent();
                for (int i = 0; i < parent.getChildCount(); i++) {
                    AccessibilityNodeInfo childNode = parent.getChild(i);
                    Log.e(TAG, "index : " + i + "   text: " + childNode.getText());
                }


                String vaccinationStatusString = String.valueOf(parent.getChild(3).getText());
                String COVIDResultString = String.valueOf(parent.getChild(5).getText());


                boolean clickYes = false;

                switch (vaccinationStatusString) {
                    case "Vaccinated":
                        if (COVIDResultString.equals(COVID_no_test_status)) {
                            clickYes = true;
                        }
                        if (COVIDResultString.equals(COVID_cleared)) {
                            clickYes = true;
                        }
                        if (COVIDResultString.equals(COVID_not_cleared)) {
                            clickYes = false;
                        }
                        break;
                    case "Not vaccinated":
                        if (COVIDResultString.equals(COVID_no_test_status)) {
                            clickYes = false;
                        }
                        if (COVIDResultString.equals(COVID_cleared)) {
                            clickYes = true;
                        }
                        if (COVIDResultString.equals(COVID_not_cleared)) {
                            clickYes = false;
                        }
                        break;
                }
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                SafeEntryBluetoothReceiver.onceUseConditionsThenClear = String.valueOf(clickYes);
//                Log.e(TAG, " 更新 onceUseConditionsThenClear 的值为: "+SafeEntryBluetoothReceiver.onceUseConditionsThenClear);

                if (clickYes) {
                    AccessibilityHelper.performClick(parent.getChild(7));
                    Log.e(TAG, "模拟点击YES按钮");
                } else {
                    AccessibilityHelper.performClick(parent.getChild(8));
                    Log.e(TAG, "模拟点击NO按钮");
                }
            }
        }
    }


    @Override
    public void onInterrupt() {
        Log.e(TAG, "     onInterrupt--->   safeEntry模拟点击中断  请到->设置->无障碍->已下载->TemperatureService 开启权限 ");

    }


}
