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
        Toast.makeText(SettingActivity.this, "您已进入" + a, Toast.LENGTH_SHORT).show();


    }


}
