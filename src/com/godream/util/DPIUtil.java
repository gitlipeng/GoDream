package com.godream.util;

import com.godream.common.Common;

import android.content.Context;
import android.view.Display;

public class DPIUtil
{
  private static Display defaultDisplay;

  public static int dip2px(float paramFloat)
  {
    return (int)(0.5F + paramFloat * Common._Density);
  }

  public static int px2dip(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat / Common._Density);
  }
}