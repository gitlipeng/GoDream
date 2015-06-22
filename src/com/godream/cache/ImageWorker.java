package com.godream.cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import com.godream.provider.Images;
import com.godream.service.DefaultClient;
import com.godream.service.AsyncImageLoader.AsyncImageLoaderTask;
import com.godream.util.BitmapHelper;
import com.godream.util.LogUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageWorker {
	private static ImageWorker worker;
	private ImageCache imageCache;
	private DiskCache diskCache;
	private Context context;
	private HttpClient defaultClient;
	public synchronized static ImageWorker getInstance(Context ctx){
		if(worker == null){
			worker = new ImageWorker(ctx);
		}
		return worker;
	}
	private ImageWorker(Context ctx){
		context = ctx;
		imageCache = ImageCache.newInstance();
		diskCache = DiskCache.newInstance(ctx);
		defaultClient = DefaultClient.getDefaultClientInstance();
	};
	
	public void setImageView(String key,ImageView imageView,TextView title,boolean isBusy,int reqWidth,int reqHeight){
		Bitmap bitmap = null;
		//1在缓存中获取
		bitmap = imageCache.getBitmap(key);
		
		//2网络请求
		if(bitmap == null){
			if(!isBusy)
				new AsyncImageLoaderTask(imageView, key,title,reqWidth,reqHeight).execute();
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
		private int reqWidth;
		private int reqHeight;
		public AsyncImageLoaderTask(ImageView imageView,String fileName,TextView title,int reqWidth,int reqHeight) {
			this.imageView = imageView;
			this.fileName = fileName;
			this.title = title;
			this.reqHeight = reqHeight;
			this.reqWidth = reqWidth;
		}
		@Override
		protected Bitmap doInBackground(Void... params) {
			//2在文件缓存中获取
			Bitmap bitmap = null;
			bitmap = diskCache.getBitmap(fileName, reqWidth, reqHeight);
			if(bitmap != null){
				imageCache.putCache(fileName, bitmap);
				LogUtil.getInstance().i("取自文件");
				return bitmap;
			}
			//2.网络请求
			LogUtil.getInstance().i("启动了" + this.getClass() + ",Thread=" + Thread.currentThread().getId());
			int i = Integer.valueOf(fileName);
			String url = Images.imageUrls[i];
			bitmap =  getBitmapFromUrl(url);
			if(bitmap != null){
				imageCache.putCache(fileName, bitmap);
				diskCache.put(fileName, bitmap);
				LogUtil.getInstance().i("取自网络");
			}
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if(bitmap != null && imageView != null){
				imageView.setImageBitmap(bitmap);
				title.setText("第" + fileName + "天的风景");
			}
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
//		memoryCache.clear();
//		fileCache.clean();
	}
	
	
}
