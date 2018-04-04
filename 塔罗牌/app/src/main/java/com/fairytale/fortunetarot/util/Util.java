package com.fairytale.fortunetarot.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.InputStream;

/**
 * Created by lizhen on 2018/1/24.
 */

public class Util {
    private static long currentTimeMillis = 0;

    public static String stringInsertWithTag(String source,int space,String tag) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }
        char[] latters = source.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < latters.length;i++) {
            if (((i+1)%space) == 0) {
                if (space == 1 && i == latters.length - 1) {
                    stringBuilder.append(String.valueOf(latters[i]));
                } else {
                    stringBuilder.append(String.valueOf(latters[i])).append(tag);
                }
            } else {
                stringBuilder.append(String.valueOf(latters[i]));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 判断当前月份是否闰月
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        }
        else if (year % 100 != 0 && year % 4 == 0){
            return true;
        }
        return  false;
    }

    /**
     * 此方法描述的是：   unicode转化为中文
     *
     * @author: zli
     * @version: 2015-8-26 下午2:59:08
     */
    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    /**
     * 屏蔽3s内连续点击
     * @return
     */
    public static boolean delayBtnClick(){
        if ((System.currentTimeMillis() - currentTimeMillis) < 3000) {
            return true;
        }
        currentTimeMillis = System.currentTimeMillis();
        return false;
    }

    public static String getStringfromAssets(Context context,String path) {
        String content = SPUtil.get(context, path,"").toString();
        if (TextUtils.isEmpty(content)) {
            try {
                InputStream is = context.getAssets().open(path);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String text = new String(buffer, "utf-8");
                SPUtil.put(context,path,text);
                return text;
            } catch (Exception e) {
                return null;
            }
        } else {
            return content;
        }
    }
}
