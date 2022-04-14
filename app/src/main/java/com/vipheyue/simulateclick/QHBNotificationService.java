package com.vipheyue.simulateclick;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.vipheyue.simulateclick.send.SendDataIntentService;

/**
 * <p>Created 16/2/4 下午11:16.</p>
 * <p><a href="mailto:codeboy2013@gmail.com">Email:codeboy2013@gmail.com</a></p>
 * <p><a href="http://www.happycodeboy.com">LeonLee Blog</a></p>
 *
 * @author LeonLee
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class QHBNotificationService extends NotificationListenerService {

    private static final String TAG = GlobalActionBarService.TAG;

    private static QHBNotificationService service;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
//        Log.e(TAG, "收到通知栏消息 ");

        if ("com.tencent.mm".equals(sbn.getPackageName()) || "com.android.mms".equals(sbn.getPackageName())) {
            //通过以下方式可以获取Notification的详细信息
            Bundle extras = sbn.getNotification().extras;
            String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
            CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
//        CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
//        Log.e(TAG, "title:" + notificationTitle + " text: " + notificationText);

            if (!notificationTitle.contains("正在运行") && !notificationTitle.contains("灭屏中")) {
                XiaoMiTelHelper.telMsgSet.add(notificationTitle + " " + notificationText);
                Intent intent = new Intent(getApplicationContext(), SendDataIntentService.class);
                startService(intent);
            }
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onNotificationRemoved(sbn);
        }
        Log.e(TAG, "通知栏监听已经断开");

    }

    @Override
    public void onListenerConnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onListenerConnected();
        }
        Log.e(TAG, "通知栏监听已经开启");

    }


}
