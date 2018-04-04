package com.fairytale.fortunetarot.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fairytale.fortunetarot.R;
import com.fairytale.fortunetarot.util.DateUtil;
import com.fairytale.fortunetarot.util.Util;
import com.fairytale.fortunetarot.widget.wheel.OnWheelChangedListener;
import com.fairytale.fortunetarot.widget.wheel.WheelView;
import com.fairytale.fortunetarot.widget.wheel.adapters.ArrayWheelAdapter;

/**
 * Created by lizhen on 16/5/9. 日历选择器
 */
public class TimePicker extends LinearLayout {
    private WheelView year;
    private WheelView month;
    private WheelView day;
    private WheelView hour;
    private WheelView minute;
    private String[] yearList = new String[22];//由于每个时间数组里面加了年，月，日，所有数组长度＋1
    private String[] monthList = new String[13];
    private String[] dayList31 = new String[32];
    private String[] dayList30 = new String[31];
    private String[] dayList29 = new String[30];
    private String[] dayList28 = new String[29];
    private String[] hourList = new String[24];
    private String[] minuteList = new String[12];
    private String currentYear = "";
    private String currentMonth = "";
    private String currentDay = "";
    private String currentHour = "";
    private String currentMinute = "";
    private ArrayWheelAdapter yearAdapter;
    private ArrayWheelAdapter monthAdapter;
    private ArrayWheelAdapter dayAdapter;
    private ArrayWheelAdapter hourAdapter;
    private ArrayWheelAdapter minuteAdapter;
    private Context context;
    private int model;
    private View line;
    private View view1;
    private View view2;
    private View view3;

    public TimePicker(Context context) {
        super(context);

    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TimePicker);
        model = array.getInteger(R.styleable.TimePicker_model,1);
        array.recycle();
        initView(context);
        initData(context);

    }

    /**
     * 0 设置到月份，1设置到天数 2设置到小时 3设置到分钟 4只能选年和月
     * @param model
     */
    public void setModel(int model) {
        this.model = model;
        changeModel();
    }


    public TimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_time_picker,this);
        year = (WheelView) view.findViewById(R.id.year);
        month = (WheelView) view.findViewById(R.id.month);
        day = (WheelView) view.findViewById(R.id.day);
        hour = (WheelView) view.findViewById(R.id.hour);
        minute = (WheelView) view.findViewById(R.id.minute);
        line = view.findViewById(R.id.view);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
//        Toast.makeText(context, "可以通过滑动选择时间", Toast.LENGTH_LONG).show();
    }

    private void initData(Context context) {
        //年份数据,默认只显示前后10年
        int currentyear = Integer.parseInt(DateUtil.GetNowDate().substring(0,4));
        String displayMonth = DateUtil.GetNowDate().substring(5,7);
        String displayDay = DateUtil.GetNowDate().substring(8,10);

        currentYear = currentyear + "";
        int start = currentyear - 10;
        for (int i = 0;i <= 21;i++) {
            if (i == 0) {
                yearList[i] = "年";
            }
            else{
                yearList[i] = (start + i) + "";
            }
        }
        year.setCyclic(false);
        yearAdapter = new ArrayWheelAdapter<>(context,yearList);
        yearAdapter.setTextSize(20);
        yearAdapter.setTextPadding(0,40,0,40);
        year.setViewAdapter(yearAdapter);
        year.setCurrentItem(10);
        year.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    currentYear = yearList[wheel.getCurrentItem()];
                    month.setCurrentItem(0);
                    day.setCurrentItem(0);
                }
            }
        });

        //月份数据
        if (model == 4) {
            monthList = new String[12];
            for (int i = 1;i <= 12;i++) {
                if (i < 10) {
                    monthList[i-1] = "0" + i;
                }
                else {
                    monthList[i-1] = i + "";
                }
            }
        }
        else {
            for (int i = 0;i <= 12;i++) {
                if (i == 0) {
                    monthList[i] = "月";
                }
                else {
                    if (i < 10) {
                        monthList[i] = "0" + i;
                    }
                    else {
                        monthList[i] = i + "";
                    }
                }
            }
        }
        month.setCyclic(false);
        monthAdapter = new ArrayWheelAdapter<>(context,monthList);
        monthAdapter.setTextSize(20);
        monthAdapter.setTextPadding(0,40,0,40);
        month.setViewAdapter(monthAdapter);
        int monthIndex = 0;
        for (int i = 0; i < monthList.length;i++) {
            if (monthList[i].equals(displayMonth)) {
                monthIndex = i;
                currentMonth = displayMonth;
            }
        }

        month.setCurrentItem(monthIndex);
        month.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    currentMonth = monthList[wheel.getCurrentItem()];
                    changeDayAdapter();
                    day.setCurrentItem(0);
                }
            }
        });

        //天数默认为31天

        if (model == 4) {
            dayList28 = new String[28];
            dayList29 = new String[29];
            dayList30 = new String[30];
            dayList31 = new String[31];
            for (int i = 1;i <= 31;i++) {
                if (i < 10) {
                    dayList28[i-1] = "0" + i;
                    dayList29[i-1] = "0" + i;
                    dayList30[i-1] = "0" + i;
                    dayList31[i-1] = "0" + i;
                }
                else {
                    if (i <= 28) {
                        dayList28[i-1] = i + "";
                        dayList29[i-1] = i + "";
                        dayList30[i-1] = i + "";
                    }
                    else if (i <= 29) {
                        dayList29[i-1] = i + "";
                        dayList30[i-1] = i + "";
                    }
                    else if (i <= 30) {
                        dayList30[i-1] = i + "";
                    }
                    dayList31[i-1] = i + "";
                }
            }
        } else {
            for (int i = 0;i <= 31;i++) {
                if (i == 0) {
                    dayList28[i] = "日";
                    dayList29[i] = "日";
                    dayList30[i] = "日";
                    dayList31[i] = "日";
                }
                else {
                    if (i < 10) {
                        dayList28[i] = "0" + i;
                        dayList29[i] = "0" + i;
                        dayList30[i] = "0" + i;
                        dayList31[i] = "0" + i;
                    }
                    else {
                        if (i <= 29) {
                            dayList28[i-1] = (i - 1) + "";
                            dayList29[i] = i + "";
                            dayList30[i] = i + "";
                        }
                        else if (i <= 30) {
                            dayList29[i-1] = (i - 1) + "";
                            dayList30[i] = i + "";
                        }
                        else if (i <= 31) {
                            dayList30[i-1] = (i - 1) + "";
                        }
                        dayList31[i] = i + "";
                    }
                }
            }
        }

        day.setCyclic(false);
        dayAdapter = new ArrayWheelAdapter<>(context,dayList31);
        changeDayAdapter();
        dayAdapter.setTextSize(20);
        dayAdapter.setTextPadding(0,40,0,40);
        day.setViewAdapter(dayAdapter);

        int dayIndex = 0;
        for (int i = 0; i < dayAdapter.getItemsCount();i++) {
            if (dayAdapter.getItemText(i).equals(displayDay)) {
                dayIndex = i;
                currentDay = displayDay;
            }
        }

        day.setCurrentItem(dayIndex);
//        day.setEnabled(false);
        day.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    if (model == 4) {
                        if (wheel.getViewAdapter().getItemsCount() == 28) {
                            currentDay = dayList28[wheel.getCurrentItem()];
                        }
                        else if(wheel.getViewAdapter().getItemsCount() == 29) {
                            currentDay = dayList29[wheel.getCurrentItem()];
                        }
                        else if(wheel.getViewAdapter().getItemsCount() == 30) {
                            currentDay = dayList30[wheel.getCurrentItem()];
                        }
                        else if(wheel.getViewAdapter().getItemsCount() == 31) {
                            currentDay = dayList31[wheel.getCurrentItem()];
                        }
                    } else {
                        if (wheel.getViewAdapter().getItemsCount() == 29) {
                            currentDay = dayList28[wheel.getCurrentItem()];
                        }
                        else if(wheel.getViewAdapter().getItemsCount() == 30) {
                            currentDay = dayList29[wheel.getCurrentItem()];
                        }
                        else if(wheel.getViewAdapter().getItemsCount() == 31) {
                            currentDay = dayList30[wheel.getCurrentItem()];
                        }
                        else if(wheel.getViewAdapter().getItemsCount() == 32) {
                            currentDay = dayList31[wheel.getCurrentItem()];
                        }
                    }

                }
            }
        });

        for (int i = 0 ;i < 24;i++) {
            if (i < 10) {
                hourList[i] = "0" + i;
            }
            else {
                hourList[i] = i + "";
            }
        }
        hourAdapter = new ArrayWheelAdapter<>(context,hourList);
        hourAdapter.setTextSize(20);
        hourAdapter.setTextPadding(0,40,0,40);
        hour.setViewAdapter(hourAdapter);
        hour.setCyclic(false);
        hour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    currentHour = hourList[newValue];
                }
            }
        });

        for (int i = 0;i <= 11;i++) {
            if (i < 2) {
                minuteList[i] = "0" + i * 5;
            }
            else {
                minuteList[i] = i * 5 + "";
            }
        }
        minuteAdapter = new ArrayWheelAdapter<>(context,minuteList);
        minuteAdapter.setTextSize(20);
        minuteAdapter.setTextPadding(0,40,0,40);
        minute.setViewAdapter(minuteAdapter);
        minute.setCyclic(false);
        minute.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    currentMinute = minuteList[newValue];
                }
            }
        });
        changeModel();
    }

    private void changeModel() {
        if (model == 0) {
            day.setVisibility(GONE);
            line.setVisibility(GONE);
            view1.setVisibility(GONE);
            hour.setVisibility(GONE);
            view2.setVisibility(GONE);
            minute.setVisibility(GONE);
        }
        if (model == 1) {
            day.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
            view1.setVisibility(GONE);
            hour.setVisibility(GONE);
            view2.setVisibility(GONE);
            minute.setVisibility(GONE);
        }
        if (model == 2) {
            day.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
            view1.setVisibility(VISIBLE);
            hour.setVisibility(VISIBLE);
            view2.setVisibility(GONE);
            minute.setVisibility(GONE);
        }
        if (model == 3) {
            day.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
            view1.setVisibility(VISIBLE);
            hour.setVisibility(VISIBLE);
            view2.setVisibility(VISIBLE);
            minute.setVisibility(VISIBLE);
        }
        if (model == 4) {
            year.setVisibility(GONE);
            view3.setVisibility(GONE);
            day.setVisibility(VISIBLE);
            line.setVisibility(VISIBLE);
            view1.setVisibility(GONE);
            hour.setVisibility(GONE);
            view2.setVisibility(GONE);
            minute.setVisibility(GONE);
        }
    }

    public boolean isNowTimeInvalid() {
        if (currentYear.equals("年")) {
            Toast.makeText(context, "请选择年份", Toast.LENGTH_SHORT).show();
            return false;
        }
        else  if (currentMonth.equals("月")) {
            Toast.makeText(context, "请选择月份", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (currentDay.equals("日")) {
            Toast.makeText(context, "请选择具体天数", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public String getDate() {
        if (model == 0) {
            if (TextUtils.isEmpty(getCurrentMonth())) {
                return getCurrentYear();
            }
            return getCurrentYear() + "-" + getCurrentMonth();
        } else if (model == 1) {
            return getCurrentYear() + "-" + getCurrentMonth() + "-" + getCurrentDay();
        } else if (model == 2) {
            return getCurrentYear() + "-" + getCurrentMonth() + "-" + getCurrentDay() + " " + getCurrentHour();
        } else if (model == 3) {
            return getCurrentYear() + "-" + getCurrentMonth() + "-" + getCurrentDay() + " " + getCurrentHour() + ":" + getCurrentMinute();
        }
        return getCurrentMonth() + "." + getCurrentDay();
    }

    public String getCurrentYear() {
//        if (currentYear.equals("年")) {
//            currentYear = DateUtill.GetNowDate().substring(0,4);
//        }
        return currentYear;
    }

    public String getCurrentMonth() {
        return currentMonth;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public String getCurrentHour() {
        return currentHour;
    }

    public String getCurrentMinute() {
        return currentMinute;
    }


    private void changeDayAdapter() {
        if (!currentMonth.equals("月")) {
            if (currentYear.equals("年")) {
                day.setEnabled(false);
            }
            else {
                day.setEnabled(true);
            }
            int tag = Integer.parseInt(currentMonth);
            switch (tag) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dayAdapter.setItems(dayList31);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dayAdapter.setItems(dayList30);
                    break;
                case 2:
                    if (!TextUtils.isEmpty(currentYear) && !currentYear.equals("年") ) {
                        if (Util.isLeapYear(Integer.parseInt(currentYear))) {
                            dayAdapter.setItems(dayList29);
                        }
                        else {
                            dayAdapter.setItems(dayList28);
                        }
                    }
                    else {
                        dayAdapter.setItems(dayList31);
                    }
            }
        }
        else {
            day.setEnabled(false);
            dayAdapter.setItems(dayList31);
        }
    }
}
