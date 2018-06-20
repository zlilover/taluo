package com.fairytale.fortunetarot.util;

import android.content.Context;

/**
 * Created by lizhen on 2017/11/1.
 */

public class MyResource {

    public static int getIdByName(Context context, String className, String resName) {
        String packageName = context.getPackageName();
        int id = 0;
        try {
            Class r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;
            for (Class cls : classes) {
                if (cls.getName().split("\\$")[1].equals(className)) {
                    desireClass = cls;
                    break;
                }
            }
            if (desireClass != null) {
                id = desireClass.getField(resName).getInt(desireClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


    public static <T>  T getSourceByName(Context context,String className,String resName){
        String packageName = context.getPackageName();
        try {
            Class r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;
            for (Class cls : classes) {
                if (cls.getName().split("\\$")[1].equals(className)) {
                    desireClass = cls;
                    break;
                }
            }
            if (desireClass != null) {
                return (T)desireClass.getField(resName).get(desireClass.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}