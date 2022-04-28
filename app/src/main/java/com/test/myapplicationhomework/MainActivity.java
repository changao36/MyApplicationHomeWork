package com.test.myapplicationhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText username = findViewById(R.id.et_username);
        EditText password = findViewById(R.id.et_passsword);
        Button login = findViewById(R.id.btn_login);
        Button register = findViewById(R.id.btn_register);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent =getIntent();
                Bundle bundle = intent.getExtras();
                if(username.getText().toString().equals("admin")&&password.getText().toString().equals("123456")){
                    Toast.makeText(MainActivity.this,"登陆成功！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MyActivity.class));

                }else if (username.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"登录失败，用户名或密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}