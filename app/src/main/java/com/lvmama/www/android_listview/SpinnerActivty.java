package com.lvmama.www.android_listview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shiyaorong on 15/12/23.
 */
public class SpinnerActivty extends Activity {

    private TextView textView;
    private Spinner spinner;

    private List<String> list;
    private ArrayAdapter<String> adapter;

    private Spinner mySpinner;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter simpleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_layout);

        textView = (TextView)findViewById(R.id.spinnerTextView);
        spinner = (Spinner)findViewById(R.id.spinner);

        textView.setText("你选择的城市是北京");
        //1. 数据源
        list = new ArrayList<String>();
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");

        //2. 新建ArrayAdapter（数组适配器）   布局文件：android.R.layout.simple_spinner_item
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        //3. adapter 设置一个下拉列表样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //4. spinner加载适配器
        spinner.setAdapter(adapter);
        //5. spinner设置监听器
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.i("tag", "position=" + position + "城市=" + list.get(position));

                String cityName = adapter.getItem(position);
                textView.setText("你选择的城市是" + cityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 自定义下拉菜单样式
        mySpinner = (Spinner)findViewById(R.id.mySpinner);
        dataList = new ArrayList<Map<String, Object>>();

        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.spinneradapter_layout, new String[]{"image", "text"}, new int[]{R.id.spinnerMyImageView, R.id.spinnerMyTextView});
        simpleAdapter.setDropDownViewResource(R.layout.spinneradapter_layout);
        mySpinner.setAdapter(simpleAdapter);
        mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("您的选择是: " + dataList.get(position).get("text")); //simpleAdapter.getItem(position)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("None");
            }
        });


    }

    private List<Map<String, Object>> getData() {

        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.mipmap.ic_launcher);
            map.put("text", "上海" + i);
            dataList.add(map);
        }

        return dataList;
    }

}
