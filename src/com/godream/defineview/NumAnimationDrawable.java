package com.godream.defineview;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.godream.R;
import com.godream.util.LogUtil;

public class NumAnimationDrawable extends Drawable {
	private Bitmap bitmap;
	private ArrayList<Integer> resId = new ArrayList<Integer>();
	private Paint paint;
	private Resources res;
	private Rect dst;
	private Rect src;
	private Handler mHandler;
	private NumAnimationListener mEndListener;

	public NumAnimationDrawable(Context context, Handler handler) {
		resId.add(R.drawable.num_1);
		resId.add(R.drawable.num_2);
		resId.add(R.drawable.num_3);
		resId.add(R.drawable.num_4);
		resId.add(R.drawable.num_5);
		resId.add(R.drawable.num_6);
		resId.add(R.drawable.num_7);
		resId.add(R.drawable.num_8);
		resId.add(R.drawable.num_9);
		resId.add(R.drawable.num_10);
		resId.add(R.drawable.num_11);
		resId.add(R.drawable.num_12);
		resId.add(R.drawable.num_13);
		resId.add(R.drawable.num_14);
		resId.add(R.drawable.num_15);
		resId.add(R.drawable.num_16);
		resId.add(R.drawable.num_17);
		resId.add(R.drawable.num_18);
		resId.add(R.drawable.num_19);
		resId.add(R.drawable.num_20);
		resId.add(R.drawable.num_21);
		resId.add(R.drawable.num_22);
		resId.add(R.drawable.num_23);
		resId.add(R.drawable.num_24);
		resId.add(R.drawable.num_25);
		resId.add(R.drawable.num_26);
		resId.add(R.drawable.num_27);
		resId.add(R.drawable.num_28);
		resId.add(R.drawable.num_29);
		resId.add(R.drawable.num_30);
		resId.add(R.drawable.num_31);
		resId.add(R.drawable.num_32);
		resId.add(R.drawable.num_33);
		resId.add(R.drawable.num_34);
		resId.add(R.drawable.num_35);
		resId.add(R.drawable.num_36);
		resId.add(R.drawable.num_37);
		resId.add(R.drawable.num_38);
		resId.add(R.drawable.num_39);
		resId.add(R.drawable.num_40);

		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.res = context.getResources();
		this.mHandler = handler;

	}

	@Override
	public void draw(Canvas canvas) {
		Rect localRect = canvas.getClipBounds();
		if (this.bitmap != null) {
			if (this.src == null) {
				this.src = new Rect();
				this.src.left = 0;
				this.src.top = 0;
				this.src.right = bitmap.getWidth();
				this.src.bottom = bitmap.getHeight();
			}
			canvas.drawBitmap(bitmap, this.src, localRect, paint);
		}
	}

	public Bitmap getBitmap() {
		return this.bitmap;
	}

	@Override
	public int getOpacity() {
		return 0;
	}

	public void reset() {
		try {
			Bitmap localBitmap = BitmapFactory.decodeResource(res,
					this.resId.get(0));
			if (localBitmap != null) {
				setBitmap(localBitmap, 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void setAlpha(int alpha) {

	}

	public void setAnimationListener(
			NumAnimationListener paramNumAnimationListener) {
		this.mEndListener = paramNumAnimationListener;
	}

	public void setBitmap(Bitmap paramBitmap, int paramInt) {
		this.bitmap = paramBitmap;
		if (this.mEndListener != null)
			this.mEndListener.onAnimationChange(paramInt);
		this.mHandler.post(new Runnable() {
			@Override
			public void run() {
				NumAnimationDrawable.this.invalidateSelf();
			}
		});
	}

	@Override
	public void setColorFilter(ColorFilter cf) {

	}

	public void start(boolean paramBoolean) {
		new AnimationThread(paramBoolean).start();
	}

	class AnimationThread extends Thread {
		int curIndex = 0;
		boolean isReverse;

		public AnimationThread(boolean arg2) {
			boolean bool = false;
			this.isReverse = bool;
			if (bool) {
				this.curIndex = (-1 + NumAnimationDrawable.this.resId.size());
			} else {
				this.curIndex = 0;
			}
		}

		@Override
		public void run() {
			try {
				for (this.curIndex = 0; (this.curIndex < NumAnimationDrawable.this.resId
						.size()); this.curIndex++) {
					Bitmap localBitmap = BitmapFactory.decodeResource(
							NumAnimationDrawable.this.res,
							((Integer) NumAnimationDrawable.this.resId
									.get(this.curIndex)).intValue());
					if (localBitmap != null) {
						NumAnimationDrawable.this.setBitmap(localBitmap,
								this.curIndex);
					}
					try {
						Thread.sleep(15L);
					} catch (Exception localException) {
					}
				}
				if (isReverse) {
					if (NumAnimationDrawable.this.mEndListener != null)
						NumAnimationDrawable.this.mEndListener.onAnimationEnd();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public static abstract interface NumAnimationListener {
		public abstract void onAnimationChange(int paramInt);

		public abstract void onAnimationEnd();
	}

}
