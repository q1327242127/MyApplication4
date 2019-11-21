package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class FightActivity extends AppCompatActivity {

    private TextView mw_hp;
    private ProgressBar mw_gj;
    private TextView yz_hp;
    private ProgressBar yz_gj;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                fight();
            } else if (msg.what == 1) {//魔王攻击
                mw_h -= 20;
                mw_hp.setText(mw_h + "/100");
                if (mw_h <= 0) {
                    message_fight.setText("勇者胜利!");
                    message_fight.setTextColor(Color.parseColor("#ff0000"));
                    timer.cancel();
                    handler.sendEmptyMessageDelayed(3,1000);
                }

            } else if (msg.what == 2) {//勇者攻击
                yz_h -= 10;
                yz_hp.setText(yz_h + "/30");
                if (yz_h <= 0) {
                    message_fight.setText("被魔王杀死了，游戏结束!");
                    timer.cancel();
                    handler.sendEmptyMessageDelayed(3,1000);
                }
            } else if (msg.what == -1) {
                message_fight.setText("战斗中。。。");
            }else if(msg.what == 3){
                finish();
            }
        }
    };
    Timer timer;
    int yz_t = 0;
    int mw_t = 0;
    int yz_h = 30;
    int mw_h = 100;
    private TextView message_fight;

    private void fight() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(-1);
                yz_t += 2;
                if (yz_t == 100) {
                    yz_t = 0;
                    handler.sendEmptyMessage(1);
                }
                mw_t += 1;
                if (mw_t == 100) {
                    mw_t = 0;
                    handler.sendEmptyMessage(2);
                }
                yz_gj.setProgress(yz_t);
                mw_gj.setProgress(mw_t);
            }
        }, 0, 50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!= null){
            timer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        initView();
        handler.sendEmptyMessageDelayed(0, 0);
    }

    private void initView() {
        timer = new Timer();
        mw_hp = (TextView) findViewById(R.id.mw_hp);
        mw_gj = (ProgressBar) findViewById(R.id.mw_gj);
        yz_hp = (TextView) findViewById(R.id.yz_hp);
        yz_gj = (ProgressBar) findViewById(R.id.yz_gj);
        message_fight = (TextView) findViewById(R.id.message_fight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
