package com.godream.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.godream.R;
import com.godream.bean.B2C_Cat2_Bean;
import com.godream.bean.B2C_Comm_Bean;
import com.godream.util.LogUtil;

public class B2C_Cat2_Adapter extends BaseAdapter{
	private List<B2C_Cat2_Bean> list;
	private LayoutInflater inflater;
	private Context ctx;
	public static int pressedPosition = -1;
	
	public B2C_Cat2_Adapter(Context context, List<B2C_Cat2_Bean> list) {
		this.ctx = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
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
		LogUtil.getInstance().i("getView position:" + position);
		ViewHolder holder; 
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_b2c_item_cat2, null);
			holder.catName = (TextView) convertView.findViewById(R.id.catName);
			holder.imageArrow = (ImageView) convertView.findViewById(R.id.imageArrow);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		B2C_Cat2_Bean bean = list.get(position);
		holder.catName.setText(bean.getCatName());
		if(pressedPosition != -1 && pressedPosition == position){
			holder.imageArrow.setVisibility(View.VISIBLE);
		}else{
			holder.imageArrow.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	final class ViewHolder{
		ImageView imageArrow;
		TextView catName;
	}
	
}
