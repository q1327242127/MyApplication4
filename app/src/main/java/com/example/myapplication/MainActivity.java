package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.map.MapInit;
import com.example.myapplication.map.MapView;

import java.io.InputStream;
import java.util.Random;

public class MainActivity extends Activity {

    MapView view;
    public RelativeLayout relativeLayout;
    TextView textView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Display display = getWindowManager().getDefaultDisplay();
        Log.i("view", "height:" + display.getHeight());
        Log.i("view", "width:" + display.getWidth());
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.i("view", "height" + displayMetrics.heightPixels);
        Log.i("view", "width" + displayMetrics.widthPixels);
        // 全屏显示窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new MapView(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
        setContentView(view);
        Button button = new Button(this);
        button.setBackgroundResource(R.drawable.mybutton);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(((int) view.smallCenterR) * 4, ((int) view.smallCenterR) * 4);
        layoutParams.leftMargin = displayMetrics.widthPixels / 5 * 4;
        layoutParams.topMargin = displayMetrics.heightPixels / 3 * 2;

        addContentView(button, layoutParams);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view.btnWeapon) {
                    view.moveWeapon = 0;
                    view.btnWeapon = true;
                }
            }
        });

        relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.dlog_dailog, null);
        FrameLayout.LayoutParams relativeParams = new FrameLayout.LayoutParams(displayMetrics.widthPixels, displayMetrics.heightPixels /3);
        relativeParams.topMargin = displayMetrics.heightPixels / 3 * 2;
        addContentView(relativeLayout, relativeParams);
//        relativeLayout.setVisibility(View.GONE);
        textView = relativeLayout.findViewById(R.id.dlog_content);
        textView.setText("请寻找2个隐藏空间");
        relativeLayout.findViewById(R.id.dlog_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout.setVisibility(View.GONE);
            }
        });

         mediaPlayer = MediaPlayer.create(this,R.raw.walk);
        mediaPlayer.setLooping(true);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public TextView getTextView() {
        return textView;
    }
}
