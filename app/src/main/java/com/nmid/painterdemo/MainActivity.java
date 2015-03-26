package com.nmid.painterdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener
{

    private Button guessbutton,paintbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        guessbutton = (Button)findViewById(R.id.guess_button);
        paintbutton = (Button)findViewById(R.id.paint_button);

        guessbutton.setOnClickListener(this);
        paintbutton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.guess_button:
                Intent guessIntent = new Intent(this,GuessActivity.class);
                guessIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(guessIntent);
                break;
            case R.id.paint_button:
                Intent paintIntent = new Intent(this,PaintActivity.class);
                paintIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(paintIntent);
                break;
        }

    }
    protected void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("雏鹰计划三期作品“大画师”！");
        builder.setTitle("关于我们");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_about:
                dialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
