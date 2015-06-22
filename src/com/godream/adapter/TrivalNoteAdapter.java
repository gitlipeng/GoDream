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
import com.godream.bean.TrivalNote;

public class TrivalNoteAdapter extends BaseAdapter{
	private List<TrivalNote> list;
	private LayoutInflater inflater;
	private Context ctx;
	
	public TrivalNoteAdapter(Context context, List<TrivalNote> list) {
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
			convertView = inflater.inflate(R.layout.travel_notes_item, null);
			holder.travelNotesPictrue = (ImageView) convertView.findViewById(R.id.travelNotesPictrue);
			holder.travelnotesTreasureFlag = (ImageView) convertView.findViewById(R.id.travelnotesTreasureFlag);
			holder.travelNotesName =  (TextView) convertView.findViewById(R.id.travelNotesName);
			holder.travelNotesItemCommentCountText = (TextView) convertView.findViewById(R.id.travelNotesItemCommentCountText);
			holder.travelNotesItemVisitCountText = (TextView) convertView.findViewById(R.id.travelNotesItemVisitCountText);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		TrivalNote note = list.get(position);
		holder.travelNotesPictrue.setImageResource(note.getCover());
		holder.travelNotesName.setText(note.getTitle());
		holder.travelNotesItemCommentCountText.setText(note.getNumComment());
		holder.travelNotesItemVisitCountText.setText(note.getNumVisit());
		if(note.isTreasure()){
			holder.travelnotesTreasureFlag.setVisibility(View.VISIBLE);
		}else{
			holder.travelnotesTreasureFlag.setVisibility(View.GONE);
		}
		return convertView;
	}
	final class ViewHolder{
		ImageView travelNotesPictrue;
		ImageView travelnotesTreasureFlag;
		TextView travelNotesName;
		TextView travelNotesItemCommentCountText;
		TextView travelNotesItemVisitCountText;
	}
}
