package com.fairytale.fortunetarot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.fairytale.fortunetarot.comm.Config;
import com.fairytale.fortunetarot.controller.HistoryActivity;
import com.fairytale.fortunetarot.controller.MainActivity;
import com.fairytale.fortunetarot.controller.WebDetailActivity;
import com.fairytale.fortunetarot.entity.PushEntity;
import com.fairytale.fortunetarot.util.JsonUtils;
import com.fairytale.fortunetarot.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
//            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        /*if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知" + bundle.getString(JPushInterface.EXTRA_EXTRA));
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else */

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String content = bundle.getString(JPushInterface.EXTRA_EXTRA);
                if (TextUtils.isEmpty(content)) {
                    return;
                }

                PushEntity entity = JsonUtils.jsonStringToEntity(content,PushEntity.class);
                processCustomMessage(context, entity);
            }

        } else {
            Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, PushEntity entity) {
        if (entity != null) {
            Intent intent = null;
            switch (entity.getPushtype()) {
                case 1:     //  每日一牌，跳转到主页“每日”tab页
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,0);
                    break;
                case 2:     //  看看今日历史上发生了什么，跳转到历史上的今天列表页。
                    intent = new Intent(context, HistoryActivity.class);
                    break;
                case 3:     //  可以跳转历史上今天具体的某一个事件详细页面
                    intent = new Intent(context, WebDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("contentId", entity.getContentid());
                    bundle.putString("contentType", 2 + "");
                    intent.putExtras(bundle);
                    break;
                case 4:     //  查看最新的塔罗资讯，跳转到主页“资讯”tab页
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,1);
                    break;
                case 5:     //  例如：查看1月10日的塔罗运势。可以跳转到具体的一个文章页面

                    break;
                case 6:     //  一起来玩玩塔罗占卜，可以跳转到“占卜”tab页
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,2);
                    break;
                case 7:     //  可以跳转到具体某一个牌阵开始占卜的介绍页

                    break;
                case 8:     //  一起来学习牌义，跳转到“牌义”tab页
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,3);
                    break;
                case 9:     //  一起来学习牌阵，跳转到“牌阵”tab页
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Config.NOTIFICATION_CLICKED_TO_MAIN,4);
                    break;
                case 10:    //  今日做活动，价格优惠。跳转到主页，读取服务端config参数之后，然后弹出购买框。（没有购买的用户会提示此消息，购买过了不会提示此消息）
                    intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Config.NOTIFICATION_CLICKED_TO_SHOW_BUY,4);
                    break;
                case 11:    //  新版本发布啦，提示。读取服务端config参数之后，弹出新版本对话框。

                    break;
            }
            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }
}
