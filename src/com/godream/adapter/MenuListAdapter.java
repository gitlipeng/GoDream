package com.godream.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.godream.R;
import com.godream.bean.MenuBean;
import com.godream.common.Common;
import com.godream.util.DPIUtil;

public class MenuListAdapter extends BaseAdapter {

	private List<MenuBean> list;

	private LayoutInflater inflater;

	private Context ctx;
	
	private int allHeight;

	public MenuListAdapter(Context context, List<MenuBean> list,int allHeight) {
		this.ctx = context;
		this.list = list;
		this.allHeight = allHeight;
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
			convertView = inflater.inflate(R.layout.menu_list_item, null);
			holder.mImage = (ImageView) convertView.findViewById(R.id.iv_menu_item);
			holder.mTextView =  (TextView) convertView.findViewById(R.id.tv_menu_item);
			
			RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)holder.mTextView.getLayoutParams();
			int height = Common._ScreenHeight - DPIUtil.dip2px(50.0F);
		    localLayoutParams.height = allHeight/getCount();
		    holder.mTextView.setLayoutParams(localLayoutParams);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		MenuBean bean = list.get(position);
		holder.mImage.setBackgroundResource(bean.getImageId());
		holder.mTextView.setText(bean.getContent());
		
		return convertView;
	}

	final class ViewHolder {

		ImageView mImage;

		TextView mTextView;
	}
}
