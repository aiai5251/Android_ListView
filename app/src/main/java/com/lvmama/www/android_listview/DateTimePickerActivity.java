package com.lvmama.www.android_listview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by shiyaorong on 15/12/23.
 */
public class DateTimePickerActivity extends Activity {

    private DatePicker datePicker;
    private TimePicker timePicker;

    private Calendar cal;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datetimepicker_layout);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.timePicker);

        //获取日历的对象
        cal = Calendar.getInstance();
        //获取年月日时分秒的信息
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1; //一月是0开始计算的
        day = cal.get(Calendar.DAY_OF_MONTH); //当月的多少天
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        setTitle(year + "-" + month + "-" + day + "-" + hour + "-" + minute);

        //datePicker初始化
        datePicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //选中的
                setTitle(year + "-" + (monthOfYear + 1) + "-"+dayOfMonth);
            }
        });


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setTitle(hourOfDay + ":" + minute);
            }
        });

//        // 对话框的形式展示日历
//        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                setTitle(year + "-" + (monthOfYear + 1) + "-"+dayOfMonth);
//            }
//        }, year, cal.get(Calendar.MONTH), day).show();
//
//        // 对话框的形式展示事件时间选择器
//        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                setTitle(hourOfDay + ":" + minute);
//            }
//        }, hour, minute, true).show();

    }
}
