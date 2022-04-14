package com.vipheyue.simulateclick.send;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vipheyue.simulateclick.R;
import com.vipheyue.simulateclick.XiaoMiTelHelper;

public class HistoryActivity extends AppCompatActivity {

    private TextView tv_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        tv_msg = findViewById(R.id.tv_msg);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = XiaoMiTelHelper.historyMsgList.size()-1; i >=0; i--) {
            stringBuffer.append(XiaoMiTelHelper.historyMsgList.get(i)+"\n");
        }
        tv_msg.setText(stringBuffer.toString());

    }
    public void cleanMsg(View view) {
        XiaoMiTelHelper.historyMsgList.clear();
        tv_msg.setText("");
    }

}