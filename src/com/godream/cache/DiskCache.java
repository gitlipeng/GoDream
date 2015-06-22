package com.godream.cache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.godream.common.Common;
import com.godream.common.Constant;
import com.godream.util.LogUtil;

/**
 * 从sd卡读取缓存
 * 
 * @author lipeng
 *
 *
 */
public class DiskCache {
	private File cacheDir;
	private static DiskCache diskCache;
	
	public synchronized static DiskCache newInstance(Context ctx){
		if(diskCache == null){
			diskCache = new DiskCache(ctx);
		}
		return diskCache;
	}
	private DiskCache(Context context) {
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
	
	public Bitmap getBitmap(String fileName,int reqWidth,int reqHeight){
		File f =new File(cacheDir,fileName+".jpg");
		if(f.exists()){
			LogUtil.getInstance().i("the file you wanted exists " + f.getAbsolutePath());
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(f.getAbsolutePath(), options);
			
			options.inSampleSize = calculateInSampleSize(options,400,400);
			options.inJustDecodeBounds = false;
			Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
			return bitmap;
		}else{
			LogUtil.getInstance().i("the file you wanted does not exists: " + f.getAbsolutePath());
			return null;
		}
//		File[] files = cacheDir.listFiles();
//		String name = files[Integer.valueOf(fileName)].getAbsolutePath();
//		// First decode with inJustDecodeBounds=true to check dimensions
//		final BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		BitmapFactory.decodeFile(name, options);
//		
//		options.inSampleSize = calculateInSampleSize(options,400,400);
//		options.inJustDecodeBounds = false;
//		Bitmap bitmap = BitmapFactory.decodeFile(name, options);
//		return bitmap;
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
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
			boolean rel = bitmap.compress(CompressFormat.JPEG, 100, bos);
			bos.close();
			return rel;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
