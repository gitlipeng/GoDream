package com.godream;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import com.godream.adapter.BeanAdapter;
import com.godream.adapter.LandscapeListAdapter;
import com.godream.bean.Landscape;
import com.godream.bean.TrivalNote;
import com.godream.defineview.XListView;
import com.godream.util.LogUtil;

public class TrivalNoteList extends Activity{
	private XListView mXlistview;
	private List<TrivalNote> noteList;
	private BeanAdapter adapter;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			noteList.addAll(TrivalNote.getData(5));
			adapter.notifyDataSetChanged();
			mXlistview.stopLoadMore();
			mXlistview.stopRefresh();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trivalnotelist);
		mXlistview = (XListView) findViewById(R.id.xlistview);
		mXlistview.setLoadType(XListView.PULL_LOAD_TYPE);
		mXlistview.setPullRefreshEnable(true);

		noteList = TrivalNote.getData(15);
		adapter = new BeanAdapter(this, noteList,
				R.layout.travel_notes_item, new String[] { "title", "cover",
						"numComment", "numVisit" }, new int[] {
						R.id.travelNotesName, R.id.travelNotesPictrue,
						R.id.travelNotesItemCommentCountText,
						R.id.travelNotesItemVisitCountText }) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TrivalNote note = (TrivalNote) getItem(position);
				View localView1 = super.getView(position, convertView, parent);
				View localView2 = localView1
						.findViewById(R.id.travelnotesTreasureFlag);
				if (note.isTreasure()) {
					localView2.setVisibility(View.VISIBLE);
				} else {
					localView2.setVisibility(View.GONE);
				}
				return localView1;
			}
		};
		mXlistview.setAdapter(adapter);
		mXlistview.setXListViewListener(new XListView.IXListViewListener() {
			@Override
			public void onRefresh() {
				LogUtil.getInstance().i("onRefresh");
				getData();
			}

			@Override
			public void onLoadMore() {
				LogUtil.getInstance().i("onLoadMore");
				getData();
			}
		});
		this.mXlistview.stopLoadMore();

	}
	private void getData() {
		if (adapter.getCount() >= 40) {
			mXlistview.stopRefresh();
			mXlistview.stopLoadMore();
			return;
		}
		handler.sendMessageDelayed(handler.obtainMessage(), 2000l);
	}
}
