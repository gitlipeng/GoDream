package com.godream.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.godream.R;
import com.godream.bean.B2C_Comm_Bean;
import com.godream.bean.TrivalNote;

public class B2C_Comm_Adapter extends BaseAdapter{
	private List<B2C_Comm_Bean> list;
	private LayoutInflater inflater;
	private Context ctx;
	public static int pressedPosition = -1;
	
	public B2C_Comm_Adapter(Context context, List<B2C_Comm_Bean> list) {
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
		ViewHolder holder; 
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.layout_b2c_item_comment, null);
			holder.imageCat = (ImageView) convertView.findViewById(R.id.imageCat);
			holder.imageArrow = (ImageView) convertView.findViewById(R.id.imageArrow);
			holder.catName = (TextView) convertView.findViewById(R.id.catName);
			holder.catDetail =  (TextView) convertView.findViewById(R.id.catDetail);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		B2C_Comm_Bean bean = list.get(position);
		holder.catName.setText(bean.getCatName());
		holder.catDetail.setText(bean.getCatDetail());
		if(pressedPosition != -1 && pressedPosition == position){
			holder.imageArrow.setVisibility(View.VISIBLE);
			holder.imageCat.setImageResource(bean.getImageId_Pressed());
		}else{
			holder.imageArrow.setVisibility(View.INVISIBLE);
			holder.imageCat.setImageResource(bean.getImageId());
		}
		
		return convertView;
	}
	
	final class ViewHolder{
		RelativeLayout rl_menu_one;
		ImageView imageCat;
		ImageView imageArrow;
		RelativeLayout itemParent;
		TextView catName;
		TextView catDetail;
	}
}
