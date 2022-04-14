package com.vipheyue.simulateclick.send;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.vipheyue.simulateclick.GlobalActionBarService;
import com.vipheyue.simulateclick.XiaoMiTelHelper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SendDataIntentService extends IntentService {


    public SendDataIntentService() {
        super("SendDataIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
//            Log.e(GlobalActionBarService.TAG, "发送数据到云端");


            XiaoMiTelHelper.historyMsgList.addAll(XiaoMiTelHelper.telMsgSet);

            // 跳到其他应用 打印数据
            if (XiaoMiTelHelper.telMsgSet.size() > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for (String msg : XiaoMiTelHelper.telMsgSet) {
                    stringBuffer.append(msg);
                    stringBuffer.append("%0a");
                }

                Log.e(GlobalActionBarService.TAG, stringBuffer.toString());

                try {
//                    run("http://s.welightworld.com:8090/AwYyXb2yjMuotbEztDGGaj/这里改88");
                    run("http://117.50.175.133:8090/AwYyXb2yjMuotbEztDGGaj/小米手机记录/"+stringBuffer.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static   OkHttpClient client = new OkHttpClient();

  public static   String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(GlobalActionBarService.TAG, " 网络请求失败");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.e(GlobalActionBarService.TAG, "发送完成 清空数据");
                String body = response.body().string();
                Log.e(GlobalActionBarService.TAG, "网络返回的数据： " + body);
                XiaoMiTelHelper.telMsgSet.clear();

            }
        });

        return "";
    }

}