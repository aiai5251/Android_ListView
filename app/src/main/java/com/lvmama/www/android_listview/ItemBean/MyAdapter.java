package com.lvmama.www.android_listview.ItemBean;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvmama.www.android_listview.R;

import java.util.List;

/**
 * Created by shiyaorong on 15/12/28.
 */
public class MyAdapter extends BaseAdapter {

    private List<ItemBean> mList;

    private LayoutInflater mInflater;

    private ImageLoader mImageLoader;

    public MyAdapter(Context context, List<ItemBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
        mImageLoader = new ImageLoader(); //只初始化一次
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 逗比式 －－－－－－－－－－－－－－－－－－－－－

        //返回每一项的显示内容
//        View view = mInflater.inflate(R.layout.listview_itemlayout, null);
//        ImageView imageView = (ImageView)view.findViewById(R.id.iv_image);
//        TextView tv = (TextView)view.findViewById(R.id.tv_title);
//        TextView content = (TextView)view.findViewById(R.id.tv_content);
//
//        ItemBean itemBean = mList.get(position);
//        imageView.setBackgroundResource(itemBean.ItemImageResid);
//        tv.setText(itemBean.ItemTitle);
//        content.setText(itemBean.ItemContent);
//
//        return view;
        // 逗比式 －－－－－－－－－－－－－－－－－－－－－

        // 普通式 －－－－－－－－－－－－－－－－－－－－－
//        if (convertView == null) { //读取缓存
//            convertView = mInflater.inflate(R.layout.listview_itemlayout, null);
//        }
//        ImageView imageView = (ImageView)convertView.findViewById(R.id.iv_image);
//        TextView tv = (TextView)convertView.findViewById(R.id.tv_title);
//        TextView content = (TextView)convertView.findViewById(R.id.tv_content);
//
//        ItemBean itemBean = mList.get(position);
//        imageView.setBackgroundResource(itemBean.ItemImageResid);
//        tv.setText(itemBean.ItemTitle);
//        content.setText(itemBean.ItemContent);
//        return convertView;
        // 普通式 －－－－－－－－－－－－－－－－－－－－－

        // 文艺式 －－－－－－－－－－－－－－－－－－－－－
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_itemlayout, null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_image);
            viewHolder.title = (TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.content = (TextView)convertView.findViewById(R.id.tv_content);

            convertView.setTag(viewHolder); //建立关系
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        ItemBean itemBean = mList.get(position);
        //读取网络图片
        String url = itemBean.ItemImageUrl;
        viewHolder.imageView.setTag(url);

        //根据tag绑定
        //使用多线程
//        new ImageLoader().showImageByThread(viewHolder.imageView, url);
        //使用AsyncTask加载
//        new ImageLoader().getShowImageByAsyncTask(viewHolder.imageView, url); //每次都创建ImageLoader 不行，要弄成成员变量，只初始化一次
        mImageLoader.getShowImageByAsyncTask(viewHolder.imageView, url);

        viewHolder.imageView.setImageResource(itemBean.ItemImageResid);
        viewHolder.title.setText(itemBean.ItemTitle);
        viewHolder.content.setText(itemBean.ItemContent);

        return convertView;
        // 文艺式 －－－－－－－－－－－－－－－－－－－－－

    }

    class ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView content;
    }


}
