package com.godream.util;

import com.godream.MainApplication;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public final class CacheTools
{
  public static final SharedPreferences mySharedPreference = MainApplication.getContext().getSharedPreferences("QunarPreferences", 0);
  public static final LruCache<String, Bitmap> cache = new LruCache(409600);


  public static void removeFromSharedPreferences(String paramString)
  {
    if (paramString == null)
      return;
    SharedPreferences.Editor localEditor = mySharedPreference.edit();
    localEditor.remove(paramString);
    localEditor.commit();
  }

  public static void addToSharedPreferences(String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = mySharedPreference.edit();
    localEditor.putInt(paramString, paramInt);
    localEditor.commit();
  }

  public static void addToSharedPreferences(String paramString, long paramLong)
  {
    SharedPreferences.Editor localEditor = mySharedPreference.edit();
    localEditor.putLong(paramString, paramLong);
    localEditor.commit();
  }

  public static void addToLruCache(String paramString, Bitmap paramBitmap)
  {
    cache.put(paramString, paramBitmap);
  }

  public static void addToSharedPreferences(String paramString, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = mySharedPreference.edit();
    localEditor.putBoolean(paramString, paramBoolean);
    localEditor.commit();
  }

  public static int getFromSharedPreferences(String paramString, int paramInt)
  {
    try
    {
      int i = mySharedPreference.getInt(paramString, paramInt);
      return i;
    }
    catch (Exception localException)
    {
    }
    return paramInt;
  }

  public static long getFromSharedPreferences(String paramString)
  {
    try
    {
      long l = mySharedPreference.getLong(paramString, 0L);
      return l;
    }
    catch (Exception localException)
    {
    }
    return 0L;
  }

  public static String getFromSharedPreferences(String paramString1, String paramString2)
  {
    try
    {
      String str = mySharedPreference.getString(paramString1, paramString2);
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString2;
  }

  public static boolean getFromSharedPreferences(String paramString, boolean paramBoolean)
  {
    try
    {
      boolean bool = mySharedPreference.getBoolean(paramString, paramBoolean);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return paramBoolean;
  }

  public static Bitmap getFromLruCache(String paramString)
  {
    if (paramString == null)
      return null;
    return (Bitmap)cache.get(paramString);
  }

}
