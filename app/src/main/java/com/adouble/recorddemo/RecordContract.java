package com.adouble.recorddemo;

/**
 * Created by double on 16-6-15.
 * Project: WayTone
 */
public class RecordContract {
    interface View extends BaseView<Presenter> {

        void back();

        void showRecording();

        void showBackPrompt();

        void setTime(String s);

        void showLimit();

        void viewInit();

        void showStop();

        void showNoRecord();

        void showPause();

    }

    interface Presenter extends BasePresenter {

        void back();

        void stopRecord();

        void startRecord();

        void resumeRecord();

        void pauseRecord();

        void del();

        void ok();
    }
}
