package com.nmid.painterdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.nmid.adapter.GuessLVAdapter;
import com.nmid.util.URLConnect;

//import com.nmid.painterdemo.com.nmid.GuessLVAdapter;

/**
 * Created by Toryang on 2015/3/30.
 */
public class GuessFragment extends Fragment {
    ListView guessLV = null;
    static GuessLVAdapter guessLVAdapter =null;
    Button test = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_guess,container,false);
        guessLV = (ListView)rootView.findViewById(R.id.guessLV);
        test = (Button) rootView.findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new URLConnect().start();
            }
        });
        guessLVAdapter = new GuessLVAdapter(inflater);
        guessLV.setAdapter(guessLVAdapter);
        return rootView;
    }

}
