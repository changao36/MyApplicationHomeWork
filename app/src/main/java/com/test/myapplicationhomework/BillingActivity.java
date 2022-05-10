package com.test.myapplicationhomework;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillingActivity extends Activity {

    private CharSequence flag;
    private TextView textView;
    public double myBalance = 1000.00;
    private final String[] columns = {"time", "money", "flag"};
    private Uri uri = Uri.parse("content://com.test.myapplicationhomework.billProvider");
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_record);
        EditText money = findViewById(R.id.money);
        Button buttonSubmit = findViewById(R.id.submit);
        ListView listView = findViewById(R.id.bill);


        textView = findViewById(R.id.spend);
        registerForContextMenu(textView);  //为文本框注册上下文菜单


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = money.getText().toString();
                list.clear();
                if (s != "") {
                    BigDecimal balance = new BigDecimal(myBalance);
                    ContentResolver resolver = getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    contentValues.put("time", formatter.format(date));
                    contentValues.put("money", s);
                    contentValues.put("flag", flag.toString());
                    Log.i("bill", "target------------->" + contentValues.get("time") + "||" + contentValues.get("money"));
                    resolver.insert(uri, contentValues);
                    Cursor cursor = resolver.query(uri, null, null, null, null);
                    while (cursor.moveToNext()) {
                        int columnIndex = cursor.getColumnIndex(columns[0]);
                        int columnIndex1 = cursor.getColumnIndex(columns[1]);
                        int columnIndex2 = cursor.getColumnIndex(columns[2]);
                        String string = cursor.getString(columnIndex);
                        String string1 = cursor.getString(columnIndex1);
                        String string2 = cursor.getString(columnIndex2);
                        if (string2.equals("支出")) {
                            list.add(string + "   支出：    " + string1+"元");
                        }
                        if (string2.equals("收入")) {
                            list.add(string + "   收入：    " + string1+"元");
                        }
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BillingActivity.this, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(arrayAdapter);
                    cursor.close();
                }
            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = new MenuInflater(this);//实例化对象
        menuInflater.inflate(R.menu.contextmenu, menu); //解析菜单文件
        menu.setHeaderIcon(R.drawable.money);
        menu.setHeaderTitle("请选择： ");
    }

    public boolean onContextItemSelected(@NonNull MenuItem item) {

        flag = item.getTitle();
        switch (item.getItemId()) {
            case R.id.spending:
                textView.setText("支出：");
                textView.setTextSize(15);
                break;
            case R.id.earning:
                textView.setText("收入： ");
                textView.setTextSize(15);
                break;
        }
        return true;
    }


}
