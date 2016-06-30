package com.adouble.recorddemo;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adouble.recorddemo.event.VoiceEvent;
import com.adouble.recorddemo.util.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener, RecordContract.View {

    private static final String RECORD_PATH = "record_path";
    private static final int RESULT_CODE = 0;
    @BindView(R.id.media_record_img)
    ImageView mRecordImg;
    @BindView(R.id.media_record_time)
    TextView mTime;
    @BindView(R.id.media_record_start_pause)
    ImageView mStartPause;
    @BindView(R.id.media_record_stop)
    ImageView mStop;
    @BindView(R.id.record_del)
    FloatingActionButton mRecordDel;

    private RecordContract.Presenter mPresent;
    private Dialog mMultipleDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_record);
        ButterKnife.bind(this);

        new RecordPresenter(this);


        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mPresent.del();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresent.back();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void showRecording() {
        mRecordImg.setSelected(true);
        mStartPause.setSelected(true);
        mStop.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBackPrompt() {

    }

    @Override
    public void setTime(String s) {
        mTime.setText(s);
    }

    @Override
    public void showLimit() {
        mTime.setTextColor(Color.RED);
    }

    @Override
    public void viewInit() {
        mTime.setText("00:00:00");
        mRecordImg.setSelected(false);
        mStartPause.setVisibility(View.VISIBLE);
        mStartPause.setSelected(false);
        mRecordImg.setVisibility(View.VISIBLE);
        mStop.setVisibility(View.GONE);

        mRecordImg.getDrawable().setLevel(0);
    }

    @Override
    public void showStop() {
        Logger.d("showStop");
        mStartPause.setSelected(false);
        mStartPause.setVisibility(View.GONE);
        mRecordImg.setSelected(true);
        mStop.setVisibility(View.VISIBLE);
    }


    @Override
    public void showNoRecord() {
        ToastUtil.showToast(this, "没有录音");
    }

    @Override
    public void showPause() {
        Logger.d("showStop");
        mStartPause.setSelected(false);
        mRecordImg.setSelected(true);
        mStop.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setVoiceLev(VoiceEvent voiceEvent) {
        int level = -1500 + 6000 * (int) (voiceEvent.lev) / 100;
        mRecordImg.getDrawable().setLevel(level * 3);
    }

    @Override
    public void setPresent(RecordContract.Presenter present) {
        mPresent = present;
    }

    @OnClick({R.id.media_record_img, R.id.media_record_start_pause, R.id.media_record_stop, R.id.record_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.media_record_img:
                break;
            case R.id.media_record_start_pause:
                switch (((RecordPresenter) mPresent).state) {
                    case RecordPresenter.STATE_RECORD_PREPARE:
                        mPresent.startRecord();
                        break;
                    case RecordPresenter.STATE_RECORD_PAUSE:
                        mPresent.resumeRecord();
                        break;
                    case RecordPresenter.STATE_RECORD_RECORDING:
                        mPresent.pauseRecord();
                        break;
                }
                break;
            case R.id.media_record_stop:
                mPresent.stopRecord();
                break;
            case R.id.record_del:
                mPresent.del();
                break;
        }
    }


}
