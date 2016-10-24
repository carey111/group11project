package com.geminno.empsys.fragment;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.geminno.empsys.R;
import com.geminno.empsys.pojo.ChatRecordBean;
import com.geminno.empsys.utils.NetUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class Fragment_zuyouquan_xiaoxi extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    final List<ChatRecordBean.ChatRecord> chatRecordList = new ArrayList<ChatRecordBean.ChatRecord>();
    private ListView lv_chatRecord;
//    private static final int REFRESH_COMPLETE = 0X110;
    private BaseAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressBar progress;
//    private Handler mHandler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case REFRESH_COMPLETE:
//
//                    mSwipeLayout.setRefreshing(false);
////                    getChatRecordList();
////                    lv_chatRecord.setAdapter(adapter);
//
//                   adapter.notifyDataSetChanged();
//
//                    break;
//
//            }
//        }
//
//        ;
//    };
    private Handler mHandler = new Handler();
    private void refreshView() {

    lv_chatRecord.setAdapter(adapter);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragment_zuyouquan_xiaoxi, null);
        lv_chatRecord = ((ListView) v.findViewById(R.id.lv_chatRecord));
        mSwipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.id_swipe_ly);
        mSwipeLayout.setOnRefreshListener(this);
        progress = ((ProgressBar) v.findViewById(R.id.progress));
//        mSwipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
//                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        adapter = new BaseAdapter() {

            private ImageView iv_photo;

            @Override
            public int getCount() {
                return chatRecordList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
//                Log.i(TAG, "加载listview item position:" + position);
                //打气筒
                View view = View.inflate(getActivity(), R.layout.mainactivity_listview_record_item, null);
                TextView tv_nicheng = ((TextView) view.findViewById(R.id.tv_nicheng));
                TextView tv_context = ((TextView) view.findViewById(R.id.tv_context));
                iv_photo = ((ImageView) view.findViewById(R.id.iv_photo));


                ChatRecordBean.ChatRecord chatRecord = chatRecordList.get(position);
                x.image().bind(iv_photo, NetUtils.url+"MyApp/"+ chatRecord.userPhotoImg);
                Log.i("Fragment", "getView: "+NetUtils.url+"MyApp/"+ chatRecord.userPhotoImg);
                tv_nicheng.setText(chatRecord.userName);
                tv_context.setText(chatRecord.recordContext);

                return view;
            }
        };

        //从服务器拿
        getChatRecordList();
        //  lv_chatRecord.setAdapter(adapter);
        refreshView();


        return v;


    }

    private void getChatRecordList() {
        RequestParams params = new RequestParams("http://10.0.2.2:8080/MyApp/getchatrecordbypage");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                ChatRecordBean bean = gson.fromJson(result, ChatRecordBean.class);
                //如果从服务器拿到数据，就将progress动画设置消失
                progress.setVisibility(View.GONE);
//                System.out.println(bean.status+"??????");
//                System.out.println(bean.chatRecordList.size()+"======");
                chatRecordList.addAll(bean.chatRecordList);
                System.out.println(chatRecordList);
                //通知listview更新界面
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void onRefresh() {
//        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
        mHandler.postDelayed(mRefresh, 2000);
    }
    private Runnable mRefresh = new Runnable() {

        @Override
        public void run() {
            refreshView();
            mSwipeLayout.setRefreshing(false);

        }

    };


}

