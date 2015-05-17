package com.nmid.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nmid.painterdemo.R;
import com.nmid.util.ApkEntity;
import com.nmid.util.IPAddress;
import com.nmid.util.ListData;
import com.squareup.picasso.Picasso;

public class ListAdapter extends BaseAdapter{
	LayoutInflater inflater;
	ArrayList<ApkEntity> apk_list;
	public ListAdapter(Context context,ArrayList<ApkEntity> apk_list){
		this.apk_list = apk_list;
		this.inflater = LayoutInflater.from(context);
	}
	
	public void onDateChange(ArrayList<ApkEntity> apk_list) {
		this.apk_list = apk_list;
		this.notifyDataSetChanged();
	}
	
	class ViewHolder{
		ImageView myImage;
		TextView myText;
	}
	@Override
	public int getCount() {

		return apk_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return apk_list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ApkEntity entity = apk_list.get(arg0);
		final ViewHolder viewHolder;
        if(arg1 == null){
            arg1 = inflater.inflate(R.layout.mypaint_item,null);
            viewHolder = new ViewHolder();
            viewHolder.myImage = (ImageView)arg1.findViewById(R.id.image);
            viewHolder.myText = (TextView)arg1.findViewById(R.id.name);
            arg1.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)arg1.getTag();
        }
        Picasso.with(arg1.getContext()).load(IPAddress.IP+"GreatArtist/loading/"
                + entity.getPaintJson()).resize(480,800).into(viewHolder.myImage);
        viewHolder.myText.setText(entity.getName());
		return arg1;
	}

}
