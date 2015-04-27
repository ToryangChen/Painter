package com.nmid.painterdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Matrix;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Inflater;


/**
 * Created by Toryang on 2015/3/30.
 */
public class PaintFragment extends Fragment implements View.OnClickListener{
    private ImageView imageView;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private Button penButton,eraserButton,clearButton,invisibleButton;
    private Spinner colorChoice;
    private ArrayAdapter adapter;
    private View view;
    private String name;
    Context context = null;
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_paint, container, false);


        Thread thread = new Thread(){
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                        initEvent();
                        initImage();
                    }
                });
            }
        };
        thread.start();
        return view;
    }
    private void initView(){
        penButton = (Button)view.findViewById(R.id.pen_button);
        eraserButton = (Button)view.findViewById(R.id.eraser_button);
        clearButton = (Button)view.findViewById(R.id.clearButton);
        invisibleButton = (Button)view.findViewById(R.id.invisibleButton);

        colorChoice = (Spinner)view.findViewById(R.id.color);


   }

    private void initEvent(){
        penButton.setOnClickListener(this);
        eraserButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        invisibleButton.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.color,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colorChoice.setAdapter(adapter);
        colorChoice.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
        colorChoice.setVisibility(View.VISIBLE);

    }

    private class SpinnerXMLSelectedListener implements OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    paint.setColor(Color.BLACK);
                    break;
                case 1:
                    paint.setColor(Color.RED);
                    break;
                case 2:
                    paint.setColor(Color.YELLOW);
                    break;
                case 3:
                    paint.setColor(Color.BLUE);
                    break;
                case 4:
                    paint.setColor(Color.GREEN);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.pen_button:
                System.out.println("pen");
                paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(5);
                break;
            case R.id.eraser_button:
                paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(100);
                break;
            case R.id.clearButton:
                clear();
                break;
            case R.id.invisibleButton:
                dialog2();

                break;
        }
    }
    private void initImage(){
        this.imageView = (ImageView)view.findViewById(R.id.paintView);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        System.out.println("heigth2 : " + dm.heightPixels);
        System.out.println("width2 : " + dm.widthPixels);
        int VIEW_WIDTH = dm.widthPixels;
        int VIEW_HEIGHT = dm.heightPixels - (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,105,
                this.getResources().getDisplayMetrics());

        baseBitmap = Bitmap.createBitmap(VIEW_WIDTH,VIEW_HEIGHT,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(baseBitmap);
        canvas.drawColor(Color.WHITE);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawBitmap(baseBitmap,new Matrix(),paint);

        imageView.setImageBitmap(baseBitmap);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        imageView.setImageBitmap(baseBitmap);
                        break;
                }
                return true;
            }
        });

    }

    public String  createPath(){
        String path = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File sdCardDir = Environment.getExternalStorageDirectory();
            path = sdCardDir.getPath()+"/大画师";
            File path1 = new File(path);
            if(!path1.exists()){
                path1.mkdirs();
            }
        }
        return path;
    }

    public void save(){
        try{
            String path = createPath();

            //String fileName = System.currentTimeMillis()+".jpg";
            String fileName = name+".jpg";
            File file = new File(path,fileName);
            OutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.close();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            //sendBroadcast(intent);
            new Upload(fileName).start();
            Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
            initImage();

        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity(),"保存图片失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(getActivity(),"保存图片失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    private void dialog2() {

        final EditText editText = new EditText(view.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("请输入作品的名字");

        builder.setView(editText);
        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = editText.getText().toString();
                System.out.println("1111111111111"+name);
                save();
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

    public void clear(){
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        initImage();

    }
    protected void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("是否保存作品？");
        builder.setTitle("提示");
        builder.setCancelable(false);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog2();
                System.exit(0);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        builder.create().show();

    }


}
