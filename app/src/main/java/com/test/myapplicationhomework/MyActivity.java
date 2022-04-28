package com.test.myapplicationhomework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final ListView listView = (ListView) findViewById(R.id.listView1);
        /*适配器*/
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this,R.array.ctype, android.R.layout.simple_list_item_checked);//创建适配器
        listView.setAdapter(arrayAdapter);//将适配器和ListView关联
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    startActivity(new Intent(MyActivity.this,ImageActivity.class));
                }
                if (i == 1){
                    
                } else {
                    int a = i+1;
                    String result = adapterView.getItemAtPosition(i).toString(); //获取选择项的值
                    Toast.makeText(MyActivity.this,"您选择了第"+a+"项",Toast.LENGTH_SHORT).show(); //显示提示消息框
                }

            }
        });

    }
}
