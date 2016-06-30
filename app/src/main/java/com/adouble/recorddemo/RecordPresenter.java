package com.adouble.recorddemo;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.adouble.recorddemo.record.Mp3Recorder;
import com.adouble.recorddemo.record.OnMaxDurationReached;
import com.adouble.recorddemo.util.TimeUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by double on 16-6-15.
 * Project: WayTone
 */
public class RecordPresenter implements RecordContract.Presenter, OnMaxDurationReached {


    public static final int STATE_RECORD_PREPARE = 0;
    public static final int STATE_RECORD_RECORDING = 1;
    public static final int STATE_RECORD_PAUSE = 2;
    public static final int STATE_RECORD_STOP = 3;

    public int state = STATE_RECORD_PREPARE;

    private final RecordContract.View mView;
    private long mRecordName;
    private boolean hasRecord;

    /**
     * 音频录音的总长度
     */
    private static int voiceLength;

    /**
     * 最大录音长度
     */
    private static final int MAX_LENGTH = 300 * 1000;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            return false;
        }
    });

    private Runnable runnableUp;

    private Mp3Recorder mMp3Recorder;


    public RecordPresenter(RecordContract.View view) {
        mView = view;
        mView.setPresent(this);
    }

    @Override
    public void back() {
        if (mMp3Recorder != null) {
            mView.showBackPrompt();
        } else {
            mView.back();
        }
    }

    @Override
    public void startRecord() {
        state = STATE_RECORD_RECORDING;
gi        Logger.d("开始录音");
        mView.showRecording();

        if (mMp3Recorder == null) {
            mMp3Recorder = new Mp3Recorder();
            mMp3Recorder.setmMaxDuration(300);//60s
            mRecordName = System.currentTimeMillis();
            mMp3Recorder.setOutputFile(Environment.getExternalStorageDirectory() + "/" + mRecordName + ".mp3");
            mMp3Recorder.setOnMaxDurationReachedListener(this);

            mMp3Recorder.prepare();
            mMp3Recorder.startRecording();
            Logger.d("startRecord 地址：" + Environment.getExternalStorageDirectory() + "/" + mRecordName + ".mp3");
            timing();
        } else {

        }
    }

    @Override
    public void resumeRecord() {
        state = STATE_RECORD_RECORDING;
        mView.showRecording();
        mMp3Recorder.resume();
        timing();
    }

    /**
     * 暂停录音
     */
    @Override
    public void pauseRecord() {
        state = STATE_RECORD_PAUSE;
        mView.showPause();

        stopTime();

        if (mMp3Recorder.getRecorderState() == Mp3Recorder.State.RECORDING) {
            mMp3Recorder.pause();
            Logger.d("pauseRecord 暂停录音");
        }

    }

    @Override
    public void stopRecord() {
        state = STATE_RECORD_STOP;
        mView.showStop();

        int state = mMp3Recorder.getRecorderState();
        if (state == Mp3Recorder.State.RECORDING || state == Mp3Recorder.State.PAUSED) {
            mMp3Recorder.stop();
            Logger.d("停止录音");
            hasRecord = true;
            stopTime();
            ok();
        }
    }

    @Override
    public void del() {
        state = STATE_RECORD_PREPARE;
        mView.viewInit();
        hasRecord = false;

        //停止播放音频
        mMp3Recorder = null;
        voiceLength = 0;
        Logger.d("清空");

        stopTime();
    }

    @Override
    public void ok() {

    }

    private void stopTime() {
        if (handler != null && runnableUp != null) {
            handler.removeCallbacks(runnableUp);
            runnableUp = null;
        }
    }

    @Override
    public void start() {

    }

    /**
     * 计时功能
     */
    private void timing() {
        runnableUp = new Runnable() {
            @Override
            public void run() {
                voiceLength += 100;
                if (voiceLength >= (MAX_LENGTH - 10 * 1000)) {
                    mView.showLimit();
                }
                if (voiceLength == 0) {
                    stopTime();
                    pauseRecord();
                } else {
                    mView.setTime(TimeUtils.convertMilliSecondToMinute2(voiceLength));
                    handler.postDelayed(this, 100);
                }
            }
        };
        handler.postDelayed(runnableUp, 100);
    }


    @Override
    public void onMaxDurationReached() {
        mView.showLimit();
        mMp3Recorder.stop();
    }


}
