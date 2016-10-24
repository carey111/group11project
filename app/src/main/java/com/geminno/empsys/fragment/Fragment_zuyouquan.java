package com.geminno.empsys.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.geminno.empsys.R;
import com.geminno.empsys.pojo.ChatRecordBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Fragment_zuyouquan  extends Fragment{
      Fragment_zuyouquan_haoyou fragment_zuyouquan_haoyou;
      Fragment_zuyouquan_xiaoxi fragment_zuyouquan_xiaoxi;
      Fragment[] fragments;

     //按钮的数组，一开始第一个按钮被选中
     Button[] tabs;
    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    private Button btn_xiaoxi;
    private Button btn_haoyou;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.layout_fragment_zuyoquan,null);

        //初始化fragment
        Fragment_zuyouquan_haoyou fragment_zuyouquan_haoyou=new Fragment_zuyouquan_haoyou();
        Fragment_zuyouquan_xiaoxi fragment_zuyouquan_xiaoxi=new Fragment_zuyouquan_xiaoxi();

        //所有fragment的数组
        fragments=new Fragment[]{fragment_zuyouquan_xiaoxi,fragment_zuyouquan_haoyou};
     //设置按钮的数组
        tabs=new Button[2];
        btn_xiaoxi = ((Button) view.findViewById(R.id.btn_xiaoxi));
        btn_haoyou = ((Button) view.findViewById(R.id.btn_haoyou));
        tabs[0]=btn_xiaoxi;
        tabs[1]=btn_haoyou;
        btn_xiaoxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIndex=0;//选中第1项
                switchFragment();
                btn_haoyou.setBackgroundResource(R.drawable.buttonstyle);
                btn_xiaoxi.setBackgroundColor(Color.WHITE);
            }
        });

        btn_haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIndex=1;//选中第2项
                switchFragment();
                btn_xiaoxi.setBackgroundResource(R.drawable.buttonstyle);
                btn_haoyou.setBackgroundColor(Color.WHITE);
            }
        });

        //界面初始显示第一个fragment;添加第一个fragment
         getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_xiaoxi_haoyou, fragments[0]).commit();
        //初始时，按钮1选中
        tabs[0].setSelected(true);
        return view;
    }

    public void switchFragment() {
        android.support.v4.app.FragmentTransaction transaction;
            //如果选择的项不是当前选中项，则替换；否则，不做操作
            if(newIndex!=oldIndex){
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.hide(fragments[oldIndex]);//隐藏当前显示项

                //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.fl_xiaoxi_haoyou,fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }
        //之前选中的项，取消选中
        tabs[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        tabs[newIndex].setSelected(true);
        //当前选择项变为选中项
        oldIndex=newIndex;
    }

}
