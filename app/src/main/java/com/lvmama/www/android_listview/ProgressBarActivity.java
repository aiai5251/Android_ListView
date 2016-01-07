package com.lvmama.www.android_listview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shiyaorong on 15/12/23.
 */
public class ProgressBarActivity extends Activity implements View.OnClickListener {

    private ProgressBar progressBar;
    private Button add;
    private Button reduce;
    private Button reset;
    private TextView text;

    private Button showDialog;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启用窗口特征，启用带进度和不带进度的进度条
        requestWindowFeature(Window.FEATURE_PROGRESS);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.progressbar_layout);

        setProgressBarVisibility(true);
        setProgressBarIndeterminateVisibility(false);//false不显示
        setProgress(9999);////Max=10000

        progressBar = (ProgressBar)findViewById(R.id.progressBar_horizontal);
        add = (Button)findViewById(R.id.add);
        reduce = (Button)findViewById(R.id.reduce);
        reset = (Button)findViewById(R.id.reset);
        text = (TextView)findViewById(R.id.text);

        add.setOnClickListener(this);
        reduce.setOnClickListener(this);
        reset.setOnClickListener(this);

        reloadData();

        showDialog = (Button)findViewById(R.id.progressDialog);
        showDialog.setOnClickListener(this);

    }

    private void reloadData() {
        //获取第一进度
        int first = progressBar.getProgress();
        //获取第二进度
        int second = progressBar.getSecondaryProgress();
        //获取最大进度
        int max = progressBar.getMax();
        text.setText("第一进度百分比：" + (int)(first/(float)max * 100) + "% 第二进度百分比" + (int)(second/(float)max * 100) + "%");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                //增加第一进度和第二进度10个刻度
                progressBar.incrementProgressBy(10);
                progressBar.incrementSecondaryProgressBy(10);
                break;
            case R.id.reduce:
                //减少第一进度和第二进度10个刻度
                progressBar.incrementProgressBy(-10);
                progressBar.incrementSecondaryProgressBy(-10);
                break;
            case R.id.reset:
                //重置
                progressBar.setProgress(10);
                progressBar.setSecondaryProgress(20);
                break;
            case R.id.progressDialog:
                //新建progressDialog对象
                progressDialog = new ProgressDialog(ProgressBarActivity.this);
                //设置显示风格
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                //设置标题
                progressDialog.setTitle("慕课网");
                progressDialog.setMessage("哈哈哈哈哈哈哈哈哈哈");
                //设置图标
                progressDialog.setIcon(R.mipmap.qqicon);

                /**
                 * 设置进度条的一些信息
                 */
                progressDialog.setMax(100);
//                progressDialog.setProgress(50);
//                progressDialog.setSecondaryProgress(80);

                //设置初始化已经增长到的进度
                progressDialog.incrementProgressBy(50);
                //进度条明显精确显示
                progressDialog.setIndeterminate(false);

                /**
                 * 设定确定按钮
                 */
                progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProgressBarActivity.this, "欢迎啊哈哈", Toast.LENGTH_SHORT).show();
                    }
                });

                //是否可以通过返回按钮退出对话框
                progressDialog.setCancelable(true);

                //显示ProgeressDialog
                progressDialog.show();
                break;
            default:
                break;
        }

        reloadData();
    }
}
