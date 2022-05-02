package com.test.myapplicationhomework;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private ProgressBar circleP;
    private Handler mHandler;
    private int mProgressStatus = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_display);


        circleP = findViewById(R.id.progressB1);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x111) {
                    circleP.setProgress(mProgressStatus);//更新进度
                } else {
                    Toast.makeText(ImageActivity.this, "图片加载成功", Toast.LENGTH_SHORT).show();
                    circleP.setVisibility(View.GONE); //进度条不显示，并且不占用空间

                    GridView gridView = findViewById(R.id.girdView1);
                    List<Integer> imageId = new ArrayList<Integer>();
                    Collections.addAll(imageId, R.drawable.img01, R.drawable.img02, R.drawable.img03,
                            R.drawable.img04, R.drawable.img05, R.drawable.img06,
                            R.drawable.img07, R.drawable.img08, R.drawable.img09);


                    BaseAdapter adapter = new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return imageId.size();
                        }

                        @Override
                        public Object getItem(int i) {
                            return i;
                        }

                        @Override
                        public long getItemId(int i) {
                            return i;
                        }

                        @Override
                        public View getView(int i, View view, ViewGroup viewGroup) {
                            ImageView imageView;
                            if (view == null) {
                                imageView = new ImageView(ImageActivity.this);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                imageView.setPadding(5, 0, 5, 0);
                                imageView.setLayoutParams(new LinearLayout.LayoutParams(250, 250));
                            } else {
                                imageView = (ImageView) view;
                            }
                            imageView.setImageResource(imageId.get(i));
                            return imageView;
                        }
                    };
                    gridView.setAdapter(adapter);

                    gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            AlertDialog alert = new AlertDialog.Builder(ImageActivity.this).create();
                            alert.setIcon(R.drawable.img05);
                            alert.setTitle("系统提示");
                            alert.setMessage("确认删除此图片？");

                            alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ImageActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            });

                            alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imageId.remove(position);
                                    adapter.notifyDataSetChanged();
                                    gridView.setAdapter(adapter);
                                    Toast.makeText(ImageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            alert.show();
                            return true;
                        }
                    });

                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgressStatus = doWork();
                    Message m = new Message();
                    if (mProgressStatus < 100) {
                        m.what = 0x111;
                        mHandler.sendMessage(m);
                    } else {
                        m.what = 0x110;
                        mHandler.sendMessage(m);
                        break;
                    }
                }
            }


            private int doWork() {
                mProgressStatus += Math.random() * 10;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return mProgressStatus;
            }
        }).start(); //开启一个线程

    }
}
