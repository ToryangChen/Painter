package com.nmid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nmid.painterdemo.R;
import com.nmid.util.IPAddress;
import com.nmid.util.ListData;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

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
        EditText answerET;
        ImageButton commitBtn;
        ProgressBar updating;
    }
    public void setUser(List<String> list)
    {
        this.list =list;
    }

    @Override
    public int getCount() {
        int i =ListData.list.size();
            return i;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //     LayoutInflater mInflater = LayoutInflater.from(context);
        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.guess_item,null);
            holder = new ViewHolder();
            holder.newImageView = (ImageView)convertView.findViewById(R.id.newview);
            holder.answerET = (EditText) convertView.findViewById(R.id.answerET);
            holder.commitBtn = (ImageButton) convertView.findViewById(R.id.commitBtn);
            holder.updating = (ProgressBar) convertView.findViewById(R.id.updating);
            holder.updating.setVisibility(View.VISIBLE);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(convertView.getContext()).load(IPAddress.IP+"GreatArtist/loading/"
                +ListData.list.get(position)).resize(200,200).into(holder.newImageView);
        holder.updating.setVisibility(View.GONE);
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
