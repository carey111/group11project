package com.geminno.empsys.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geminno.empsys.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Fragment_home extends Fragment{

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.layout_fragment_home,null);
            return view;
    }
}
