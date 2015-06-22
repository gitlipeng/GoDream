package com.godream.defineview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.godream.R;
import com.godream.util.LogUtil;

public class XListViewHeader extends LinearLayout {
	public static final int STATE_NORMAL = 0;
	public static final int STATE_READY = 1;
	public static final int STATE_REFRESHING = 2;
	private final int ROTATE_ANIM_DURATION = 180;
	private ImageView mArrowImageView;
	private LinearLayout mContainer;
	private TextView mHintTextView;
	private ProgressBar mProgressBar;
	private Animation mRotateDownAnim;
	private Animation mRotateUpAnim;
	private int mState = 0;

	public XListViewHeader(Context paramContext) {
		super(paramContext);
		initView(paramContext);
	}

	public XListViewHeader(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initView(paramContext);
	}

	private void initView(Context paramContext) {
		LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, 0);
		this.mContainer = ((LinearLayout) LayoutInflater.from(paramContext)
				.inflate(R.layout.xlistview_header, null));
		addView(this.mContainer, localLayoutParams);
		setGravity(80);
		this.mArrowImageView = ((ImageView) findViewById(R.id.xlistview_header_arrow));
		this.mHintTextView = ((TextView) findViewById(R.id.xlistview_header_hint_textview));
		this.mProgressBar = ((ProgressBar) findViewById(R.id.xlistview_header_progressbar));
		this.mRotateUpAnim = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1,
				0.5F);
		this.mRotateUpAnim.setDuration(180L);
		this.mRotateUpAnim.setFillAfter(true);
		this.mRotateDownAnim = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1,
				0.5F);
		this.mRotateDownAnim.setDuration(180L);
		this.mRotateDownAnim.setFillAfter(true);
	}

	public int getVisiableHeight() {
		return this.mContainer.getHeight();
	}

	public void setState(int paramInt) {
		if (paramInt == this.mState)
			return;
		if (paramInt == STATE_REFRESHING) {
			this.mArrowImageView.clearAnimation();
			this.mArrowImageView.setVisibility(View.INVISIBLE);
			this.mProgressBar.setVisibility(View.VISIBLE);
		} else {
			this.mArrowImageView.setVisibility(View.VISIBLE);
			this.mProgressBar.setVisibility(View.INVISIBLE);
		}

		switch (paramInt) {
		case STATE_READY:
			this.mArrowImageView.clearAnimation();
			this.mArrowImageView.startAnimation(this.mRotateUpAnim);
			this.mHintTextView.setText(R.string.xlistview_header_hint_ready);
			break;
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				this.mArrowImageView.clearAnimation();
				this.mArrowImageView.startAnimation(this.mRotateDownAnim);
				this.mHintTextView
						.setText(R.string.xlistview_header_hint_normal);
			} else {
				this.mHintTextView
						.setText(R.string.xlistview_header_hint_normal);
			}
			break;
		case STATE_REFRESHING:
			LogUtil.getInstance().i("invaisble");
			this.mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
		default:
			break;
		}
		this.mState = paramInt;
	}

	public void setVisiableHeight(int paramInt) {
		if (paramInt < 0)
			paramInt = 0;
		LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) this.mContainer
				.getLayoutParams();
		localLayoutParams.height = paramInt;
		this.mContainer.setLayoutParams(localLayoutParams);
	}
}