package com.nmid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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
import com.nmid.util.ApkEntity;
import com.nmid.util.IPAddress;
import com.nmid.util.ListData;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by waxxl on 2015/4/7.
 */

public class GuessLVAdapter extends BaseAdapter {
    List<ApkEntity> apk_list = new ArrayList<>();
    // Context context;
    LayoutInflater mInflater;

    public GuessLVAdapter(Context context,List<ApkEntity> apk_list) {
        super();
        this.apk_list = apk_list;
        this.mInflater = LayoutInflater.from(context);
    }

    public void onDateChange(ArrayList<ApkEntity> apk_list) {
        this.apk_list = apk_list;
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
            return apk_list.size();
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
        ApkEntity entity = apk_list.get(position);
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
            Picasso.with(convertView.getContext()).load(IPAddress.IP+"GreatArtist/loading/"
                    +ListData.list.get(position)).resize(480,800).into(holder.newImageView);

            holder.time.setText(entity.getName());
        }else{
            holder = (ViewHolder)convertView.getTag();
        }


//        File sdCardDir = Environment.getExternalStorageDirectory();
//        String path = sdCardDir.getPath()+"/大画师";
//        String uploadFile =path+"/"+"1.jpg";
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//        Bitmap bm = BitmapFactory.decodeFile(uploadFile, options);
//        holder.newImageView.setImageBitmap(bm);
        final View View=convertView;
        holder.commitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getAnswer = holder.answerET.getText().toString().trim();
                if(getAnswer.equals("")){
                    Toast.makeText(View.getContext(),"答案不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(getAnswer.equals(ListData.map.get(ListData.list.get(position)))){
                    Toast.makeText(View.getContext(),"恭喜您，答对了",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(View.getContext(),"您离正确答案还有点距离，再接再厉哟",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
}
