package com.test.myapplicationhomework;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class SettingListFragment extends ListFragment {

    int curCheckPosition = 0;  //当前选择的索引位置

    String[] strings = new String[]{"用户设置","网络设置","亮度设置","音量设置","其他设置"};
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_checked,strings));

        if (savedInstanceState != null) {
            curCheckPosition = savedInstanceState.getInt("curChoice",0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE); //设置列表为单选模式
        showDetails(curCheckPosition); //显示详细内容


    }

    //重写onSaveInstanceState()方法，保存当前选中的列表项的索引值
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice",curCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        showDetails(position);
    }

    private void showDetails(int index) {
        curCheckPosition = index;
        getListView().setItemChecked(index,true); //设置选中列表项为选中状态
        DetailFragment details = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail);//获取用于显示详细内容的Fragment

        if (details == null||details.getShownIndex() != index) {
            //创建一个新的DetailFragment实例 ，用于显示当前选择项对应的详细内容
            details = DetailFragment.newInstance(index);

            //要在activity中管理fragment，需要使用FragmentManager
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.detail,details); //替换原来显示的详细内容
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //设置转换效果

            ft.commit(); //提交事务

        }
    }
}
