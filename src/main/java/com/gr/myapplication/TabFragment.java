package com.gr.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gr on 16-5-4.
 */
public class TabFragment extends Fragment {
    private String title="Default";
    protected static final String TITLE="title";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments()!=null) {
            title = getArguments().getString(TITLE);
        }
        TextView textView=new TextView(getActivity());
        textView.setTextSize(30);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
