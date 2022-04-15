package com.vipheyue.simulateclick;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        String s1 = "非";
        String s2 = "非常";
        String s3 = "非常的";
        String s4 = "非常的好";
        String s5 = "非常的好看";
//        XiaoMiTelHelper.telMsgSet.add(s1);

        addString(s1);
        addString(s2);
        addString(s3);
        addString(s4);
        addString(s5);
//        XiaoMiTelHelper.telMsgSet.add(s2);
//        XiaoMiTelHelper.telMsgSet.add(s3);
//        XiaoMiTelHelper.telMsgSet.add(s4);
//        XiaoMiTelHelper.telMsgSet.add(s5);
////        boolean contains2 = XiaoMiTelHelper.telMsgSet.contains(s1);
//
//        boolean contains = s1.contains(s2);
//        boolean contains1 = s2.contains(s1);
//
//        for (int i = XiaoMiTelHelper.telMsgSet.size() - 1; i >= 0; i--) {
//            for (int j = 0; j < i; j++) {
//                XiaoMiTelHelper.telMsgSet.
//            }
//        }
        assertEquals(4, 2 + 2);
    }

    private void addString(String needAddString) {
        XiaoMiTelHelper.telMsgSet.add(needAddString);
        removeRedundantString(needAddString);
    }
    private void removeRedundantString(String needAddString) {
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