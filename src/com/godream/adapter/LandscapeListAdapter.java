package com.godream.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.godream.R;
import com.godream.bean.Landscape;
import com.godream.cache.ImageWorker;
import com.godream.service.AsyncImageLoader;

public class LandscapeListAdapter extends BaseAdapter{
	private List<Landscape> list;
	private LayoutInflater inflater;
	private Context ctx;
	private AsyncImageLoader imageLoader;
	private ImageWorker worker;
	public boolean isBusy;
	private Bitmap defaultBitMap;
	
	public LandscapeListAdapter(Context context, List<Landscape> list) {
		this.ctx = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
		imageLoader = AsyncImageLoader.getInstance(context);
		worker = ImageWorker.getInstance(context);
		defaultBitMap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.image_default);
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder; 
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.linelist_item, null);
			holder.mImage = (ImageView) convertView.findViewById(R.id.iv_linelist_item);
			holder.mDays =  (TextView) convertView.findViewById(R.id.tv_linelist_item_day);
			holder.mTitle = (TextView) convertView.findViewById(R.id.tv_linelist_item_title);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		Landscape landscape = list.get(position);
		holder.mImage.setImageBitmap(defaultBitMap);//设置默认图片
		holder.mDays.setText(String.valueOf(landscape.getDays()));
		holder.mTitle.setText(landscape.getTitle());
		
		//请求ImageView的图片
//		imageLoader.setImageView(String.valueOf(landscape.getDays()), holder.mImage,holder.mTitle,isBusy);
		worker.setImageView(String.valueOf(landscape.getDays()), holder.mImage,holder.mTitle,isBusy,Integer.MAX_VALUE,Integer.MAX_VALUE);
		return convertView;
	}
	final class ViewHolder{
		ImageView mImage;
		TextView mDays;
		TextView mTitle;
	}
}
