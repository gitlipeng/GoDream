package com.godream.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.godream.util.LogUtil;

import android.graphics.Bitmap;

public class ImageCache {
	private static ImageCache imageCache;
	private static HashMap<String,Bitmap> mFirstLevelCache;
	private static final int MAX_CAPACITY = 10;
	private static ConcurrentHashMap<String,SoftReference<Bitmap>> mSecondLevelCache;
	
	public synchronized static ImageCache newInstance(){
		if(imageCache == null){
			imageCache = new ImageCache();
		}
		return imageCache;
	}
	
	private ImageCache(){
		// true表示按照最近访问量的高低排序，false则表示按照插入顺序排序
		mFirstLevelCache = new LinkedHashMap<String, Bitmap>(MAX_CAPACITY/2, 0.75f, true){
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean removeEldestEntry(
					java.util.Map.Entry<String, Bitmap> eldest) {
				if(size() > MAX_CAPACITY){// 当超过一级缓存阈值的时候，将老的值从一级缓存搬到二级缓存
					LogUtil.getInstance().i("一级缓存到达上限，大小：" + mFirstLevelCache.size() + ",key:"+ eldest.getKey() + "被转入二级缓存");
					mSecondLevelCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
					return true;
				}
				return false;
			}
		};
		// 二级缓存，采用的是软应用，在内存不足的时候软会被回收，避免OOM
		mSecondLevelCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(MAX_CAPACITY / 2);
	}
	
	public Bitmap getBitmap(String key){
		Bitmap bitmap = null;
		bitmap = getFromFirstLevelCache(key);
		if(bitmap != null){
			return bitmap;
		}
		bitmap = getFromSecondLevelCache(key);
		return bitmap;
	}
	
	
	/**
	 * 从一级缓存中拿
	 * @param url
	 */
	private Bitmap getFromFirstLevelCache(String key){
		Bitmap bitmap = null;
		synchronized (mFirstLevelCache) {
			bitmap = mFirstLevelCache.get(key);
			if(bitmap != null){
				// 将最近访问的元素放到链的头部，提高下一次访问该元素的检索速度（LRU算法）
				mFirstLevelCache.remove(key);
				mFirstLevelCache.put(key, bitmap);
				LogUtil.getInstance().i("取自一级缓存");
			}
		}
		return bitmap;
	}
	
	/**
	 * 从二级缓存中拿
	 * @param url
	 * @return
	 */
	private Bitmap getFromSecondLevelCache(String key){
		Bitmap bitmap = null;
		SoftReference<Bitmap> softReference = mSecondLevelCache.get(key);
		if(softReference != null){
			bitmap = softReference.get();
			if(bitmap == null){// 由于内存吃紧，软引用已经被gc回收了
				mSecondLevelCache.remove(key);
			}else{
				LogUtil.getInstance().i("取自二级缓存");
			}
		}
		return bitmap;
	}
	
	public void putCache(String key,Bitmap value){
		if(key == null || value == null){
			return;
		}
		synchronized (mFirstLevelCache) {
			mFirstLevelCache.put(key, value);
		}
	}
}

























