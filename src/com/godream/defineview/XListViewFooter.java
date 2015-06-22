package com.godream.defineview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.godream.R;

public class XListViewFooter extends LinearLayout
{
  public static final int STATE_LOADING = 2;
  public static final int STATE_NORMAL = 0;
  public static final int STATE_READY = 1;
  private View mContentView;
  private Context mContext;
  private TextView mHintView;
  private ImageView mProgressBar;

  public XListViewFooter(Context paramContext)
  {
    super(paramContext);
    initView(paramContext);
  }

  public XListViewFooter(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  private void initView(Context paramContext)
  {
    this.mContext = paramContext;
    LinearLayout localLinearLayout = (LinearLayout)LayoutInflater.from(this.mContext).inflate(R.layout.xlistview_footer, null);
    addView(localLinearLayout);
    localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    this.mContentView = localLinearLayout.findViewById(R.id.xlistview_footer_content);
    this.mProgressBar = ((ImageView)localLinearLayout.findViewById(R.id.xlistview_footer_progressbar));
    this.mHintView = ((TextView)localLinearLayout.findViewById(R.id.xlistview_footer_hint_textview));
    AnimationDrawable localAnimationDrawable = new AnimationDrawable();
    localAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.loading1), 150);
    localAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.loading2), 150);
    localAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.loading3), 150);
    localAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.loading4), 150);
    localAnimationDrawable.setOneShot(false);
    this.mProgressBar.setBackgroundResource(R.drawable.loading_bg);
    this.mProgressBar.setImageDrawable(localAnimationDrawable);
  }

  public int getBottomMargin()
  {
    return ((LinearLayout.LayoutParams)this.mContentView.getLayoutParams()).bottomMargin;
  }

  public void hide()
  {
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.mContentView.getLayoutParams();
    localLayoutParams.height = 0;
    this.mContentView.setLayoutParams(localLayoutParams);
  }

  public void loading()
  {
    this.mHintView.setVisibility(View.GONE);
    this.mProgressBar.setVisibility(View.VISIBLE);
  }

  public void normal()
  {
    this.mHintView.setVisibility(View.VISIBLE);
    this.mProgressBar.setVisibility(View.GONE);
  }

  public void setBottomMargin(int paramInt)
  {
    if (paramInt < 0)
      return;
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.mContentView.getLayoutParams();
    localLayoutParams.bottomMargin = paramInt;
    this.mContentView.setLayoutParams(localLayoutParams);
  }

  public void setState(int paramInt)
  {
    if (paramInt == STATE_READY)
    {
      //松开加载更多
      this.mProgressBar.setVisibility(View.VISIBLE);
      this.mHintView.setText(R.string.xlistview_footer_hint_ready);
      return;
    }
    if (paramInt == STATE_LOADING)
    {
      //正在加载
      this.mProgressBar.setVisibility(View.VISIBLE);
      this.mHintView.setText(R.string.xlistview_header_hint_loading);
      return;
    }
    
    //加载更多
    this.mProgressBar.setVisibility(View.GONE);
    this.mHintView.setText(R.string.xlistview_footer_hint_normal);
  }

  public void show()
  {
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.mContentView.getLayoutParams();
    localLayoutParams.height = -2;
    this.mContentView.setLayoutParams(localLayoutParams);
  }
}