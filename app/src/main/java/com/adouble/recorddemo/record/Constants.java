package com.adouble.recorddemo.record;

import android.os.Environment;

public class Constants {

	/**
	 * 获取音频保存目录
	 */
	public static String WAYTONE_RECORD = Environment.getExternalStorageDirectory() + "/waytone_record";
	public static String WAYTONE_CONFIG = Environment.getExternalStorageDirectory() + "/waytone_config";
	public static String WAYTONE_PHOTO = Environment.getExternalStorageDirectory() + "/waytone_photo";
	public static String WAYTONE_VIDEO = Environment.getExternalStorageDirectory() + "/waytone_video";
	public static String WAYTONE_CONTACTS_HEADIMG = Environment.getExternalStorageDirectory() + "/waytone_config/headimg";

}
