package com.godream.service;

import java.util.WeakHashMap;

import com.godream.util.LogUtil;

import android.graphics.Bitmap;


public class MemoryCache {
	private WeakHashMap<String, Bitmap> cache = new WeakHashMap<String, Bitmap>();
	
	public Bitmap getCache(String key){
		if(key != null && !"".equals(key)){
			Bitmap bitmap = cache.get(key);
			if(bitmap == null){
				LogUtil.getInstance().i("the memorycache you wanted does not exists ");
			}else{
				LogUtil.getInstance().i("the memorycache you wanted exists ");
			}
			return bitmap;
		}
		return null;
	}
	
	public void putCache(String key,Bitmap value){
		LogUtil.getInstance().i("memorycache 大小:" + cache.size());
		if(key != null && !"".equals(key) && value != null){
			LogUtil.getInstance().i("put memorycache succelly! ");
			cache.put(key, value);
		}
	}
	
	public int getSize(){
		return cache.size(); 
	}
	
	public void clear(){
		cache.clear();
	}
	
}
