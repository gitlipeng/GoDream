package com.godream.adapter;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKRoutePlan;
import com.godream.R;

/**
*
* 类名称：DrivingRouteAdapter
* 
* 类描述：驾车路线列表Adapter
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-18 下午5:17:33
* 
* 备注：
*
*/
public class DrivingRouteAdapter extends BaseAdapter{
	private MKDrivingRouteResult result;
	private LayoutInflater inflater;
	private Context ctx;
	
	public DrivingRouteAdapter(Context context, MKDrivingRouteResult result) {
		this.result = result;
		this.inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return result.getNumPlan();
	}

	@Override
	public Object getItem(int arg0) {
		return result.getPlan(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = inflater.inflate(R.layout.route_item_driving, null);
		}
		TextView mDivingTime = (TextView) convertView.findViewById(R.id.drivingtime);
		TextView mDivingDistance = (TextView) convertView.findViewById(R.id.drivingdistance);
		MKRoutePlan routePlan = result.getPlan(position);
		mDivingTime.setText(Math.round(routePlan.getTime()/60d)+"分钟");
		mDivingDistance.setText(Math.round(routePlan.getDistance()/100d)/10d +"公里");
		return convertView;
	}
}
