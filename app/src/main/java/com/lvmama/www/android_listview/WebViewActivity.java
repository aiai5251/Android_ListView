package com.lvmama.www.android_listview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by shiyaorong on 15/12/24.
 */
public class WebViewActivity extends Activity {

    private String url = "http://2014.qq.com/";
    private WebView webView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        /**
         * 要在webView 加载页面，使用 loadUrl()
         * web资源： webView.loadUrl("http://www.baidu.com")
         * 本地文件用：webView.loadUrl("file:///android_asset/XX.html");
         * 本地文件放在： assets文件中
         *
         * //使页面获取焦点
         * webView.requestFocus();
         */

//        Uri uri = Uri.parse(url);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

        init();
    }

    private void init() {

        webView = (WebView)findViewById(R.id.webview);
//        webView.loadUrl("file:///android_asset/xx.html"); //打开本地
        webView.loadUrl(url); //打开网页
        //覆盖webview默认通过第三方或者系统浏览器打开网页的行为，使得网页可以在webview中打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true: 在webview中打开，  false: 调用系统浏览器或者第三方浏览器打开
//                return super.shouldOverrideUrlLoading(view, url);

                view.loadUrl(url);
                return true;

            }

            // webViewClient 帮助webview去处理一些页面控制和请求通知
        });

        //启用支持JavaScript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //webview加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                //newProgress 1- 100
//                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    //网页加载完毕
                    closeDialog();
                } else {
                    //网页正在加载, 打开progressDialog
                    openDialog(newProgress);
                }
            }

            private void openDialog(int newProgress) {
                if (dialog == null) {
                    dialog = new ProgressDialog(WebViewActivity.this);
                    dialog.setTitle("正在加载");
                    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    dialog.setProgress(newProgress);
                    dialog.show();
                } else {
                    dialog.setProgress(newProgress);
                }
            }

            private void closeDialog() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
            
            
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击返回可以直接返回上一页面
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                Log.i("tag", "返回返回");
                webView.goBack();
                return true;
            } else {
                Log.i("tag", "退出退出");
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
