package com.lvmama.www.android_listview;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lvmama.www.android_listview.ItemBean.ItemBean;
import com.lvmama.www.android_listview.ItemBean.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private ListView listView;
    private ArrayAdapter<String> arr_adapater; //数组适配器
    private SimpleAdapter simp_adapter; //简单适配器

    private List<Map<String, Object>> dataList;


    private Calendar cal;

    private int year;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        //请求数据
        String url = "http://www.imooc.com/api/teacher?type=4&num=30";
        new NewsAsyncTask().execute(url);

        //使用对象来存储数据
//        List<ItemBean> itemBeanList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//
//            if (i == 0) {
//                itemBeanList.add(new ItemBean(R.mipmap.mtcarecountsel, "我是标题" + i, "跳转DateTimePickerActivity"));
//            } else if (i == 1) {
//                itemBeanList.add(new ItemBean(R.mipmap.paytypeonline, "我是标题" + i, "对话框TimePicker"));
//            } else if (i == 2) {
//                itemBeanList.add(new ItemBean(R.mipmap.payicon, "我是标题" + i, "对话框TimePicker"));
//            } else if (i == 3) {
//                itemBeanList.add(new ItemBean(R.mipmap.payicon2, "我是标题" + i, "跳转GridViewActivity"));
//            } else if (i == 4) {
//                itemBeanList.add(new ItemBean(R.mipmap.performticket, "我是标题" + i, "跳转SpinnerActivity"));
//            } else if (i == 5) {
//                itemBeanList.add(new ItemBean(R.mipmap.redbagshare, "我是标题" + i, "跳转ProgressBarActivity"));
//            } else if (i == 6) {
//                itemBeanList.add(new ItemBean(R.mipmap.remarkicon, "我是标题" + i, "跳转WebviewActivity"));
//            } else  {
//                itemBeanList.add(new ItemBean(R.mipmap.microqqnormal, "我是标题" + i, "我是内容" + i));
//            }
//        }
//        listView.setAdapter(new MyAdapter(this, itemBeanList));


        //1. 新建一个数据适配器
        //ArrayAdapter(上下文，当前Listview加载的每一个列表项所对应的布局文件, 数据源)
        //2. 适配器加载数据源
//        String[] arr_data = {"hehe", "haha", "hoho"};
//        arr_adapater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);
//        listView.setAdapter(arr_adapater);

        //SimoleAdapter()
        /**
         * context：上下问
         * data：数据源 一个Map组成的list集合
         *       每一个Map都会去对应Listview列表中的一行
         *       每一个Map（键－值对）中的键必须包含所有的from中所指定的健
         * resource：列表项的布局文件ID
         * form：Map中的键名
         * to：绑定数据视图中的ID，与from成对应关系
         */

//        dataList = new ArrayList<Map<String, Object>>();
//        simp_adapter = new SimpleAdapter(this, getData(), R.layout.item, new String[]{"pic", "text"}, new int[]{R.id.pic, R.id.text});
        //3. 视图（listview）加载适配器
//        listView.setAdapter(simp_adapter);

        // 单个item点击
        listView.setOnItemClickListener(this);

        // scroll 监听
        listView.setOnScrollListener(this);


        //获取日历的对象
        cal = Calendar.getInstance();
        //获取年月日时分秒的信息
        year = cal.get(Calendar.YEAR);
        day = cal.get(Calendar.DAY_OF_MONTH); //当月的多少天
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    private List<Map<String, Object>> getData() {

        for (int i = 0; i < 20; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pic", R.mipmap.ic_launcher);
            if (i == 0) {
                map.put("text", "跳转DateTimePickerActivity");
            } else if (i == 1) {
                map.put("text", "对话框DatePicker");
            } else if (i == 2) {
                map.put("text", "对话框TimePicker");
            } else if (i == 3) {
                map.put("text", "跳转GridViewActivity");
            } else if (i == 4) {
                map.put("text", "跳转SpinnerActivity");
            } else if (i == 5) {
                map.put("text", "跳转ProgressBarActivity");
            } else if (i == 6) {
                map.put("text", "跳转WebviewActivity");
            } else  {
                map.put("text", "hehe" + i);
            }
            dataList.add(map);
        }

        return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Intent intent = new Intent(MainActivity.this, DateTimePickerActivity.class);
            startActivity(intent);
            return;
        } else if (position == 1) {
            // 对话框的形式展示日历
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    setTitle(year + "-" + (monthOfYear + 1) + "-"+dayOfMonth);
                }
            }, year, cal.get(Calendar.MONTH), day).show();
            return;
        } else if (position == 2) {
            // 对话框的形式展示事件原则器
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    setTitle(hourOfDay + ":" + minute);
                }
            }, hour, minute, true).show();
            return;
        } else if (position == 3) {
            Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
            startActivity(intent);
            return;
        } else if (position == 4) {
            Intent intent = new Intent(MainActivity.this, SpinnerActivty.class);
            startActivity(intent);
            return;
        } else if (position == 5) {
            Intent intent = new Intent(MainActivity.this, ProgressBarActivity.class);
            startActivity(intent);
            return;
        } else if (position == 6) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            startActivity(intent);
            return;
        }
        String text = listView.getItemAtPosition(position) + "";
        Toast.makeText(this, "position=" + position + " text=" + text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                Log.i("Main", "用户在手指离开屏幕之前，由于用力滑了一下，视图仍然由于惯性继续滑动");
//                Map<String , Object> map = new HashMap<String, Object>();
//                map.put("pic", R.mipmap.ic_launcher);
//                map.put("text", "增加项");
//                dataList.add(map); //更新数据源
//                simp_adapter.notifyDataSetChanged(); //通知UI刷新界面
                break;
            case SCROLL_STATE_IDLE:
                Log.i("Main", "视图已经停止滑动");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("Main", "手指没有离开屏幕，视图正在滑动");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }


    //在线程中请求数据
    class NewsAsyncTask extends AsyncTask<String, Void, List<ItemBean>> { //第一个为传进去的参数url，第三个为返回的数据
        @Override
        protected List<ItemBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<ItemBean> list) {
            super.onPostExecute(list);

            listView.setAdapter(new MyAdapter(MainActivity.this, list));

        }
    }

    private List<ItemBean> getJsonData(String url) {
        List<ItemBean> itemBeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            ItemBean itemBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    itemBean = new ItemBean(R.mipmap.mtcarecountsel, jsonObject.getString("name"), jsonObject.getString("description"));
                    itemBean.ItemImageUrl = jsonObject.getString("picSmall");
                    itemBeanList.add(itemBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemBeanList;
    }

    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
