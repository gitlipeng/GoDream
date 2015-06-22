package com.godream.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.godream.R;
import com.godream.defineview.FingerImageView;
import com.godream.util.LogUtil;

public class GuidFragment extends Fragment implements OnClickListener{
	private FingerImageView mImageView1;
	private FingerImageView mImageView2;
	private FingerImageView mImageView3;
	private FingerImageView mImageView4;
	private FingerImageView mImageView5;
	private FingerImageView mImageView6;
	private FingerImageView mImageView7;
	private FingerImageView mImageView8;
	private FingerImageView mImageView9;
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
		View v = inflater.inflate(R.layout.guidefragment, container, false);
		mImageView1 = (FingerImageView) v.findViewById(R.id.guide_item1_id_bak);
		mImageView2 = (FingerImageView) v.findViewById(R.id.guide_item2_id);
		mImageView3 = (FingerImageView) v.findViewById(R.id.guide_item3_id);
		mImageView4 = (FingerImageView) v.findViewById(R.id.guide_item4_id);
		mImageView5 = (FingerImageView) v.findViewById(R.id.guide_item5_id);
		mImageView6 = (FingerImageView) v.findViewById(R.id.guide_item6_id);
		mImageView7 = (FingerImageView) v.findViewById(R.id.guide_item7_id);
		mImageView8 = (FingerImageView) v.findViewById(R.id.guide_item8_id);
		mImageView9 = (FingerImageView) v.findViewById(R.id.guide_item9_id);
		
		mImageView1.setOnClickListener(this);
		mImageView2.setOnClickListener(this);
		mImageView3.setOnClickListener(this);
		mImageView4.setOnClickListener(this);
		mImageView5.setOnClickListener(this);
		mImageView6.setOnClickListener(this);
		mImageView7.setOnClickListener(this);
		mImageView8.setOnClickListener(this);
		mImageView9.setOnClickListener(this);
		return v;
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
		FingerImageView view = (FingerImageView)v;
		Toast.makeText(getActivity(), view.getText(), Toast.LENGTH_SHORT).show();
	}
}
