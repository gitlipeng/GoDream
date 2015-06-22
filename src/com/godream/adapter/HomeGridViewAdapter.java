package com.godream.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.godream.R;
import com.godream.bean.HomeGridItem;

public class HomeGridViewAdapter extends BaseAdapter{
	private ArrayList<HomeGridItem> gridItemList;
	private LayoutInflater inflater;
	public HomeGridViewAdapter(Context context,ArrayList<HomeGridItem> gridItemList) {
		this.gridItemList = gridItemList;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return gridItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return gridItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View gridItem = inflater.inflate(R.layout.homegrid_item, null);
		ImageView image = (ImageView) gridItem.findViewById(R.id.item_image);
		TextView text = (TextView) gridItem.findViewById(R.id.item_name);
		HomeGridItem item = gridItemList.get(position);
		image.setBackgroundResource(item.getImageId());
		text.setText(item.getName());
		return gridItem;
	}

}
