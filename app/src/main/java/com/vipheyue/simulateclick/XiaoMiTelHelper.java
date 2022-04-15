package com.vipheyue.simulateclick;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class XiaoMiTelHelper {
    private static final String TAG = "无障碍";

    static  String[] ignoreTitle = {"自动应答", "电话字幕", "手动应答", "自动应答模式", "通话模式"};

    public static LinkedHashSet<String> telMsgSet = new LinkedHashSet<>();
    public static ArrayList<String> historyMsgList = new ArrayList<String>();


    public static AccessibilityNodeInfo findAllNode(AccessibilityNodeInfo nodeInfo, String parentIndex) {

        if (nodeInfo == null) {
            return null;
        }
        for (int currentIndex = 0; currentIndex < nodeInfo.getChildCount(); currentIndex++) {
            AccessibilityNodeInfo node = nodeInfo.getChild(currentIndex);
//            Log.e(GlobalActionBarService.TAG, "parentIndex : " + parentIndex + "  currentIndex : " + currentIndex + "   text: " + node.getText() + " node:   " + node.toString());

            if (node.getText() != null) {
                String msg = String.valueOf(node.getText());

                if (!telMsgSet.contains(msg) && !Arrays.asList(ignoreTitle).contains(msg)) {
//                    telMsgSet.add(msg);
                    addString(msg);
                }

            }


//            if (!"null".equals(msg)) {
//                telMsgSet.add(msg);
//            }

            findAllNode(node, parentIndex + " " + currentIndex);
        }
        return null;
    }


    private static void addString(String needAddString) {
        XiaoMiTelHelper.telMsgSet.add(needAddString);
        removeRedundantString(needAddString);
    }

    private static void removeRedundantString(String needAddString) {
        if (needAddString.length() == 0) {
            return;
        }

        String lastString = needAddString.substring(0, needAddString.length() - 1);

        boolean contains = XiaoMiTelHelper.telMsgSet.contains(lastString);
        if (contains) {
            XiaoMiTelHelper.telMsgSet.remove(lastString);
        }
        removeRedundantString(lastString);
    }


}
