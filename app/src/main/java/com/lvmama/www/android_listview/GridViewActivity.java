package com.lvmama.www.android_listview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shiyaorong on 15/12/23.
 */
public class GridViewActivity extends Activity {

    private GridView gridView;

    private List<Map<String, Object>> dataList;

    private int[] icon = {R.mipmap.microqqnormal, R.mipmap.remarkicon,
            R.mipmap.microsinaselect, R.mipmap.mtcarecount,
            R.mipmap.mtcarecountsel, R.mipmap.payicon,
            R.mipmap.payicon2, R.mipmap.paytypeonline,
            R.mipmap.performticket, R.mipmap.placeorderforward,
            R.mipmap.recommendcollect, R.mipmap.redbagshare};
    private String[] iconName = {"QQ空间", "备忘录", "新浪微博", "取消收藏", "喜欢", "银联", "支付宝", "景区支付", "KTV", "闹钟", "收藏", "分享"};

    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_layout);

        gridView = (GridView)findViewById(R.id.gridView);

        //准备数据源
        //新建适配器（SimpleAdapter）
        //GridView加载适配器
        //GridView配置事件监听器（OnItemClickListener）
        dataList = new ArrayList<Map<String, Object>>();
        adapter = new SimpleAdapter(this, getData(), R.layout.gridviewadapter_layout,
                                    new String[]{"imageview", "textview"},
                                    new int[]{R.id.imageview, R.id.textview});
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tag", iconName[position]);
            }
        });


    }

    private List<Map<String, Object>> getData() {

        for (int i = 0; i < iconName.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("imageview", icon[i]);
            map.put("textview", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }

}
