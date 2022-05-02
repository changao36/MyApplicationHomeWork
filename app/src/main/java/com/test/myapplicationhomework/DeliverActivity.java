package com.test.myapplicationhomework;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DeliverActivity extends Activity {


    private NetworkChangeReceiver networkChangeReceiver;
    private IntentFilter intentFilter;

    //新建此类，让其继承BroadcastReceiver，并且重写onCreate方法
    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//使用getSystemService得到ConnectivityManager实例
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "网络有效", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliver_data);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//添加广播
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);//使用registerReceiver方法进行注册，将两实例都传进去


        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();
        TextView name = findViewById(R.id.tv_name);
        name.setText("姓名： " + bundle.getString("name"));
        TextView sex = findViewById(R.id.tv_sex);
        sex.setText("性别：" + bundle.getChar("sex"));
        TextView hobby = findViewById(R.id.tv_hobby);
        hobby.setText("爱好： " + bundle.getSerializable("hobby"));
        TextView age = findViewById(R.id.tv_age);
        age.setText("年龄： " + bundle.getInt("age"));
        TextView result = findViewById(R.id.tv_result);
        result.setText("成绩： " + bundle.getByte("result"));


    }

    public void onDestory() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }
}
