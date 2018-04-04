package com.fairytale.fortunetarot.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fairytale.fortunetarot.comm.App;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 *  zli android系统相关工具
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class AndroidUtil {
	public static int camera_front = -1;
	public static int camera_back = -1;

	public static int[] getScreenParams(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return new int[]{metric.widthPixels,metric.heightPixels};
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo;
			packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo;
			packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static void showKeyStrod(View edit){
		InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(edit,0);
	}

	public static boolean isServiceRunning(Context context, String serviceName) {
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> lists = am.getRunningServices(200);
		for (RunningServiceInfo info : lists) {
			if (info.service.getClassName().equals(serviceName)) {
				isRunning = true;
			}
		}
		return isRunning;
	}

	// 进程是否运行
	public static boolean isProessRunning(Context context, String proessName) {
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
		if (lists != null) {
			for (RunningAppProcessInfo info : lists) {
				if (info.processName.equals(proessName)) {
					isRunning = true;
				}
			}
		}
		return isRunning;
	}

	// 应用是否在前台
	public static boolean isAppBackground(Context context) {
		boolean isInBackground = true;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
			List<RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
			for (RunningAppProcessInfo processInfo : runningProcesses) {
				//前台程序
				if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					for (String activeProcess : processInfo.pkgList) {
						if (activeProcess.equals(context.getPackageName())) {
							isInBackground = false;
						}
					}
				}
			}
		} else {
			List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
			ComponentName componentInfo = taskInfo.get(0).topActivity;
			if (componentInfo.getPackageName().equals(context.getPackageName())) {
				isInBackground = false;
			}
		}

		return isInBackground;
	}

	/**
	 * 判断是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}


	/**
	 * 
	 * 此方法描述的是： 检测摄像头是否可用
	 * 
	 * @author: zli
	 * @version: 2015-8-26 下午3:43:53
	 */
	@SuppressLint("NewApi")
	private static boolean checkCameraFacing(final int facing) {
		if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
			return false;
		}
		final int cameraCount = Camera.getNumberOfCameras();
		CameraInfo info = new CameraInfo();
		for (int i = 0; i < cameraCount; i++) {
			Camera.getCameraInfo(i, info);
			if (facing == info.facing) {
				if (facing == CameraInfo.CAMERA_FACING_BACK) {
					camera_back = i;
				} else {
					camera_front = i;
				}
				return true;
			}
		}
		return false;
	}

	@SuppressLint("NewApi")
	public static Camera openCamera(int i) {
		try {
			Camera c = Camera.open(i);
			if (c != null) {
				return c;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 此方法描述的是： 检测是否有后置摄像头
	 * 
	 * @author: zli
	 * @version: 2015-8-26 下午3:44:12
	 */
	public static boolean hasBackFacingCamera() {
		return checkCameraFacing(CameraInfo.CAMERA_FACING_BACK);
	}

	/**
	 * 
	 * 此方法描述的是： 检测是否有前置摄像头
	 * 
	 * @author: zli
	 * @version: 2015-8-26 下午3:44:28
	 */
	public static boolean hasFrontFacingCamera() {
		return checkCameraFacing(CameraInfo.CAMERA_FACING_FRONT);
	}

	/**
	 * 
	 * 此方法描述的是： 获取版本号
	 * 
	 * @author: zli
	 * @version: 2015-8-26 下午3:44:40
	 */
	public static int getSdkVersion() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 *
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPenGPS(final Context context) {
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
			return locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			return true;
		}

	}

	/*
	 * 检查是否安装了某应用
	 */
	public static boolean isAvilible(Context context, String packageName) {
		final PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pinfo.size(); i++) {
			if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
				return true;
		}
		return false;
	}



	public static String getProcessName(Context cxt, int pid) {
		ActivityManager am = (ActivityManager) cxt
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		for (RunningAppProcessInfo procInfo : runningApps) {
			if (procInfo.pid == pid) {
				return procInfo.processName;
			}
		}
		return null;
	}

	public static String getModuleAndSDK() {
		return Build.MODEL + "," + Build.VERSION.SDK_INT;
	}


	public static void closeKeyStrod(EditText edit){
		InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
	}

	public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	/**
	 * 获取屏幕宽高比
	 * @param context
	 * @return
     */
	public static float getScreenScale(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			Point size = new Point();
			wm.getDefaultDisplay().getSize(size);
			return ((float)size.x)/size.y;
		}else{
			return ((float)wm.getDefaultDisplay().getWidth())/wm.getDefaultDisplay().getHeight();
		}
	}

	public static int getStatusBarHeight(Context context){
		int result=0;
		int resourceId=context.getResources().getIdentifier("status_bar_height","dimen","android");
		if(resourceId>0){
			result=context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static Bitmap getBitmapFromStream(InputStream is, BitmapFactory.Options opts) {
		return BitmapFactory.decodeStream(is, null, opts);
	}
}
