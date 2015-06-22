package com.godream.defineview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.godream.R;
import com.godream.common.Constant;
import com.godream.util.BitmapHelper;
import com.godream.util.CacheTools;
import com.godream.util.LogUtil;

/**
*
* 类名称：FingerImageView
* 
* 类描述：自定义imageview
* 
* 创建人：lipeng
* 
* 创建时间：2013-10-22 下午1:44:28
* 
* 备注：
*
*/
public class FingerImageView extends ImageView {
	private int backColor;//背景色
	private String text;//图的文字
	private int textColor;//文字色
	private float textSize;//文字大小
	private float text_x;//文字x坐标
	private float text_y;//文字y坐标
	private int backImage;//图中的图片
	private float image_x;//图片的x坐标
	private float image_y;//图片的y坐标
	private int height;//1:宽高一致 2：高度为宽度的一半 -1：使用自定义的高度
	
	private Bitmap bitmap_finger;//按下后的手指效果
	private Bitmap bitmap_back;//图片
	
	private Paint paint_Text;
	private Paint paint_Image;

	private ScaleAnimation animation_start;//按下后缩放的效果
	private ScaleAnimation animation_end;
	
	private boolean showFinger;
	
	public FingerImageView(Context context) {
		super(context);
	}

	public FingerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				com.godream.R.styleable.finger);
		
		backColor = typedArray.getColor(R.styleable.finger_backcolor,R.color.gray);
		text = typedArray.getString(R.styleable.finger_text);
		textSize = typedArray.getDimension(R.styleable.finger_textSize, -1.0f);
		if(textSize == -1.0f){
			textSize = 20.0f;
		}
		textColor = typedArray.getColor(R.styleable.finger_textColor, R.color.white);
		text_x = typedArray.getFloat(R.styleable.finger_text_x, -1.0f);
		text_y = typedArray.getFloat(R.styleable.finger_text_y, -1.0f);
		backImage = typedArray.getResourceId(R.styleable.finger_image, 0);
		image_x = typedArray.getFloat(R.styleable.finger_image_x, -1.0f);
		image_y = typedArray.getFloat(R.styleable.finger_image_y, -1.0f);
		height = typedArray.getInt(R.styleable.finger_height,-1);
		initView();
	}

	private void initView() {
		bitmap_finger = CacheTools.getFromLruCache(Constant.FINGER_IMAGE);
		if (bitmap_finger == null) {
			bitmap_finger = BitmapFactory.decodeResource(getResources(),
					R.drawable.fingerprint, null);
			CacheTools.addToLruCache(Constant.FINGER_IMAGE, bitmap_finger);
		}
		paint_Text = new Paint();
		paint_Text.setColor(textColor);
		paint_Text.setAntiAlias(true);
		
		paint_Text.setTextSize(BitmapHelper.dip2px(getContext(), textSize));
		LogUtil.getInstance().i(text + ":" + paint_Text.getTextSize());
		paint_Text.setTextAlign(Paint.Align.LEFT);
		
		paint_Image = new Paint();
		paint_Image.setDither(true);
		paint_Image.setAntiAlias(true);
		
		float start = 1.0f;
		float end = 0.95f;
		animation_start = new ScaleAnimation(start, end, start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation_start.setDuration(200);
		animation_start.setFillAfter(true);
		animation_end = new ScaleAnimation(end, start, end, start, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation_end.setDuration(200);
		animation_end.setFillAfter(true);
		
		if(backImage != 0){
			bitmap_back = CacheTools.getFromLruCache(String.valueOf(backImage));
			if (bitmap_back == null) {
			bitmap_back = BitmapFactory.decodeResource(getResources(),
					backImage, null);
				CacheTools.addToLruCache(String.valueOf(backImage), bitmap_back);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		   LogUtil.getInstance().i(text + ":width-" + getMeasuredWidth());
		   if(height == 1){
			   //宽度高度一致
			   setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
		   }else if(height == 2){
			   //高度为宽度的一半,由于图片间隔是4.5,所以在去除一半高度时，两个半个高度加起来要比一张图高出4.5，故减去2.25，此处需根据具体使用情况考虑变换，这里只适用与本次情况
			   setMeasuredDimension(getMeasuredWidth(), Math.round(getMeasuredWidth() / 2 - BitmapHelper.dip2px(getContext(), 2.25F)));
		   }else{
			   //高度自定义
			   setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
		   }
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(backColor);
		
		if(!"".equals(text) && text != null)
			if(text_x == -1.0f || text_y == -1.0f){
				canvas.drawText(text, BitmapHelper.dip2px(getContext(), 56.0F), 0.6F * getHeight(), paint_Text);
			}else{
				canvas.drawText(text, BitmapHelper.dip2px(getContext(), text_x), BitmapHelper.dip2px(getContext(), text_y), paint_Text);
			}
		if(bitmap_back != null){
			if(image_x == -1.0f || image_y == -1.0f){
				canvas.drawBitmap(bitmap_back, (getWidth() - this.bitmap_back.getWidth()) / 2, (getHeight() - this.bitmap_back.getHeight()) / 2, paint_Image);
			}else{
				canvas.drawBitmap(bitmap_back, image_x, image_y, paint_Image);
			}
		}
		if (showFinger)
        	canvas.drawBitmap(this.bitmap_finger, (getWidth() - this.bitmap_finger.getWidth()) / 2, (getHeight() - this.bitmap_finger.getHeight()) / 2, paint_Image);
	}
	public boolean onTouchEvent(MotionEvent paramMotionEvent)
	  {
		switch (paramMotionEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			showFinger = true;
			invalidate();
			this.startAnimation(animation_start);
			break;
		case MotionEvent.ACTION_UP:
			showFinger = false;
			invalidate();
			this.startAnimation(animation_end);
			performClick();
//			if(listener!=null){
//				listener.onclick();
//			}
			break;
		// 滑动出去不会调用action_up,调用action_cancel
		case MotionEvent.ACTION_CANCEL:
			showFinger = false;
			invalidate();
			this.startAnimation(animation_end);
			break;
		}
		// 不返回true，Action_up就响应不了
		return true;
	  }
	
	public String getText(){
		return text;
	}
	
	
}
