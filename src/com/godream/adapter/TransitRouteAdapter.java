package com.godream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.MKTransitRoutePlan;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.godream.R;

/**
*
* 类名称：TransitRouteAdapter
* 
* 类描述：公交线路列表Adapter
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-18 下午5:16:19
* 
* 备注：
*
*/
public class TransitRouteAdapter extends BaseAdapter{
	private MKTransitRouteResult result;
	private LayoutInflater inflater;
	private Context ctx;
	
	public TransitRouteAdapter(Context context, MKTransitRouteResult result) {
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
		ViewHolder holder; 
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.route_item_transit, null);
			holder.mTime = (TextView) convertView.findViewById(R.id.time);
			holder.mComment =  (TextView) convertView.findViewById(R.id.comment);
			holder.mDistance = (TextView) convertView.findViewById(R.id.distance);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		MKTransitRoutePlan routePlan = result.getPlan(position);
		holder.mTime.setText(Math.round(routePlan.getTime()/60d)+"分钟");
		holder.mComment.setText(routePlan.getContent());
		holder.mDistance.setText(Math.round(routePlan.getDistance()/100d)/10d +"公里");
		return convertView;
	}
	final class ViewHolder{
		TextView mTime;
		TextView mComment;
		TextView mDistance;
	}
}
