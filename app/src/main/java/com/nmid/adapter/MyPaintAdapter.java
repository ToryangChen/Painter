package com.nmid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nmid.painterdemo.R;
import com.nmid.util.BaseData;
import com.nmid.util.IPAddress;
import com.nmid.util.ListData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toryang on 2015/5/8.
 */
public class MyPaintAdapter extends BaseAdapter {
    List<String> list = new ArrayList<>();
    LayoutInflater inflater;

    public MyPaintAdapter(LayoutInflater inflater){
        super();
        this.inflater = inflater;
    }


    class ViewHolder{
        ImageView myImage;
        TextView imageName;
    }
    @Override
    public int getCount() {

        return ListData.list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.mypaint_item,null);
            viewHolder = new ViewHolder();
            viewHolder.myImage = (ImageView)convertView.findViewById(R.id.mypaintImage);
            viewHolder.imageName = (TextView)convertView.findViewById(R.id.paintName);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(convertView.getContext()).load(IPAddress.IP+"GreatArtist/loading/"
                +ListData.list.get(position)).resize(200,200).into(viewHolder.myImage);

        return convertView;
    }
}
