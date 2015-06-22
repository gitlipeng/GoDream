package com.godream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.godream.defineview.NewUserGuideScrollView;

public class NewUserGuiderActivity extends SuperActivity
{
  private LinearLayout newUserGuideLayout;
  private NewUserGuideScrollView newUserGuideScrollView;
  private TextView newUserGuideTitleView;
  private Handler handler = new Handler(){
	public void handleMessage(android.os.Message msg) {};  
  };
  public static void open(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(paramContext, NewUserGuiderActivity.class);
    paramContext.startActivity(localIntent);
  }


  protected void onCreate(Bundle paramBundle)
  {
	super.onCreate(paramBundle);
    setContentView(R.layout.animationfragment);
    this.newUserGuideScrollView = ((NewUserGuideScrollView)findViewById(R.id.newUserGuideScrollView));
    this.newUserGuideScrollView.setHandler(this.handler);
    this.newUserGuideTitleView = ((TextView)findViewById(R.id.newUserGuideTitleView));
    this.newUserGuideTitleView.setText("旅游攻略 ");
  }

  protected void onResume()
  {
    super.onResume();
  }
}