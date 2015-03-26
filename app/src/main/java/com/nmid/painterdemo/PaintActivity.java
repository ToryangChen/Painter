package com.nmid.painterdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Toryang on 2015/3/25.
 */
public class PaintActivity extends ActionBarActivity implements View.OnClickListener{
    private ImageView imageView;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private Button penButton,eraserButton,clearButton,invisibleButton;
    private Spinner colorChoice;
    private android.support.v7.app.ActionBar actionBar;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        initView();
        initButton();

    }
    private void initButton(){
        penButton = (Button)findViewById(R.id.pen_button);
        eraserButton = (Button)findViewById(R.id.eraser_button);
        clearButton = (Button)findViewById(R.id.clearButton);
        invisibleButton = (Button)findViewById(R.id.invisibleButton);
        colorChoice = (Spinner)findViewById(R.id.color);


        penButton.setOnClickListener(this);
        eraserButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        invisibleButton.setOnClickListener(this);
    }
    private void initView(){
        this.imageView = (ImageView)findViewById(R.id.paintView);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        System.out.println("heigth2 : " + dm.heightPixels);
        System.out.println("width2 : " + dm.widthPixels);
        int VIEW_WIDTH = dm.widthPixels;
        int VIEW_HEIGHT = dm.heightPixels;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pen_button:
                Toast.makeText(this,"pen",Toast.LENGTH_SHORT).show();
                paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setStrokeWidth(5);
                break;
            case R.id.eraser_button:
                Toast.makeText(this,"eraser",Toast.LENGTH_SHORT).show();
                paint = new Paint();
                paint.setColor(Color.WHITE);
                paint.setStrokeWidth(100);
                break;
            case R.id.color:
                Toast.makeText(this,"color",Toast.LENGTH_SHORT).show();
                break;
            case R.id.clearButton:
                clear();
                break;
            case R.id.invisibleButton:
                invisible();
                break;
        }

    }

    private void invisible(){
        actionBar = getSupportActionBar();

        if(flag == true){
            penButton.setVisibility(Button.INVISIBLE);
            eraserButton.setVisibility(Button.INVISIBLE);
            colorChoice.setVisibility(Button.INVISIBLE);
            clearButton.setVisibility(Button.INVISIBLE);
            actionBar.hide();
            flag = !flag;
        }else{
            penButton.setVisibility(Button.VISIBLE);
            eraserButton.setVisibility(Button.VISIBLE);
            colorChoice.setVisibility(Button.VISIBLE);
            clearButton.setVisibility(Button.VISIBLE);
            actionBar.show();
            flag = !flag;
        }

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

            File file = new File(path,System.currentTimeMillis()+".jpg");
            OutputStream stream = new FileOutputStream(file);
            baseBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.close();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            sendBroadcast(intent);

            Toast.makeText(PaintActivity.this, "保存图片成功", Toast.LENGTH_SHORT).show();
            finish();
        } catch (FileNotFoundException e) {
            Toast.makeText(PaintActivity.this,"保存图片失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(PaintActivity.this,"保存图片失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    public void clear(){
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        initView();

    }
    protected void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PaintActivity.this);
        builder.setMessage("是否保存作品？");
        builder.setTitle("提示");
        builder.setCancelable(false);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save();
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("否",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                System.exit(0);
            }
        });
        builder.create().show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            dialog();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_paint, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                save();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
