package com.geminno.empsys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import com.geminno.empsys.fragment.Fragment_home;
import com.geminno.empsys.fragment.Fragment_mine;
import com.geminno.empsys.fragment.Fragment_zufang;
import com.geminno.empsys.fragment.Fragment_zuyouquan;

public class MyActivity extends AppCompatActivity implements View.OnClickListener{


    private RadioButton rb_zuyouquan;
    private RadioButton rb_home;
    private RadioButton rb_house;
    private RadioButton rb_mine;
    Fragment_zuyouquan fragment_zuyouquan;
    Fragment_home fragment_home;
    Fragment_zufang fragment_zufang;
    Fragment_mine fragment_mine;


    Fragment[] fragments;
    //按钮的数组，一开始第一个按钮被选中
    Button[] tabs;

    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //初始化fragment
        fragment_zuyouquan=new Fragment_zuyouquan();
        fragment_home=new Fragment_home();
        fragment_zufang=new Fragment_zufang();
        fragment_mine=new Fragment_mine();

        //所有fragment的数组
        fragments=new Fragment[]{fragment_home,fragment_zufang,fragment_zuyouquan,fragment_mine};

        //设置按钮的数组
        tabs=new Button[4];


        rb_zuyouquan = ((RadioButton) findViewById(R.id.rb_zuyouquan));
        rb_home = ((RadioButton) findViewById(R.id.rb_home));
        rb_house = ((RadioButton) findViewById(R.id.rb_house));
        rb_mine = ((RadioButton) findViewById(R.id.rb_mine));
        tabs[0]=rb_home;
        tabs[1]=rb_house;
        tabs[2]=rb_zuyouquan;
        tabs[3]=rb_mine;


        rb_zuyouquan.setOnClickListener(this);
        rb_home.setOnClickListener(this);
        rb_house.setOnClickListener(this);
        rb_mine.setOnClickListener(this);
        //初始时，按钮1选中
        tabs[0].setSelected(true);
        //界面初始显示第一个fragment;添加第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragments[0]).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_home:
                newIndex=0;//选中第1项
                break;
            case R.id.rb_house:
                newIndex=1;//选中第2项
                break;
            case R.id.rb_zuyouquan:
                newIndex=2;//选中第3项
                break;
            case R.id.rb_mine:
                newIndex=3;//选中第4项
                break;
        }
        switchFragment();
    }
    public void switchFragment() {
        FragmentTransaction transaction;
        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if(newIndex!=oldIndex){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.hide(fragments[oldIndex]);//隐藏当前显示项

            //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.fl_content,fragments[newIndex]);
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

