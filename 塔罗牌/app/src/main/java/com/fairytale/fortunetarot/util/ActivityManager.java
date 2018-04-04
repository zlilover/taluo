package com.fairytale.fortunetarot.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

public class ActivityManager {
	private static Stack<Activity> activityStack;
	private static ActivityManager instance;

	private ActivityManager() {

	}

	/**
	 * 单一实例
	 */
	public static ActivityManager getAppManager() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 移除指定的Activity
	 */
	public void removeActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		if (activityStack == null) {
			return;
		}
		Iterator iterator = activityStack.iterator();
		while (iterator.hasNext()) {
			Activity activity = (Activity) iterator.next();
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
				return;
			}
		}
	}

	/**
	 * 清楚所有activity
	 */
	public void finishAllActivity() {
		if (activityStack != null) {
			while (activityStack.size() > 0) {
				finishActivity();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 * 
	 *            isBackground 是否开启启后台运行?
	 */
	public void AppExit( Boolean isBackground) {
		try {
			finishAllActivity();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!isBackground) {
				System.exit(0);
			}
		}
	}
}
