package com.nmid.painterdemo.com.nmid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nmid.painterdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by waxxl on 2015/4/7.
 */

public class GuessLVAdapter extends BaseAdapter {
    List<String> list = new ArrayList<>();
   // Context context;
    LayoutInflater mInflater;

    public GuessLVAdapter(LayoutInflater mInflater) {
        super();
        this.mInflater = mInflater;

    }
    class ViewHolder{
        ImageView newImageView;
        ImageView doubtView;
        TextView doubtTV;
        TextView interviewTV;
    }
    public void setUser(List<String> list)
    {
        this.list =list;
    }

    @Override
    public int getCount() {

        return 3;
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
        ViewHolder holder;
   //     LayoutInflater mInflater = LayoutInflater.from(context);
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.guess_item,null);
            holder = new ViewHolder();
            holder.newImageView = (ImageView)convertView.findViewById(R.id.newview);
//            holder.doubtTV = (TextView)convertView.findViewById(R.id.doubtTV);
//            holder.interviewTV = (TextView)convertView.findViewById(R.id.interviewTV);
//            holder.doubtView = (ImageView) convertView.findViewById(R.id.newview);
//            holder.zhaoxin = (TextView) convertView.findViewById(R.id.zhaoxin);
//            holder.zhaoxin.getBackground().setAlpha(180);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
//			try {
//				average = musicsJSON.getJSONObject(position).getJSONObject("rating").getString("average");
//				author = musicsJSON.getJSONObject(position).getJSONArray("author").getJSONObject(0).getString("name");
//				image = musicsJSON.getJSONObject(position).getString("image");
//
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
        File sdCardDir = Environment.getExternalStorageDirectory();
        String path = sdCardDir.getPath()+"/大画师";
        String uploadFile =path+"/"+"1.jpg";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(uploadFile, options);
        holder.newImageView.setImageBitmap(bm);
/*        holder.doubtTV.setText(list.get(position)+position);
        holder.doubtTV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent doubtIntent = new Intent();
                doubtIntent.setClass(MainActivity.this, DoubtActivity.class);
                startActivity(doubtIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        holder.interviewTV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent doubtIntent = new Intent();
                doubtIntent.setClass(MainActivity.this, DoubtActivity.class);
                startActivity(doubtIntent);
            }
        });
        holder.newImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent doubtIntent = new Intent();
                doubtIntent.setClass(MainActivity.this, DoubtActivity.class);
                startActivity(doubtIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });*/

        return convertView;
    }
}
