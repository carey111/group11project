package com.geminno.empsys.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geminno.empsys.FriendInfoActivity;
import com.geminno.empsys.R;
import com.geminno.empsys.pojo.ChatRecordBean;
import com.geminno.empsys.pojo.FriendsBean;
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
 * Created by Administrator on 2016/9/29.
 */
public class Fragment_zuyouquan_haoyou extends Fragment{

    final List<FriendsBean.Friends> friendsList = new ArrayList<FriendsBean.Friends>();
    private ListView lv_friends;
    private BaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.layout_fragment_zuyouquan_haoyou,null);
        lv_friends = ((ListView) v.findViewById(R.id.lv_friends));
        adapter=new BaseAdapter() {
            private ImageView iv_photo;

            @Override
            public int getCount() {
                return friendsList.size();
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
                View view = View.inflate(getActivity(), R.layout.myactivity_listview_friends_item, null);
                TextView tv_nicheng = ((TextView) view.findViewById(R.id.tv_nicheng));

                iv_photo = ((ImageView) view.findViewById(R.id.iv_photo));


               FriendsBean.Friends friend = friendsList.get(position);
                x.image().bind(iv_photo, NetUtils.url+"MyApp/"+friend.userPhotoImg);

                    tv_nicheng.setText(friend.userName);


                return view;
            }


        };
        lv_friends.setAdapter(adapter);
        //从服务器拿
        getFriendsList();
        lv_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), FriendInfoActivity.class);
                FriendsBean.Friends friends=friendsList.get(position);
                intent.putExtra("userId",friends.friendId+"");
                intent.putExtra("userName",friends.userName);
                intent.putExtra("userImg",friends.userPhotoImg);
                intent.putExtra("interestLabel",friends.interestLabel);
                intent.putExtra("birthday",friends.birthday);
                intent.putExtra("sex",friends.sex+"");
                startActivity(intent);
            }
        });
        return v;

    }
    private void getFriendsList() {
        RequestParams params = new RequestParams(NetUtils.url+"MyApp/getallfriendsbypage");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                FriendsBean bean= gson.fromJson(result, FriendsBean.class);

//                System.out.println(bean.status+"??????");
//                System.out.println(bean.chatRecordList.size()+"======");
                friendsList.addAll(bean.friendsList);
                System.out.println(friendsList);
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
}
