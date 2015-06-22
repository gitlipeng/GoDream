package com.godream.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.godream.R;
import com.godream.adapter.TrivalNoteAdapter;
import com.godream.bean.TrivalNote;
import com.godream.defineview.XListView;
import com.godream.util.LogUtil;

public class TrivalNoteFragment extends Fragment implements OnClickListener {

	private XListView mXlistview;

	private List<TrivalNote> noteList;

	private Activity activity;

	private TrivalNoteAdapter adapter;

	private LinearLayout mTitileLayout;

	private LayoutInflater inflater;

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (adapter.getCount() >= 40) {
				mXlistview.stopRefresh();
				mXlistview.stopLoadMore();
				mXlistview.setLoadType(XListView.NONE_LOAD_TYPE);
				Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
				return;
			}
			noteList.addAll(TrivalNote.getData(5));
			adapter.notifyDataSetChanged();
			mXlistview.stopLoadMore();
			mXlistview.stopRefresh();
		};
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.getInstance().i("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		inflater = LayoutInflater.from(getActivity());
		LogUtil.getInstance().i("onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtil.getInstance().i("onCreateView");
		View v = inflater.inflate(R.layout.trivalnotelist, container, false);
		mXlistview = (XListView) v.findViewById(R.id.xlistview);
		mXlistview.setLoadType(XListView.AUTO_LOAD_TYPE);
		mXlistview.setPullRefreshEnable(true);

		noteList = TrivalNote.getData(5);
		adapter = new TrivalNoteAdapter(getActivity(), noteList);
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
		this.mXlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int relPosition = position - mXlistview.getHeaderViewsCount();
				Toast.makeText(getActivity(), noteList.get(relPosition).getTitle(), Toast.LENGTH_SHORT).show();
			}
		});

		mTitileLayout = (LinearLayout) v.findViewById(R.id.title_ll);
		addImageView(mTitileLayout, R.drawable.ic_title_export_default);
		addImageView(mTitileLayout, R.drawable.ic_title_home_default);

		return v;
	}

	private void addImageView(LinearLayout ll, int id) {
		View view = inflater.inflate(R.layout.actionbar_item, ll, false);
		ImageButton btn = (ImageButton) view.findViewById(R.id.actionbar_item);
		btn.setImageResource(id);
		btn.setId(id);
		int index = ll.getChildCount();
		view.setOnClickListener(this);
		ll.addView(view, index);
	}

	private void getData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					handler.sendMessage(handler.obtainMessage());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.getInstance().i("onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.getInstance().i("onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.getInstance().i("onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.getInstance().i("onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.getInstance().i("onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.getInstance().i("onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.getInstance().i("onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.getInstance().i("onDetach");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.drawable.ic_title_export_default:
				mXlistview.setLoadType(XListView.AUTO_LOAD_TYPE);
				break;
			case R.drawable.ic_title_home_default:
				mXlistview.setLoadType(XListView.PULL_LOAD_TYPE);
				break;
			default:
				break;
		}
	}
}
