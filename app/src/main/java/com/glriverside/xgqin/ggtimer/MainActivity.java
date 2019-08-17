package com.glriverside.xgqin.ggtimer;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibPlayPause;
    private ImageButton ibTick;
    private ImageButton ibReset;

    private ListView lvTimeLog;
    private TextView tvTimerNumber;
    private TextView tvInterval;

    private List timeLogList;

    private TimerLogAdapter adapter;

    private boolean timerStatus = false;

    private static final int TIMER = 1000;

    private Long timestamp = 0L;
    private Long interval = 0L;

    private SoundPool mSoundPool;
    private int mSoundId;
    private int mStreamId = -1;

    private Timer mTimer;

    private TimerTask mTimerTask;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIMER:
                    timestamp += 10;
                    interval += 10;
                    tvTimerNumber.setText(TimeUtils.intervalToString(timestamp));
                    tvInterval.setText(TimeUtils.intervalToString(interval));
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ib_play_pause:
                    timerStatus = !timerStatus;
                    if (timerStatus) {
                        ibPlayPause.setBackground(getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
                        startTimer();
                    } else {
                        ibPlayPause.setBackground(getDrawable(R.drawable.ic_play_circle_outline_black_24dp));
                        stopTimer();
                    }
                    ibReset.setEnabled(!timerStatus);
                    ibTick.setEnabled(timerStatus);
                    break;

                case R.id.ib_tick:
                    adapter.insert(new TimerLog(timestamp, interval), 0);
                    adapter.notifyDataSetChanged();

                    interval = 0L;
                    tvInterval.setText(TimeUtils.intervalToString(interval));
                    break;

                case R.id.ib_reset:
                    ibReset.setEnabled(false);
                    timestamp = 0L;
                    interval = 0L;
                    tvTimerNumber.setText(TimeUtils.intervalToString(timestamp));
                    tvInterval.setText(TimeUtils.intervalToString(interval));
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {
        timeLogList = new ArrayList<TimerLog>();
        adapter = new TimerLogAdapter(MainActivity.this, R.layout.list_item, timeLogList);
        lvTimeLog.setAdapter(adapter);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        mSoundId = mSoundPool.load(MainActivity.this, R.raw.tick, 1);
    }

    private void initView() {
        ibPlayPause = findViewById(R.id.ib_play_pause);
        ibTick = findViewById(R.id.ib_tick);
        ibReset = findViewById(R.id.ib_reset);
        lvTimeLog = findViewById(R.id.lv_timer_log);
        tvTimerNumber = findViewById(R.id.tv_timer);
        tvInterval = findViewById(R.id.tv_interval);

        ibPlayPause.setOnClickListener(clickListener);
        ibTick.setOnClickListener(clickListener);
        ibReset.setOnClickListener(clickListener);

        ibTick.setEnabled(timerStatus);
    }

    private void startTimer() {
        mTimer = new Timer();

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(TIMER);
            }
        };
        mTimer.schedule(mTimerTask, 0, 10);

        if (mStreamId == -1) {
            mStreamId = mSoundPool.play(mSoundId, 50, 50, 1, -1, 1);
        } else {
            mSoundPool.resume(mStreamId);
        }
    }

    private void stopTimer() {
        mTimer.cancel();
        mTimerTask.cancel();
        mSoundPool.pause(mStreamId);
    }
}
