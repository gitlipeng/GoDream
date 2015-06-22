package com.godream.defineview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.godream.R;
import com.godream.common.Common;
import com.godream.util.DPIUtil;
import com.godream.util.LogUtil;

public class NewUserGuideScrollView extends ScrollView {
	private int animY = 0;
	private int curHideIndex = 2;
	private int offsetY = (int) (0.75D * Common._ScreenHeight);
	private float curScrollPositionY;
	private int curShowIndex = 1;
	private Context mContext;
	private boolean initView = false;
	private TextView guideGoButton;
	private TextView guidePointT5;
	private TextView guidePointT6;
	private ImageView guidePointX1;
	private ImageView guidePointX1Sub1;
	private ImageView guidePointX1Sub2;
	private ImageView guidePointX2;
	private TextView guidePointX2SubTitle;
	private TextView guidePointX2Title;
	private ImageView guidePointX3;
	private TextView guidePointX3SubTitle;
	private TextView guidePointX3Title;
	private ImageView guidePointX4;
	private TextView guidePointX4SubTitle;
	private TextView guidePointX4Title;
	private ImageView guidePointX5;
	private ImageView guidePointX6;
	private ImageView guidePointX7;
	private ImageView guidePointX8;
	private ImageView guidePointX9;
	private ImageView guideShineSpot1;
	private ImageView guideShineSpot2;
	private ImageView guideShineSpot3;
	private ImageView guideShineSpot4;
	private ImageView guideShineSpot5;
	private Handler mHandler;
	
	private LinearLayout newUserGuideLayout;
	private ViewGroup.MarginLayoutParams newUserGuideLayoutLP;

	public NewUserGuideScrollView(Context context) {
		super(context);
		this.mContext = context;
	}

	public NewUserGuideScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	public NewUserGuideScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
	}

	public void setHandler(Handler paramHandler) {
		this.mHandler = paramHandler;
	}
	
	private void initView() {
		this.guidePointX1 = ((ImageView) findViewById(R.id.guidePointX1));
		this.guidePointX1Sub1 = ((ImageView) findViewById(R.id.guidePointX1Sub1));
		this.guidePointX1Sub2 = ((ImageView) findViewById(R.id.guidePointX1Sub2));
		this.guidePointX2 = ((ImageView) findViewById(R.id.guidePointX2));
		this.guidePointX3 = ((ImageView) findViewById(R.id.guidePointX3));
		this.guidePointX4 = ((ImageView) findViewById(R.id.guidePointX4));
		this.guidePointX5 = ((ImageView) findViewById(R.id.guidePointX5));
		this.guidePointX6 = ((ImageView) findViewById(R.id.guidePointX6));
		this.guidePointX7 = ((ImageView) findViewById(R.id.guidePointX7));
		this.guidePointX8 = ((ImageView) findViewById(R.id.guidePointX8));
		this.guidePointX9 = ((ImageView) findViewById(R.id.guidePointX9));
		// this.guideShineSpot1 =
		// ((ImageView)findViewById(R.id.guideShineSpot1));
		// this.guideShineSpot2 =
		// ((ImageView)findViewById(R.id.guideShineSpot2));
		// this.guideShineSpot3 =
		// ((ImageView)findViewById(R.id.guideShineSpot3));
		// this.guideShineSpot4 =
		// ((ImageView)findViewById(R.id.guideShineSpot4));
		// this.guideShineSpot5 =
		// ((ImageView)findViewById(R.id.guideShineSpot5));
		this.guidePointT6 = ((TextView) findViewById(R.id.guidePointT6));
		this.guidePointX2Title = ((TextView) findViewById(R.id.guidePointX2Title));
		this.guidePointX2SubTitle = ((TextView) findViewById(R.id.guidePointX2SubTitle));
		this.guidePointX3Title = ((TextView) findViewById(R.id.guidePointX3Title));
		this.guidePointX3SubTitle = ((TextView) findViewById(R.id.guidePointX3SubTitle));
		this.guidePointX4Title = ((TextView) findViewById(R.id.guidePointX4Title));
		this.guidePointX4SubTitle = ((TextView) findViewById(R.id.guidePointX4SubTitle));
		this.guidePointT5 = ((TextView) findViewById(R.id.guidePointT5));
		this.guideGoButton = ((TextView) findViewById(R.id.guideGoButton));
	
		NumAnimationDrawable localNumAnimationDrawable = new NumAnimationDrawable(
				this.mContext, getHandler());
		localNumAnimationDrawable.setAnimationListener(new NumAnimationDrawable.NumAnimationListener() {
			@Override
			public void onAnimationEnd() {
			}
			
			@Override
			public void onAnimationChange(int paramInt) {
				NewUserGuideScrollView.this.mHandler
				.post(new Runnable() {
					public void run() {
						NewUserGuideScrollView.this
								.invalidate();
					}
				});
			}
		});
		
		this.guidePointX7.setImageDrawable(localNumAnimationDrawable);
	}

	/*
	 * newUserGuideLayoutLP.topMargin为高度一半减去40dip，使开始布局在下方
	 * 
	 * @see android.widget.ScrollView#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (!this.initView) {
			this.initView = true;
			initView();
		}
		LinearLayout localLinearLayout = (LinearLayout) findViewById(R.id.newUserGuideTitleLayout);
		if (localLinearLayout.getVisibility() != 0) {
			this.newUserGuideLayout = ((LinearLayout) findViewById(R.id.newUserGuideLayout));
			this.newUserGuideLayoutLP = ((ViewGroup.MarginLayoutParams) this.newUserGuideLayout
					.getLayoutParams());
			this.newUserGuideLayoutLP.topMargin = (Common._ScreenHeight / 2 - DPIUtil
					.dip2px(40.0F));
			localLinearLayout.startAnimation(getTitleViewFadeIn());
			localLinearLayout.setVisibility(0);
		}
	}

	/**
	 * 描述：1-头部显示
	 * 
	 * @return 创建人：lipeng 创建时间：2013-10-26 下午1:03:05 备注：
	 */
	private Animation getTitleViewFadeIn() {
		AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
		localAlphaAnimation.setDuration(800L);
		localAlphaAnimation
				.setAnimationListener(new Animation.AnimationListener() {
					public void onAnimationEnd(Animation paramAnimation) {
						View localView = NewUserGuideScrollView.this
								.findViewById(R.id.newUserGuidePointLayout);
						localView.startAnimation(NewUserGuideScrollView.this
								.getPointViewFadeIn());
						localView.setVisibility(0);
					}

					public void onAnimationRepeat(Animation paramAnimation) {
					}

					public void onAnimationStart(Animation paramAnimation) {
					}
				});
		return localAlphaAnimation;
	}

	/**
	 * 描述：2-整体Layout向上移动，第一个点放大为图像
	 * 
	 * @return 创建人：lipeng 创建时间：2013-10-26 下午1:03:17 备注：
	 */
	private Animation getPointViewFadeIn() {
		AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
		localAlphaAnimation.setDuration(800L);
		localAlphaAnimation
				.setAnimationListener(new Animation.AnimationListener() {
					public void onAnimationEnd(Animation paramAnimation) {
						NewUserGuideScrollView.this.newUserGuideLayout
								.startAnimation(NewUserGuideScrollView.this
										.getRootViewTransSet());
						NewUserGuideScrollView.this.guidePointX1
								.startAnimation(NewUserGuideScrollView.this
										.getGuidePointX1ScaleIn());
						NewUserGuideScrollView.this.guidePointX1
								.setVisibility(0);
					}

					public void onAnimationRepeat(Animation paramAnimation) {
					}

					public void onAnimationStart(Animation paramAnimation) {
					}
				});
		return localAlphaAnimation;
	}

	/**
	 *描述：设置整体布局动画
	 *@return
	 *创建人：lipeng
	 *创建时间：2013-10-26 下午4:32:27
	 *备注：
	 */
	private AnimationSet getRootViewTransSet() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		localAnimationSet.addAnimation(getRootViewTrans());
		localAnimationSet.setFillBefore(false);
		localAnimationSet.setFillAfter(true);
		localAnimationSet
				.setAnimationListener(new Animation.AnimationListener() {
					public void onAnimationEnd(Animation paramAnimation) {
					}

					public void onAnimationRepeat(Animation paramAnimation) {
					}

					public void onAnimationStart(Animation paramAnimation) {
					}
				});
		return localAnimationSet;
	}

	/**
	 * 描述：整体移动Animation，Y方向向上移动
	 * 
	 * @return 创建人：lipeng 创建时间：2013-10-26 下午1:07:31 备注：
	 */
	private Animation getRootViewTrans() {
		this.animY = (this.newUserGuideLayoutLP.topMargin - DPIUtil
				.dip2px(62.0F));
		TranslateAnimation localTranslateAnimation = new TranslateAnimation(
				0.0F, 0.0F, 0.0F, -this.animY);
		localTranslateAnimation.setDuration(600L);
		return localTranslateAnimation;
	}

	/**
	 *描述：缩放动画，显示PointX1
	 *@return
	 *创建人：lipeng
	 *创建时间：2013-10-26 下午1:14:30
	 *备注：
	 */
	private Animation getGuidePointX1ScaleIn() {
		ScaleAnimation localScaleAnimation = new ScaleAnimation(0.0F, 1.0F,
				0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
		localScaleAnimation.setDuration(600L);
		localScaleAnimation
				.setAnimationListener(new Animation.AnimationListener() {
					public void onAnimationEnd(Animation paramAnimation) {
						NewUserGuideScrollView.this.guidePointX1Sub1
								.setVisibility(0);
						NewUserGuideScrollView.this.guidePointX1Sub2
								.setVisibility(0);
//						NewUserGuideScrollView.this.guideShineSpot1
//								.setVisibility(0);
//						NewUserGuideScrollView.this.guideShineSpot2
//								.setVisibility(0);
//						NewUserGuideScrollView.this.guideShineSpot3
//								.setVisibility(0);
//						NewUserGuideScrollView.this.guideShineSpot4
//								.setVisibility(0);
//						NewUserGuideScrollView.this.guideShineSpot5
//								.setVisibility(0);
//						if (NewUserGuideScrollView.this.mShineThread == null) {
//							NewUserGuideScrollView.this.mShineThread = new NewUserGuideScrollView.ShineThread(
//									NewUserGuideScrollView.this);
//							NewUserGuideScrollView.this.mShineThread.start();
//						}
					}

					public void onAnimationRepeat(Animation paramAnimation) {
					}

					public void onAnimationStart(Animation paramAnimation) {
					}
				});
		return localScaleAnimation;
	}
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		int i = -1;
		Object localObject1;
		TextView localTextView1;
		ImageView localImageView;
		if(t == oldt)
			return;
		if(t > oldt){
			i = this.curHideIndex;
		}
		if(t < oldt){
			i = this.curShowIndex;
		}
		
		localObject1 = null;
		localTextView1 = null;
		localImageView = null;
			
		switch (i) {
		case 2:
			localImageView = this.guidePointX2;
			localTextView1 = this.guidePointX2Title;
			localObject1 = this.guidePointX2SubTitle;
			break;
		case 3:
			localImageView = this.guidePointX3;
			localTextView1 = this.guidePointX3Title;
			localObject1 = this.guidePointX3SubTitle;
			break;
		case 4:
			localImageView = this.guidePointX4;
			localTextView1 = this.guidePointX4Title;
			localObject1 = this.guidePointX4SubTitle;
			break;
		case 5:
			localImageView = this.guidePointX5;
			localTextView1 = this.guidePointT5;
			localObject1 = null;
			break;
		case 6:
			localImageView = this.guidePointX6;
			localTextView1 = this.guidePointT6;
			localObject1 = this.guidePointX7;
			break;
		case 7:
			localImageView = this.guidePointX8;
			localObject1 = null;
			localTextView1 = null;
			break;
		default:
			return;
		}
		int[] arrayOfInt = new int[2];
		localImageView.getLocationOnScreen(arrayOfInt);
		if (arrayOfInt[1] + DPIUtil.dip2px(52.0F) - this.animY <= this.offsetY){
			//向上滑动,逐个显示
			if(localImageView.getVisibility() == View.INVISIBLE){
				curHideIndex = (i + 1);
				curShowIndex = i;
				
				if(i == 6){
					localImageView.startAnimation(getCommonScaleIn());
					localImageView.setVisibility(View.VISIBLE);
					if (localTextView1 != null) {
						localTextView1.startAnimation(getCommonFadeInSet());
						localTextView1.setVisibility(View.VISIBLE);
					}
					
					NumAnimationDrawable localNumAnimationDrawable = (NumAnimationDrawable)guidePointX7.getDrawable();
					localNumAnimationDrawable.start(false);
					guidePointX7.setVisibility(View.VISIBLE);
				}else if(i == 7){
					//最后一个图标，播放旋转动画
					localImageView.startAnimation(getRotateIn());
					localImageView.setVisibility(View.VISIBLE);
				}else{
					localImageView.startAnimation(getCommonScaleIn());
					localImageView.setVisibility(View.VISIBLE);
					
					if (localTextView1 != null) {
						localTextView1.startAnimation(getCommonFadeInSet());
						localTextView1.setVisibility(View.VISIBLE);
					}
					if (localObject1 != null) {
						((View) localObject1).startAnimation(getCommonFadeInSet());
						((View) localObject1).setVisibility(View.VISIBLE);
					}
				}
				

			}
		}else{
			if(localImageView.getVisibility() == View.VISIBLE){
				//向下滑动,逐个消失
				curHideIndex = i;
				curShowIndex = (i - 1);
				if(i == 7){
					//最后一个图标，播放旋转动画
					localImageView.startAnimation(getRotateOut());
					localImageView.setVisibility(View.INVISIBLE);
				}else{
					localImageView.startAnimation(getCommonScaleOut());
					localImageView.setVisibility(View.INVISIBLE);
					if (localTextView1 != null) {
						localTextView1.startAnimation(getCommonFadeOut());
						localTextView1.setVisibility(View.INVISIBLE);
					}
					if (localObject1 != null) {
						((View) localObject1).startAnimation(getCommonFadeOut());
						((View) localObject1).setVisibility(View.INVISIBLE);
					}
				}
			}
		}
	}
	private AnimationSet getRotateIn() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 8.0F,
				this.guidePointX9.getWidth() / 2, this.guidePointX9.getHeight());
		localRotateAnimation.setDuration(400L);
		localAnimationSet.addAnimation(localRotateAnimation);
		localAnimationSet.setFillAfter(true);
		return localAnimationSet;
	}

	/**
	 *描述：旋转动画
	 *@return
	 *创建人：lipeng
	 *创建时间：2013-10-26 下午4:51:58
	 *备注：
	 */
	private AnimationSet getRotateOut() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		RotateAnimation localRotateAnimation = new RotateAnimation(8.0F, 0.0F,
				this.guidePointX9.getWidth() / 2, this.guidePointX9.getHeight());
		localRotateAnimation.setDuration(400L);
		localAnimationSet.addAnimation(localRotateAnimation);
		localAnimationSet.setFillAfter(true);
		return localAnimationSet;
	}
	
	/**
	 *描述：缩放动画
	 *@return
	 *创建人：lipeng
	 *创建时间：2013-10-26 下午4:52:07
	 *备注：
	 */
	private AnimationSet getCommonScaleIn() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		ScaleAnimation localScaleAnimation = new ScaleAnimation(0.0F, 1.0F,
				0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
		localScaleAnimation.setDuration(400L);
		localAnimationSet.addAnimation(localScaleAnimation);
		return localAnimationSet;
	}

	private Animation getCommonScaleOut() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 0.0F,
				1.0F, 0.0F, 1, 0.5F, 1, 0.5F);
		localScaleAnimation.setDuration(400L);
		localAnimationSet.addAnimation(localScaleAnimation);
		return localScaleAnimation;
	}
	
	/**
	 *描述：淡入淡出动画
	 *@return
	 *创建人：lipeng
	 *创建时间：2013-10-26 下午4:54:50
	 *备注：
	 */
	private AnimationSet getCommonFadeInSet() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
		localAlphaAnimation.setDuration(400L);
		localAnimationSet.addAnimation(localAlphaAnimation);
		localAnimationSet.setFillAfter(true);
		return localAnimationSet;
	}

	private Animation getCommonFadeOut() {
		AnimationSet localAnimationSet = new AnimationSet(true);
		AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
		localAlphaAnimation.setDuration(400L);
		localAnimationSet.addAnimation(localAlphaAnimation);
		return localAlphaAnimation;
	}
}




















