package com.godream.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godream.R;
import com.godream.util.LogUtil;

public class MoreFragment extends Fragment {
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.getInstance().i("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.getInstance().i("onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogUtil.getInstance().i("onCreateView");
		return inflater.inflate(R.layout.more_view, container, false);
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
}
