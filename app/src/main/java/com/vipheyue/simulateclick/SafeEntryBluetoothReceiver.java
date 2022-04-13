package com.vipheyue.simulateclick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SafeEntryBluetoothReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "SafeEntryBluetoothReceiver", Toast.LENGTH_SHORT).show();
    }
}