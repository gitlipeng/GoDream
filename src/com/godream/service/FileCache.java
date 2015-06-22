package com.godream.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import com.godream.common.Common;
import com.godream.common.Constant;
import com.godream.util.LogUtil;

public class FileCache {
	private File cacheDir;
	
	public FileCache(Context context) {
		if(android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED)){
			cacheDir = new File(Common.CACHE_DIR);
		}else{
			cacheDir = context.getCacheDir();
		}
		
		if(!cacheDir.exists()){
			cacheDir.mkdirs();
		}
	}
	
	public File getFile(String fileName){
		File f =new File(cacheDir,fileName+".jpg");
		if(f.exists()){
			LogUtil.getInstance().i("the file you wanted exists " + f.getAbsolutePath());
			return f;
		}else{
			LogUtil.getInstance().i("the file you wanted does not exists: " + f.getAbsolutePath());
		}
		return null;
	}
	
	public void put(String fileName,Bitmap value){
		File f = new File(cacheDir,fileName + ".jpg");
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(saveBitmap(f,value)){
			LogUtil.getInstance().i("Save file to sdcard successfully!");
		}else{
			LogUtil.getInstance().i("Save file to sdcard failed!");
		}
		
	}
	
	public File createFile(String key) {
		File f = new File(cacheDir, key);
		if(!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return f;
	}
	
	public void clean(){
		File[] files = cacheDir.listFiles();
		for(int i = 6; i < files.length; i++){
			File f = files[i];
			f.delete();
		}
	}
	
	private boolean saveBitmap(File file,Bitmap bitmap){
		if(file == null || bitmap == null){
			return false;
		}
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			return bitmap.compress(CompressFormat.JPEG, 100, bos);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}












