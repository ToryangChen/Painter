package com.nmid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nmid.painterdemo.R;
import com.nmid.util.AddScore;
import com.nmid.util.ApkEntity;
import com.nmid.util.IPAddress;
import com.nmid.util.ListData;
import com.nmid.util.URLConnect;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by waxxl on 2015/4/7.
 */

public class GuessLVAdapter extends BaseAdapter {

    int i =-1;
    List<ApkEntity> apk_list = new ArrayList<>();
    ListData listData;
    Context context;
    LayoutInflater mInflater;

    public GuessLVAdapter(Context context,List<ApkEntity> apk_list,ListData listData) {
        super();
        this.context = context;
        this.listData =listData;
        this.apk_list = apk_list;
        this.mInflater = LayoutInflater.from(context);
    }

    public void onDateChange(ListData listData) {
        this.listData = listData;
        this.notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView newImageView;
        EditText answerET;
        ImageButton commitBtn;
        TextView time;
    }

    @Override
    public int getCount() {
        //int i =ListData.list.size();
            return listData.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return apk_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
     //   ApkEntity entity = apk_list.get(position);
        //     LayoutInflater mInflater = LayoutInflater.from(context);
        if(convertView == null)
        {

            convertView = mInflater.inflate(R.layout.guess_item,null);
            holder = new ViewHolder();
            holder.newImageView = (ImageView)convertView.findViewById(R.id.newview);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            holder.answerET = (EditText) convertView.findViewById(R.id.answerET);
            holder.commitBtn = (ImageButton) convertView.findViewById(R.id.commitBtn);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
            holder.answerET.setFocusable(true);
            holder.answerET.setFocusableInTouchMode(true);
            holder.answerET.setClickable(true);

        }
        Picasso.with(convertView.getContext()).load(IPAddress.IP+"GreatArtist/loading/"
                +listData.getList().get(position)).resize(480,800).into(holder.newImageView);

        holder.time.setText(listData.getMap().get(listData.getList().get(position)));



        final View View=convertView;

        holder.answerET.setOnTouchListener(new android.view.View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    i = position;
                }
                return false;
            }
        });
        holder.commitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getAnswer = holder.answerET.getText().toString();
                int position2 =position;
                if(getAnswer.equals("")){
                    Toast.makeText(View.getContext(),"答案不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(getAnswer.equals(listData.getMap().get(listData.getList().get(position2)))){
                    Toast.makeText(View.getContext(),"恭喜您，答对了",Toast.LENGTH_SHORT).show();
                    listData.getMap().remove(listData.getList().get(position2));
                    listData.getList().remove(position2);
                    GuessLVAdapter.this.notifyDataSetChanged();
                    holder.answerET.setText("");
                    new AddScore().start();
                }
                else{
                    Toast.makeText(View.getContext(),"您离正确答案还有点距离，再接再厉哟",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.answerET.clearFocus();
        if (1 != -1 && i == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            holder.answerET.requestFocus();
        }
        holder.answerET.setSelection(holder.answerET.getText().length());
        return convertView;
    }

}
