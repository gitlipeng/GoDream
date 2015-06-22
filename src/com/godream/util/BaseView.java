package com.godream.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.godream.R;

public class BaseView {

	public BaseView() {

	}

	private ContentView contentView;

	public ContentView getContentView() {
		return contentView;
	}

	/**
	 * 生成含有导航栏的view
	 * @param context
	 * @param leftLayoutID
	 * @param centerLayoutID
	 * @param rightLayoutID
	 * @param contentLayoutID
	 */
	public void setContentView(Context context, int leftLayoutID, //
			int centerLayoutID, int rightLayoutID,int contentLayoutID) {
		contentView = new ContentView(context,leftLayoutID, centerLayoutID, rightLayoutID,contentLayoutID);
	}

	/**
	 * 生成不含导航栏的view
	 * @param context
	 * @param titleUIListener
	 * @param contentLayoutID
	 * @param noTitle
	 */
	public void setTitleView (Context context,int contentLayoutID,boolean noTitle){
		contentView = new ContentView(context, contentLayoutID, noTitle);
	}

	public class ContentView extends RelativeLayout {

		private Context context;
		private int leftLayoutID;//导航栏布局ID 左部分
		private int centerLayoutID;// 导航栏布局ID 中部分
		private int rightLayoutID;// 导航栏布局ID 右部分

		private int contentLayoutID;// 正文布局ID
		private boolean noTitle = false;// 没有导航栏


		/**
		 * 含有导航栏的界面
		 * @param context
		 * @param leftLayoutID
		 * @param centerLayoutID
		 * @param rightLayoutID
		 * @param contentLayoutID
		 */
		public ContentView(Context context,  int leftLayoutID, //
				int centerLayoutID, int rightLayoutID, int contentLayoutID) {
			super(context);
			this.context = context;
			this.leftLayoutID = leftLayoutID;
			this.centerLayoutID = centerLayoutID;
			this.rightLayoutID = rightLayoutID;
			this.contentLayoutID = contentLayoutID;
			init();
		}

		/**
		 * 不含有导航栏的界面
		 * @param context
		 * @param contentLayoutID
		 * @param noTitle
		 */
		public ContentView(Context context, int contentLayoutID,boolean noTitle) {
			super(context);
			this.context = context;
			this.noTitle = noTitle;
			this.contentLayoutID = contentLayoutID;
			init();
		}

		private void init() {
			View mainView = LayoutInflater.from(context).inflate(R.layout.base_view, this, true);
			LinearLayout relative_title = (LinearLayout) mainView.findViewById(R.id.relative_title);

			if (noTitle) {// 如果没有导航栏，隐藏布局
				relative_title.setVisibility(View.GONE);
			}

			/* 导航栏部分   start*/
			if (leftLayoutID != 0) {// 导航栏	左部分
				RelativeLayout relative_title_leftUI = (RelativeLayout) mainView.findViewById(R.id.relative_title_leftUI);
				LayoutInflater.from(context).inflate(leftLayoutID, relative_title_leftUI);
			}

			if (centerLayoutID != 0) {// 导航栏	中部分
				RelativeLayout relative_title_centerUI = (RelativeLayout) mainView.findViewById(R.id.relative_title_centerUI);
				LayoutInflater.from(context).inflate(centerLayoutID, relative_title_centerUI);
			}

			if (rightLayoutID != 0) {// 导航栏	右部分
				RelativeLayout relative_title_rightUI = (RelativeLayout) mainView.findViewById(R.id.relative_title_rightUI);
				LayoutInflater.from(context).inflate(rightLayoutID, relative_title_rightUI);
			}
			/* 导航栏部分   end*/

			/* 正文部分   start*/
			if (contentLayoutID != 0) {
				LinearLayout linear_contentView = (LinearLayout) mainView.findViewById(R.id.linear_contentView);
				LayoutInflater.from(context).inflate(contentLayoutID, linear_contentView);
			}
		}
	}

}
