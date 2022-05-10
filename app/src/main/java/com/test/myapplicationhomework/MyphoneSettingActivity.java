package com.test.myapplicationhomework;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyphoneSettingActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_setting);

    }

    private void initWidgets() {
        findViewById(R.id.tv_internet).setOnClickListener(this);
        findViewById(R.id.tv_equipment).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_internet:

                Toast.makeText(this, "您点击了 网络和互联网", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_equipment:
                Toast.makeText(this, "您点击了 连接的设备", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }
    }
}
