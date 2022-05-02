package com.test.myapplicationhomework;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DetailFragment extends Fragment {

    String[] strings = new String[]{"用户信息","网络设置","亮度设置",
            "音量亮度","其他设置"};

    public static DetailFragment newInstance(int index) {

        Bundle bundle = new Bundle();

        DetailFragment fragment = new DetailFragment();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public int getShownIndex(){
        return getArguments().getInt("index",0); //获取要显示的列表项索引

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        ScrollView scroller = new ScrollView(getActivity()); //创建一个滚动视图
        TextView text = new TextView(getActivity()); //创建一个文本框对象
        text.setPadding(10, 10, 10, 10);//设置内边距
        scroller.addView(text);  //将文本框添加到滚动视图中
        text.setText(strings[getShownIndex()]);  //设置文本框要显示的文本
        return scroller;

    }
}
