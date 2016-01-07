package com.lvmama.www.android_listview.ItemBean;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Handler;
import java.util.logging.LogRecord;

/**
 * Created by shiyaorong on 15/12/29.
 */
public class ImageLoader {

    private ImageView mImageview;
    private String mUrl;

    //创建缓存cache
    private LruCache<String, Bitmap> mCache;

    public ImageLoader() {
        //获取最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //设定缓存空间
        int cacheSize = maxMemory/4;
        mCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用   返回每次存入的图片有多大
                return value.getByteCount();
            }
        }; //需要指定缓存空间大小
    }

    //增加到缓存
    public void addBitmapTocache(String url, Bitmap bitmap) {
        //判断缓存是否存在
        if (getBitmapFormCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }

    //从缓存中获取数据
    public Bitmap getBitmapFormCache(String url) {
        return mCache.get(url);
    }

    //接收
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageview.getTag().equals(mUrl)) { //缓存机制
                mImageview.setImageBitmap((Bitmap)msg.obj);
            }
        }

    };

    //多线程显示图片  这里只能处理数据，不能显示UI
    public void showImageByThread(final ImageView imageView, final String url) {

        mImageview = imageView;
        mUrl = url;
        //开辟新线程
        new Thread() {
            @Override
            public void run() {
                super.run();

                Bitmap bit = getBitmapFromUrl(url);
                //发送message
                Message message = Message.obtain();
                message.obj = bit;
                mhandler.sendMessage(message);
            }
        }.start();

    }

    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    //------------------------ 使用AsyncTask实现显示图片
    public void getShowImageByAsyncTask(ImageView imageView, String url) {

        //读取缓存数据
        Bitmap bitmap = getBitmapFormCache(url);
        if (bitmap == null) {
            //下载
            new NewsAsyncTask(imageView, url).execute(url);

        } else {
            imageView.setImageBitmap(bitmap); //直接从内容中读取
        }


    }

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView mImageview;
        private String murl;

        public NewsAsyncTask(ImageView imageView, String url) {
            mImageview = imageView;
            murl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = getBitmapFromUrl(params[0]); //下载图片
            if (bitmap != null) { //确认下载成功
                //将不在混存的图片中保存缓存图片
                addBitmapTocache(params[0], bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (mImageview.getTag().equals(mUrl)) { //解决缓存显示错误，图片不会加载错误，每次刷新都会加载，需要使用缓存
                mImageview.setImageBitmap(bitmap);
            }

        }
    }

}
