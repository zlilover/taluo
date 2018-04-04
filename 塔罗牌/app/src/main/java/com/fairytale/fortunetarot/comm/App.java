package com.fairytale.fortunetarot.comm;

import android.app.Application;
import android.text.TextUtils;

import com.fairytale.fortunetarot.http.request.BaseConfigRequest;
import com.fairytale.fortunetarot.util.Logger;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lizhen on 2018/1/9.
 */

public class App extends Application {
    private static App instance;

    public static App getInstance () {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.setIsDebug(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        instance = this;
        initParams();
//        test(testNum);
    }

    private void initParams() {
        new BaseConfigRequest().getBaseConfig(this);
    }

//    String test(String params) {
//        if (params.contains(".")) {
//            String[] ss = params.split("\\.");
//            String integerpart = ss[0];
//            String floatPart = ss[1];
//            String intText = "";
//            String floatText = "";
//            int integerLength = integerpart.length();
//            int floatLenth = floatPart.length();
//            for (int i = 0; i< integerLength; i++) {
//                intText += alaboToChina(Integer.parseInt(integerpart.substring(i,i + 1)),integerLength - (i + 1));
//            }
//            for (int i = 0; i < floatLenth ;i++) {
//                floatText += alaboToChina(Integer.parseInt(floatPart.substring(i,i+1)), -(i + 1));
//            }
//            Logger.e("text big","--------->" + intText + "圆" + floatText);
//        }
//        return null;
//    }
//
//    String alaboToChina(int num,int tenCount) {
//        String single = "";
//        String ten = "";
//        switch (num) {
//            case 1:
//                single =  "壹";
//                break;
//            case 2:
//                single = "贰";
//                break;
//            case 3:
//                single = "叁";
//                break;
//            case 4:
//                single = "肆";
//                break;
//            case 5:
//                single = "伍";
//                break;
//            case 6:
//                single = "陆";
//                break;
//            case 7:
//                single = "柒";
//                break;
//            case 8:
//                single = "捌";
//                break;
//            case 9:
//                single = "玖";
//        }
//        switch (tenCount) {
//            case -2:
//                ten = "分";
//                break;
//            case -1:
//                ten = "角";
//                break;
//            case 1:
//                ten = "拾";
//                break;
//            case 2:
//                ten = "佰";
//                break;
//            case 3:
//                ten = "仟";
//                break;
//            case 4:
//                ten = "万";
//        }
//        if (!TextUtils.isEmpty(single)) {
//            return single + ten;
//        } else {
//            return  "";
//        }
//    }
}
