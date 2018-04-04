package com.fairytale.fortunetarot.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fairytale.fortunetarot.R;

/**
 * Created by lizhen on 16/7/12. 弹出类型的时光选择器
 */
public class PopupTimePicker extends PopupWindow implements View.OnClickListener{
    private Context context;
    private TimePicker timePicker;
    private ClickCallBack callBack;
    private int model;
    private String year;
    private String month;
    private String day;

    /**
     * model 为0 设置到月份，1设置到天数 2设置到小时 3设置到分钟
     */
    public PopupTimePicker(Context context, int model) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_time_picker,null);
        setContentView(view);
        initView(view);
        this.model = model;
        config();
    }

    private void config() {
        setFocusable(true);
        setOutsideTouchable(true);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        setAnimationStyle(R.style.animation_bottom);
        timePicker.setModel(model);
    }



    private void initView(View view) {
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.btn_ok).setOnClickListener(this);
        timePicker = (TimePicker) view.findViewById(R.id.timePicker);
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)context).getWindow().setAttributes(lp);
    }

    /**
     *
     * @param width 宽度
     * @param position 在屏幕中的位置，Gravity.TOP,Gravity.CENTER,Gravity.BOTTOM
     */
    public void show(int width,int height,int position){
        if (!isShowing()) {
            backgroundAlpha(0.5f);
            setWidth(width);
            setHeight(height);
            showAtLocation(((Activity) context).getWindow().getDecorView(),position,0,0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                callBack.confirm(null);
                dismiss();
                break;
            case R.id.btn_ok:
                if (model == 0) {
                    if (timePicker.getCurrentYear().equals("年")) {
                        Toast.makeText(context, "请选择年份", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (timePicker.getCurrentMonth().equals("月")) {
                        Toast.makeText(context, "请选择月份", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if (model == 1 || model == 2 || model == 3) {
                    if (timePicker.getCurrentYear().equals("年")) {
                        Toast.makeText(context, "请选择年份", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (timePicker.getCurrentMonth().equals("月")) {
                        Toast.makeText(context, "请选择月份", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (timePicker.getCurrentDay().equals("日")) {
                        Toast.makeText(context, "请选择天数", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (callBack != null) {
                    day = timePicker.getCurrentDay();
                    month = timePicker.getCurrentMonth();
                    year = timePicker.getCurrentYear();
                    callBack.confirm(timePicker.getDate());
                    dismiss();
                }
                break;
        }
    }

    public void setCallBack(ClickCallBack callBack) {
        this.callBack = callBack;
    }

    public interface ClickCallBack {
        void confirm(String time);
    }

    public String getDay() {
        return day;
    }

    public String getDayWithNo0() {
        if (day.startsWith("0")) {
            return day.substring(1,day.length());
        }
        return  day;
    }

    public String getMonthWithNo0() {
        if (month.startsWith("0")) {
            return month.substring(1,month.length());
        }
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }
}
