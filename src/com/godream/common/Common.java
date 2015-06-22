package com.godream.common;

import android.os.Environment;

public class Common {
	public static int _ScreenHeight;
	public static int _ScreenWidth;
	public static float _Density;
	static {
		_Density = 240.0F;
	}
	public static final String DB_NAME = "Com.db";
	public static final String PATH_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "GoDream";
	public static final String DB_DIR = PATH_CACHE + "/" + "Com.db";
	public static final String CACHE_DIR = PATH_CACHE + "/" + "image";
}
