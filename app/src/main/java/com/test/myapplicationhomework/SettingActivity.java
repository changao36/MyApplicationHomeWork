package com.test.myapplicationhomework;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SettingActivity extends Activity {

    private SeekBar seekBar; //拖动条
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        String a = getIntent().getExtras().getString("system");
        Toast.makeText(SettingActivity.this,"您已进入"+a,Toast.LENGTH_SHORT).show();

        /*final TextView result = findViewById(R.id.textView1);
        seekBar = findViewById(R.id.seekBar1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                result.setText("当前值："+progress); //修改文本视图
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

    }



}
