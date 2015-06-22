package com.godream.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import com.godream.provider.Images;
import com.godream.util.BitmapHelper;
import com.godream.util.LogUtil;
import com.godream.util.UtilTools;

/**
*
* 类名称：AsyncImageLoader
* 
* 类描述：异步加载图片控制器
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-24 上午10:05:49
* 
* 备注：
*
*/
public class AsyncImageLoader {
	private static AsyncImageLoader imageLoader;
	private MemoryCache memoryCache;
	private FileCache fileCache;
	private HttpClient defaultClient;
	
	private AsyncImageLoader(Context context) {
		memoryCache = new MemoryCache();
		fileCache = new FileCache(context);
		defaultClient = DefaultClient.getDefaultClientInstance();
	}
	
	public static synchronized AsyncImageLoader getInstance(Context context){
		if(imageLoader == null){
			imageLoader = new AsyncImageLoader(context);
			return imageLoader;
		}
		return imageLoader;
	}
	
	/**
	 *描述：设置ImageView
	 *@param key
	 *@param imageView
	 *创建人：lipeng
	 *创建时间：2013-10-24 下午1:06:49
	 *备注：
	 */
	public void setImageView(String key,ImageView imageView,TextView title,boolean isBusy){
		Bitmap bitmap = null;
		LogUtil.getInstance().i("memoryCache.size:" + memoryCache.getSize());
		//1在缓存中获取
		bitmap = memoryCache.getCache(key);
		
		//2在文件缓存中获取
		if(bitmap == null && !isBusy){
			File file = fileCache.getFile(key);
			if(file != null){
				bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), null);
				memoryCache.putCache(key, bitmap);//文件中存在,放入缓存,优先使用缓存,速度快
			}
		}
		//3网络请求
		if(bitmap == null){
			if(!isBusy)
				new AsyncImageLoaderTask(imageView, key,title).execute();
		}
		
		if(bitmap != null){
			imageView.setImageBitmap(bitmap);
			title.setText("第" + key + "天的风景");
		}
	}
	
	public class AsyncImageLoaderTask extends AsyncTask<Void, Void, Bitmap>{
		private ImageView imageView;
		private String fileName;
		TextView title;
		public AsyncImageLoaderTask(ImageView imageView,String fileName,TextView title) {
			this.imageView = imageView;
			this.fileName = fileName;
			this.title = title;
		}
		@Override
		protected Bitmap doInBackground(Void... params) {
			LogUtil.getInstance().i("启动了" + this.getClass() + ",Thread=" + Thread.currentThread().getId());
			int i = Integer.valueOf(fileName);
			String url = Images.imageUrls[i];
			return getBitmapFromUrl(url);
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if(bitmap != null && imageView != null){
				imageView.setImageBitmap(bitmap);
				title.setText("第" + fileName + "天的风景");
				memoryCache.putCache(fileName, bitmap);
				fileCache.put(fileName, bitmap);
			}
		}
	}
	
	private Bitmap getBitmapFromUrl2(String path){
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(10*1000);
				if(conn.getResponseCode() == 200){
					InputStream is = conn.getInputStream();
					return BitmapFactory.decodeStream(is);
				}
				LogUtil.getInstance().i("res code:" + conn.getResponseCode());
				return null;
				
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}finally{
				if(conn != null)
					conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	private Bitmap getBitmapFromUrl(String url){
//		LogUtil.getInstance().i("url:" + url);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		InputStream in = null;
		try {
			response = defaultClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		LogUtil.getInstance().i("response");
		if(response != null && response.getEntity() != null){
			try {
				LogUtil.getInstance().i("response success");
				in = response.getEntity().getContent();
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
//		Bitmap bmp;
//		BitmapFactory.Options opts = new BitmapFactory.Options(); 
//		byte[] bytes = UtilTools.getBytes(in); 
//		//这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;) 
//		opts.inJustDecodeBounds = true; 
//		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
//		opts.inSampleSize = computeSampleSize(opts, -1, displaypixels); 
//		//end 
//		opts.inJustDecodeBounds = false; 
//		bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
		return BitmapHelper.decodeStream(in);
	}
	
	public void clearCache(){
		memoryCache.clear();
//		fileCache.clean();
	}
	
}












