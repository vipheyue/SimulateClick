package com.vipheyue.simulateclick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import static android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_POWER_DIALOG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openAccessibilityServiceSettings();


    }

    /** 打开辅助服务的设置*/
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);// 无障碍用这个
//            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);//通知权限用这个
            startActivity(intent);

            Toast.makeText(this, "点击->已下载的服务", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}