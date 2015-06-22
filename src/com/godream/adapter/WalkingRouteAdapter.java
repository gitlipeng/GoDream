package com.godream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.MKRoutePlan;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.godream.R;

/**
*
* 类名称：WalkingRouteAdapter
* 
* 类描述：步行线路列表Adapter
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-18 下午5:16:42
* 
* 备注：
*
*/
public class WalkingRouteAdapter extends BaseAdapter{
	private MKWalkingRouteResult result;
	private LayoutInflater inflater;
	private Context ctx;
	
	public WalkingRouteAdapter(Context context, MKWalkingRouteResult result) {
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
