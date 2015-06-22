package com.godream.defineview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.godream.R;
import com.godream.util.LogUtil;

public class XListView extends ListView implements AbsListView.OnScrollListener {
	public static final int NONE_LOAD_TYPE = 0;// 禁用加载更多
	public static final int PULL_LOAD_TYPE = 1;// 手动上滑加载更多
	public static final int AUTO_LOAD_TYPE = 2;// 自动加载更多
	
	private static final float OFFSET_RADIO = 1.8F;// 下拉时上部滚动距离为手指移动距离除以1.8

	private static final int PULL_LOAD_MORE_DELTA = 50;// 上滑超过50，加载更多

	private static final int SCROLLBACK_FOOTER = 1;// 滚动到footer

	private static final int SCROLLBACK_HEADER = 0;// 滚动到header

	private static final int SCROLL_DURATION = 400;// 滚动持续时间

	private boolean mEnablePullLoad;// 是否可以加载更多

	private boolean mEnablePullRefresh = true;// 是否可以下拉刷新，默认可以

	private boolean mEnableAutoLoad;// 是否可以自动加载

	private XListViewFooter mFooterView;

	private TextView mHeaderTimeView;

	private XListViewHeader mHeaderView;

	private RelativeLayout mHeaderViewContent;

	private int mHeaderViewHeight;// header的高度

	private boolean mIsFooterReady = false;

	private float mLastY = -1.0F;// 记录上次手指位置，用当前距离与现在坐标来判断是向上滑动还是向下滑动，>0:向下滑动，<0：向上滑动

	private IXListViewListener mListViewListener;// 松开手指时，做的操作

	private boolean mPullLoading;// 是否正在加载中

	private boolean mPullRefreshing = false;// 是否正在刷新中

	private int mScrollBack;// 区分滚动到头部还是底部

	private AbsListView.OnScrollListener mScrollListener;

	private Scroller mScroller;

	private int mTotalItemCount;

	public XListView(Context paramContext) {
		super(paramContext);
		initWithContext(paramContext);
	}

	public XListView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		initWithContext(paramContext);
	}

	public XListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		initWithContext(paramContext);
	}

	private void initWithContext(Context paramContext) {
		this.mScroller = new Scroller(paramContext, new DecelerateInterpolator());
		super.setOnScrollListener(this);
		/** Header */
		this.mHeaderView = new XListViewHeader(paramContext);
		this.mHeaderViewContent = ((RelativeLayout) this.mHeaderView.findViewById(R.id.xlistview_header_content));
		this.mHeaderTimeView = ((TextView) this.mHeaderView.findViewById(R.id.xlistview_header_time));
		addHeaderView(this.mHeaderView);
		this.mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			public void onGlobalLayout() {
				XListView.this.mHeaderViewHeight = XListView.this.mHeaderViewContent.getHeight();
				XListView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});

		/** Footer */
		this.mFooterView = new XListViewFooter(paramContext);
	}

	public boolean onTouchEvent(MotionEvent paramMotionEvent) {
		if (this.mLastY == -1.0F)
			this.mLastY = paramMotionEvent.getRawY();// 第一次，获取Y的位置
		switch (paramMotionEvent.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.mLastY = paramMotionEvent.getRawY();// 按下手指，获取当前Y位置
				break;
			case MotionEvent.ACTION_MOVE:
				float f = paramMotionEvent.getRawY() - this.mLastY;// f>0：向下滑动，f<0：向上滑动
				this.mLastY = paramMotionEvent.getRawY();

				/** 更新头部高度以及箭头样式 */
				if (this.mEnablePullRefresh && (getFirstVisiblePosition() == 0) && ((this.mHeaderView.getVisiableHeight() > 0) || (f > 0.0F))) {
					updateHeaderHeight(f / OFFSET_RADIO);
					invokeOnScrolling();
				}

				/** 更新底部高度以及样式 */
				if (this.mEnablePullLoad && (getLastVisiblePosition() == -1 + this.mTotalItemCount)
				        && ((this.mFooterView.getBottomMargin() > 0) || (f < 0.0F))) {
					updateFooterHeight(-f / OFFSET_RADIO);
				}
				break;
			default:
				// 松开手后
				this.mLastY = -1.0F;

				// 下拉刷新
				if ((this.mEnablePullRefresh) && (this.mHeaderView.getVisiableHeight() > this.mHeaderViewHeight) && !mPullRefreshing) {
					this.mPullRefreshing = true;
					this.mHeaderView.setState(XListViewHeader.STATE_REFRESHING);
					if (this.mListViewListener != null) {
						this.mListViewListener.onRefresh();// 执行下拉刷新的动作
					}
				}
				resetHeaderHeight();

				// 上滑加载
				if ((getLastVisiblePosition() == -1 + this.mTotalItemCount) && (this.mEnablePullLoad)
				        && (this.mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) && !mPullLoading) {
					startLoadMore();// 执行加载更多
				}
				resetFooterHeight();
				break;
		}
		return super.onTouchEvent(paramMotionEvent);
	}

	/**
	 * 描述：更新Header的高度，改变箭头的样子
	 * 
	 * @param paramFloat
	 *            创建人：lipeng 创建时间：2013-10-30 下午8:43:55 备注：
	 */
	private void updateHeaderHeight(float paramFloat) {
		this.mHeaderView.setVisiableHeight((int) paramFloat + this.mHeaderView.getVisiableHeight());

		if ((this.mEnablePullRefresh) && (!this.mPullRefreshing)) {
			if (this.mHeaderView.getVisiableHeight() <= this.mHeaderViewHeight) {
				this.mHeaderView.setState(XListViewHeader.STATE_NORMAL);// 下拉距离没有超过Header的高度
			} else {
				this.mHeaderView.setState(XListViewHeader.STATE_READY);// 下拉距离超过Header的高度
			}
		}
		setSelection(0);
	}

	/**
	 * 描述：更新Footer的高度，改变样式
	 * 
	 * @param paramFloat
	 *            创建人：lipeng 创建时间：2013-10-30 下午8:52:56 备注：
	 */
	private void updateFooterHeight(float paramFloat) {
		int i = (int) (paramFloat + this.mFooterView.getBottomMargin());
		LogUtil.getInstance().i("bottom:" + i);
		this.mFooterView.setBottomMargin(i);

		if ((this.mEnablePullLoad) && (!this.mPullLoading)) {
			if (i <= PULL_LOAD_MORE_DELTA) {
				this.mFooterView.setState(XListViewFooter.STATE_NORMAL);// 移动距离小于50，设置为普通状态
			} else {
				this.mFooterView.setState(XListViewFooter.STATE_READY);// 移动距离大于50，设置为松开加载状态
			}
		}
	}

	/**
	 * 描述：加载更多 创建人：lipeng 创建时间：2013-10-30 下午9:05:16 备注：
	 */
	private void startLoadMore() {
		this.mFooterView.show();
		this.mPullLoading = true;
		this.mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (this.mListViewListener != null)
			this.mListViewListener.onLoadMore();// 加载更多
	}

	private void invokeOnScrolling() {
		if ((this.mScrollListener instanceof OnXScrollListener))
			((OnXScrollListener) this.mScrollListener).onXScrolling(this);
	}

	/**
	 * 描述：松手后调整高度 创建人：lipeng 创建时间：2013-10-30 下午9:09:44 备注：
	 */
	private void resetHeaderHeight() {
		int visiableHeight = this.mHeaderView.getVisiableHeight();
		if (visiableHeight == 0)
			return;

		int moveDistance = 0;
		if (visiableHeight > this.mHeaderViewHeight) {
			moveDistance = this.mHeaderViewHeight;
		}
		if (!this.mPullRefreshing) {
			moveDistance = 0;
		}
		this.mScrollBack = SCROLLBACK_HEADER;
		this.mScroller.startScroll(0, visiableHeight, 0, -(visiableHeight - moveDistance), SCROLL_DURATION);// 将调用computeScroll方法
		invalidate();
	}

	/**
	 * 描述：松手后调整高度 创建人：lipeng 创建时间：2013-10-30 下午9:15:03 备注：
	 */
	private void resetFooterHeight() {
		int i = this.mFooterView.getBottomMargin();
		if (i > 0) {
			this.mScrollBack = SCROLLBACK_FOOTER;
			this.mScroller.startScroll(0, i, 0, -i, SCROLL_DURATION);
			invalidate();
		}
	}

	/*
	 * 真正移动距离的方法
	 * @see android.view.View#computeScroll()
	 */
	public void computeScroll() {
		if (this.mScroller.computeScrollOffset()) {
			if (this.mScrollBack != SCROLLBACK_HEADER) {
				this.mFooterView.setBottomMargin(this.mScroller.getCurrY());
			} else {
				this.mHeaderView.setVisiableHeight(this.mScroller.getCurrY());
			}
		}
		postInvalidate();
		invokeOnScrolling();
		super.computeScroll();
	}

	public void onScroll(AbsListView paramAbsListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.mTotalItemCount = totalItemCount;
		LogUtil.getInstance().i("firstVisibleItem:" + firstVisibleItem + ",visibleItemCount():" + visibleItemCount  + ",totalItemCount:"+totalItemCount) ;
		if(mEnableAutoLoad){
			//自动加载更多
			if(getLastVisiblePosition() == -1 + this.mTotalItemCount){
				super.setOnScrollListener(null);
				LogUtil.getInstance().i("getLastVisiblePosition():" + getLastVisiblePosition());
				startLoadMore();
			}
		}
		
		if (this.mScrollListener != null)
			this.mScrollListener.onScroll(paramAbsListView, firstVisibleItem, visibleItemCount, totalItemCount);
	}

	public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
		if (this.mScrollListener != null)
			this.mScrollListener.onScrollStateChanged(paramAbsListView, paramInt);
	}

	/*
	 * 设置adapter，加载footerView
	 * @see android.widget.ListView#setAdapter(android.widget.ListAdapter)
	 */
	public void setAdapter(ListAdapter paramListAdapter) {
		if (!this.mIsFooterReady) {
			this.mIsFooterReady = true;
			addFooterView(this.mFooterView);
		}
		super.setAdapter(paramListAdapter);
	}

	public void setOnScrollListener(AbsListView.OnScrollListener paramOnScrollListener) {
		this.mScrollListener = paramOnScrollListener;
	}

	public void setLoadType(int type){
		switch (type) {
			case NONE_LOAD_TYPE:
				setPullLoadEnable(false);
				setAutoLoadEnable(false);
				break;
			case PULL_LOAD_TYPE:
				setPullLoadEnable(true);
				break;
			case AUTO_LOAD_TYPE:
				setAutoLoadEnable(true);
				break;
			default:
				break;
		}
	}
	
	/**
	 * 描述：加载更多
	 * 
	 * @param paramBoolean
	 *            创建人：lipeng 创建时间：2013-10-29 上午11:48:31 备注：
	 */
	private void setPullLoadEnable(boolean paramBoolean) {
		this.mEnablePullLoad = paramBoolean;
		if (this.mEnablePullLoad) {
			setAutoLoadEnable(false);//不能同时出现自动加载更多和上滑加载更多
			this.mPullLoading = false;
			this.mFooterView.show();
			this.mFooterView.setState(XListViewFooter.STATE_NORMAL);
			this.mFooterView.setOnClickListener(new View.OnClickListener() {

				public void onClick(View paramView) {
					XListView.this.startLoadMore();
				}
			});
		} else {
			this.mFooterView.hide();
			this.mFooterView.setOnClickListener(null);
		}
	}

	/**
	 * 描述：自动加载更多
	 * 
	 * @param paramBoolean
	 *            创建人：lipeng 创建时间：2013-10-29 上午11:48:39 备注：
	 */
	private void setAutoLoadEnable(boolean mEnableAutoLoad) {
		this.mEnableAutoLoad = mEnableAutoLoad;
		if (this.mEnableAutoLoad) {
			setPullLoadEnable(false);//不能同时出现自动加载更多和上滑加载更多
		}
	}

	/**
	 * 描述：下拉刷新
	 * 
	 * @param paramBoolean
	 *            创建人：lipeng 创建时间：2013-10-29 上午11:48:39 备注：
	 */
	public void setPullRefreshEnable(boolean paramBoolean) {
		this.mEnablePullRefresh = paramBoolean;
		if (this.mEnablePullRefresh) {
			this.mHeaderViewContent.setVisibility(View.VISIBLE);
		} else {
			this.mHeaderViewContent.setVisibility(View.INVISIBLE);
		}
	}

	public void setRefreshTime(String paramString) {
		this.mHeaderTimeView.setText(paramString);
	}

	public void setXListViewListener(IXListViewListener paramIXListViewListener) {
		this.mListViewListener = paramIXListViewListener;
	}

	public void stopLoadMore() {
		if (this.mPullLoading) {
			this.mPullLoading = false;
			this.mFooterView.setState(XListViewFooter.STATE_NORMAL);
			super.setOnScrollListener(this);
		}
	}

	public void stopRefresh() {
		if (this.mPullRefreshing) {
			this.mPullRefreshing = false;
			resetHeaderHeight();
		}
	}

	public static abstract interface IXListViewListener {

		public abstract void onLoadMore();

		public abstract void onRefresh();
	}

	public static abstract interface OnXScrollListener extends AbsListView.OnScrollListener {

		public abstract void onXScrolling(View paramView);
	}
}