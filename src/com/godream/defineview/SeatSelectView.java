package com.godream.defineview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;

import com.godream.R;
import com.godream.movieselectseat.Seat;
import com.godream.movieselectseat.SeatInfo;
import com.godream.movieselectseat.SeatManager;
import com.godream.movieselectseat.SeatRowNameInfo;

public class SeatSelectView extends SurfaceView {

	private static final int TOUCH_STATE_DOWN = 1;

	private static final int TOUCH_STATE_MOVIE = 2;

	private static final int TOUCH_STATE_MULIT = 3;

	private static final int TOUCH_STATE_NONE = 0;

	private SurfaceHolder mSurfaceHolder;

	private Paint paint;

	private final Handler mHandler = new Handler();

	private Bitmap seatGray;

	private Bitmap seatNormal;

	private Bitmap seatNormalSelect;

	private int numWidth = 40;

	private List<SeatRowNameInfo> rowNameList;

	private SeatInfo[] allSeats;

	private ArrayList<Seat> seats;

	private int maxX = 0;

	private int maxY = 0;

	int totleHeight = 0;

	int totleWidth = 0;

	private int touchState = 0;

	private float minScale = 1.0F;

	private int imgH;

	private int imgW;

	private SeatManager seatManager;

	private int bitmapPosX = 0;

	private int bitmapPosY = 0;

	private float lastX;

	private float lastY;

	private float lastFingerDistance = 0.0F;

	int moveDistanceX;

	int moveDistanceY;

	private final int seatSpace = 20;

	private Matrix matrix;

	private float scaleX = 1.0F;

	private float scaleY = 1.0F;

	private boolean firstIn = true;
	
	private boolean isSmallView;

	public SeatSelectView(Context context) {
		super(context);
		initView();

	}

	@Override
	public void startAnimation(Animation animation) {
		super.startAnimation(animation);
	}

	public SeatSelectView(Context context, AttributeSet paramAttributeSet) {
		super(context, paramAttributeSet);
		initView();
	}

	private Runnable mDrawRun = new Runnable() {

		public void run() {
			Canvas localCanvas = null;
			try {
				localCanvas = SeatSelectView.this.mSurfaceHolder.lockCanvas(null);
				synchronized (SeatSelectView.this.mSurfaceHolder) {
					SeatSelectView.this.doDraw(localCanvas);
					if (localCanvas != null) {
						SeatSelectView.this.mSurfaceHolder.unlockCanvasAndPost(localCanvas);
					}
					SeatSelectView.this.mHandler.removeCallbacks(SeatSelectView.this.mDrawRun);
					SeatSelectView.this.mHandler.postDelayed(SeatSelectView.this.mDrawRun, 50L);
					return;
				}
			}
			finally {
				// if (localCanvas != null)
				// MySurfaceView.this.mSurfaceHolder.unlockCanvasAndPost(localCanvas);
			}
		}
	};

	private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Log.i("surface", "surfaceChanged" + "width:" + width + ",heigth:" + height + "totleWidth:" + totleWidth + ",totleHeight:" + totleHeight);
			SeatSelectView.this.imgW = width;
			SeatSelectView.this.imgH = height;
			if (firstIn) {
				initSeatMap();
				firstIn = false;
			}
		}

		public void surfaceCreated(SurfaceHolder holder) {
			Log.i("surface", "surfaceCreated" + Thread.currentThread().getId() + ", " + Thread.currentThread().getName());
			SeatSelectView.this.mHandler.postDelayed(SeatSelectView.this.mDrawRun, 50L);
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			firstIn = false;
			SeatSelectView.this.mHandler.removeCallbacks(SeatSelectView.this.mDrawRun);
			System.gc();
			Log.i("surface", "surfaceDestroyed");
		}
	};

	private void initView() {
		this.mSurfaceHolder = getHolder();
		this.mSurfaceHolder.addCallback(this.surfaceCallback);
		this.mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
		setFocusable(true);
		setClickable(true);
		setLongClickable(false);

		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setTextSize(40.0F);

		this.seatNormal = BitmapFactory.decodeResource(getResources(), R.drawable.seat_able);
		this.seatNormalSelect = BitmapFactory.decodeResource(getResources(), R.drawable.seat_selected);
		this.seatGray = BitmapFactory.decodeResource(getResources(), R.drawable.seat_unable);

		this.matrix = new Matrix();
		this.matrix.setScale(this.scaleX, this.scaleY);
		this.numWidth = this.seatNormal.getWidth();

		setZOrderOnTop(true);// 设置画布 背景透明
		getHolder().setFormat(PixelFormat.TRANSPARENT);
	}

	/**
	 * 设置数据源
	 * 
	 * @param paramArrayList
	 * @param paramSeatRowNameInfo
	 */
	public void setManager(SeatManager seatmanager) {
		this.seatManager = seatmanager;
		this.rowNameList = seatManager.getRowNameList();
		this.maxX = seatManager.getMaxX();
		this.maxY = seatManager.getMaxY();
		this.allSeats = this.seatManager.getAllSeats();
		initSeatMap();
	}

	/**
	 * 根据座位整体宽高进行缩放至和surfaceview大小一致
	 */
	public void initSeatMap() {
		if ((this.seatNormal != null) && (this.maxX > 0) && (this.maxY > 0)) {
			int i = this.seatNormal.getWidth();
			int j = this.seatNormal.getHeight();
			this.totleWidth = (i * (1 + this.maxX) + seatSpace * (2 + this.maxX) + this.numWidth);
			this.totleHeight = (j * (1 + this.maxY) + seatSpace * (2 + this.maxY));
			this.minScale = Math.min((float) this.imgW / (float) this.totleWidth, (float) this.imgH / (float) this.totleHeight);
			Log.i("surface", "initSeatMap" + "imgH:" + imgH + ",imgW:" + imgW + "imgW:" + totleWidth + ",totleHeight:" + totleHeight);
			this.scaleX = this.minScale;
			this.scaleY = this.minScale;
			Log.i("surface", "initSeatMap" + scaleX);
			this.matrix.setScale(this.scaleX, this.scaleY);
		}
	}

	/**
	 * 具体描画
	 * 
	 * @param paramCanvas
	 */
	private void doDraw(Canvas paramCanvas) {
		if (this.mSurfaceHolder == null || paramCanvas == null || allSeats == null) {
			return;
		}
		paint.reset();
		this.paint.setColor(getResources().getColor(R.color.white));
		paramCanvas.clipRect(0, 0, getWidth(), getHeight());
		paramCanvas.drawRect(0.0f, 0.0f, this.totleWidth, this.totleHeight, paint);
		paramCanvas.save();

		// 縮放
		Log.i("scale:", this.scaleX + ":" + this.scaleY);
		this.matrix.setScale(this.scaleX, this.scaleY);
		paramCanvas.setMatrix(this.matrix);

		SeatInfo[] arrayOfSeatInfo = this.allSeats;
		int numTatalHeight;
		int numTop;
		// 画线
		// if (this.maxX > 0) {
		// int k = 1 + this.maxX >> 1;
		// int m = 1 + this.maxY >> 1;
		// int n = 10 + (this.bitmapPosX + this.numWidth + k *
		// this.seatNormal.getWidth() + k * seatSpace);
		// int i1 = 10 + (this.bitmapPosY + +m * this.seatNormal.getHeight() + m
		// * seatSpace);
		// this.paint.setStrokeWidth(4.0F);
		// this.paint.setColor(getResources().getColor(R.color.red));
		// paramCanvas.drawLine(n, 0.0F, n, this.totleHeight, this.paint);//
		// 描画垂直线
		// paramCanvas.drawLine(this.bitmapPosX + this.numWidth, i1,
		// this.totleWidth, i1, this.paint);// 描画水平线
		// }

		// 画座位
		this.paint.setColor(getResources().getColor(R.color.black_color));
		for (int i = 0; i < arrayOfSeatInfo.length; i++) {
			drawSeats(paramCanvas, arrayOfSeatInfo[i]);
		}

		// 画左侧导航栏
		if ((this.rowNameList != null) && (this.rowNameList.size() > 0)) {
			this.paint.setColor(getResources().getColor(R.color.selector_gray));
			this.paint.setTextSize(40.0F);
			this.paint.setTextAlign(Paint.Align.CENTER);
			Paint.FontMetrics localFontMetrics = this.paint.getFontMetrics();
			numTatalHeight = (int) Math.ceil(-localFontMetrics.top + localFontMetrics.descent);
			numTop = (int) Math.abs(localFontMetrics.top);
			paramCanvas.drawRect(0.0F, 0.0F, this.numWidth, this.totleHeight, this.paint);

			for (Iterator<SeatRowNameInfo> iter = this.rowNameList.iterator(); iter.hasNext();) {
				SeatRowNameInfo localSeatRowNameInfo = iter.next();
				this.paint.setColor(getResources().getColor(R.color.black_color));
				int rowId = localSeatRowNameInfo.getRowId();
				int y = (this.seatNormal.getHeight() >> 1) + (this.bitmapPosY + rowId * this.seatNormal.getHeight() + seatSpace * (rowId + 1))
				        + (numTop - (numTatalHeight >> 1));
				paramCanvas.drawText(localSeatRowNameInfo.getRowName(), this.numWidth >> 1, y, this.paint);
			}
		}
		
		//描画框线
		if(isSmallView){
			 this.paint.setStrokeWidth(40.0F);
			 this.paint.setColor(getResources().getColor(R.color.yellow));
			 this.paint.setStyle(Style.STROKE);
			 paramCanvas.drawRect(seatManager.getMainX() , seatManager.getMainY() , seatManager.getMainWidth() , seatManager.getMainHeight(), this.paint);
		}
		 
		paramCanvas.restore();
		return;
	}

	private void drawSeats(Canvas paramCanvas, SeatInfo paramSeatInfo) {
		int i = paramSeatInfo.getStatus();
		switch (paramSeatInfo.getType()) {
			case 0:
				drawCommonSeat(paramCanvas, paramSeatInfo, i);
				break;
			// case 1:
			// break;
			// case 2:
			// drawCoupleLeftSeat(paramCanvas, paramSeatInfo, i);
			// break;
			// case 3:
			// drawCoupleRightSeat(paramCanvas, paramSeatInfo, i);
			// break;
			default:
				Log.d("", "未知的 座位状态值:" + paramSeatInfo.getType());
				break;
		}
	}

	private void drawCommonSeat(Canvas paramCanvas, SeatInfo paramSeatInfo, int paramStatus) {
		this.paint.setTextSize(40.0F);
		this.paint.setTextAlign(Paint.Align.CENTER);
		switch (paramStatus) {
			case SeatManager.STATUS_CAN_SELECT:
				int n = this.bitmapPosX + this.numWidth + this.seatNormal.getWidth() * paramSeatInfo.getX() + seatSpace * (1 + paramSeatInfo.getX());
				int i1 = this.bitmapPosY + this.seatNormal.getHeight() * paramSeatInfo.getY() + seatSpace * (1 + paramSeatInfo.getY());
				paramCanvas.drawBitmap(this.seatNormal, n, i1, this.paint);
				paramCanvas.drawText(paramSeatInfo.getSeatNum(), n + (this.seatNormal.getWidth() >> 1), i1 + (this.seatNormal.getHeight() >> 1),
				                     this.paint);
				break;
			case SeatManager.STATUS_SELECTED_BY_SELF:
				int k = this.bitmapPosX + this.numWidth + this.seatNormalSelect.getWidth() * paramSeatInfo.getX() + seatSpace
				        * (1 + paramSeatInfo.getX());
				int m = this.bitmapPosY + this.seatNormalSelect.getHeight() * paramSeatInfo.getY() + seatSpace * (1 + paramSeatInfo.getY());
				paramCanvas.drawBitmap(this.seatNormalSelect, k, m, this.paint);

				break;
			case SeatManager.STATUS_SELECTED_BY_OTHERS:
				int i = this.bitmapPosX + this.numWidth + this.seatGray.getWidth() * paramSeatInfo.getX() + seatSpace * (1 + paramSeatInfo.getX());
				int j = this.bitmapPosY + this.seatNormalSelect.getHeight() * paramSeatInfo.getY() + seatSpace * (1 + paramSeatInfo.getY());
				paramCanvas.drawBitmap(this.seatGray, i, j, this.paint);
				break;
			default:
				break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				this.touchState = TOUCH_STATE_DOWN;
				this.lastX = event.getRawX();
				this.lastY = event.getRawY();
				moveDistanceX = 0;
				moveDistanceY = 0;
				this.seatManager.isMoving(SeatManager.IS_MOVING);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				if (event.getPointerCount() == 2) {
					// 第二根手指按下屏幕，记录此时两根手指之间的距离
					this.lastFingerDistance = distanceBetweenFingers(event);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				this.seatManager.isMoving(SeatManager.IS_MOVING);
				onActionMove(event);
				caleSize();
				break;
			case MotionEvent.ACTION_UP:
				switch (this.touchState) {
					case TOUCH_STATE_MULIT:
						this.touchState = TOUCH_STATE_NONE;
						break;
					case TOUCH_STATE_DOWN:
						setSeatIsSelect(event);
						break;
					case TOUCH_STATE_MOVIE:
						break;
					default:
						Log.d("", "未处理的按下状态：" + this.touchState);
						break;
				}
				
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}

	private void onActionMove(MotionEvent event) {
		
		int offsetX = (int) ((event.getRawX() - this.lastX) / this.scaleX);
		int offsetY = (int) ((event.getRawY() - this.lastY) / this.scaleY);
		moveDistanceX += offsetX;
		moveDistanceY += offsetY;

		if (event.getPointerCount() == 1) {
			// 单点触控:点击和移动两种情况
			if (Math.abs(moveDistanceX) <= 5 && Math.abs(moveDistanceY) <= 5) {
				touchState = TOUCH_STATE_DOWN;// 手指移动距离小于5，认为是点击
			} else {
				touchState = TOUCH_STATE_MOVIE;// 手指移动距离大于5，认为是移动
			}
		} else if (event.getPointerCount() >= 2) {
			// 多点触控:移动和缩放两种情况
			this.touchState = TOUCH_STATE_MULIT;

			float fingerDistance = distanceBetweenFingers(event);// 两指间的距离
			float scaledRatio = fingerDistance / this.lastFingerDistance;// 移动距离比例
			scaleX = this.scaleX * scaledRatio;
			this.lastFingerDistance = fingerDistance;

			if (scaleX < this.minScale) {
				scaleX = this.minScale;
			}
			if (scaleX > 1.5F) {
				scaleX = 1.5F;
			}
			this.scaleY = scaleX;
		}
		this.lastX = event.getRawX();
		this.lastY = event.getRawY();
		movieLimition(offsetX, offsetY);
		
	}
	
	/**
	 * 计算当前位置,给缩略图提供参数
	 */
	private void caleSize(){
		seatManager.setMainX(- (float)bitmapPosX);
		seatManager.setMainY(- (float)bitmapPosY);
		seatManager.setMainWidth(getWidth() / scaleX  - (float)bitmapPosX);
		seatManager.setMainHeight(getHeight() / scaleY - (float)bitmapPosY);
		Log.i("zuobiao", "X:" + - (float)bitmapPosX + ",Y:" + - (float)bitmapPosY + ",Width:" + (getWidth() / scaleX  - bitmapPosX) + ",Height:" + (getHeight() / scaleY - bitmapPosY));
	}

	/**
	 * 计算两个手指之间的距离。
	 * 
	 * @param event
	 * @return 两个手指之间的距离
	 */
	private float distanceBetweenFingers(MotionEvent event) {
		if (event.getPointerCount() > 1) {
			float disX = Math.abs(event.getX(0) - event.getX(1));
			float disY = Math.abs(event.getY(0) - event.getY(1));
			return (float) Math.sqrt(disX * disX + disY * disY);
		}
		return 0;
	}

	/**
	 * 控制移动过程中移出上下左右边界
	 * 
	 * @param offsetX
	 * @param offsetY
	 */
	private void movieLimition(int offsetX, int offsetY) {
		this.bitmapPosX += offsetX;
		this.bitmapPosY += offsetY;
		if (bitmapPosX > 0) {
			bitmapPosX = 0;
		}
		if ((float) (bitmapPosX) * scaleX + (float) (totleWidth) * scaleX < (float) getWidth()) {
			if ((float) totleWidth * scaleX < (float) getWidth()) {
				// 缩放后的view在屏幕内完全显示
				bitmapPosX = 0;
			} else {
				// view放大至比屏幕要大
				bitmapPosX = (int) (-((float) totleWidth - (float) getWidth() / scaleX));
			}
		}
		if (bitmapPosY > 0) {
			bitmapPosY = 0;
		}
		if ((float) (bitmapPosY) * scaleY + (float) (totleHeight) * scaleY < (float) getHeight()) {
			if ((float) totleHeight * scaleY < (float) getHeight()) {
				bitmapPosY = 0;
			} else {
				bitmapPosY = (int) (-((float) totleHeight - (float) getHeight() / scaleY));
			}
		}
		return;
	}

	/**
	 * 点击了某个座位
	 * 
	 * @param paramMotionEvent
	 */
	private void setSeatIsSelect(MotionEvent paramMotionEvent) {
		SeatInfo localSeatInfo1 = getSeatPos(paramMotionEvent.getX(), paramMotionEvent.getY());
		if (localSeatInfo1 != null) {
			int i = this.seatManager.chooseSeat(localSeatInfo1);
		}
	}

	/**
	 * 根据手中点击屏幕的坐标值确定点击的SeatInfo
	 * 
	 * @param paramX
	 * @param paramY
	 * @return
	 */
	public SeatInfo getSeatPos(float paramX, float paramY) {
		boolean bool = isInRect(paramX, paramY, 0, 0, this.numWidth, (int) this.totleHeight);
		SeatInfo localSeatInfo = null;
		SeatInfo[] arrayOfSeatInfo;
		if (bool) {
			// 点击在左侧行数栏中
			return localSeatInfo;
		} else {
			arrayOfSeatInfo = this.allSeats;
			SeatInfo seatInfo;
			for (int i = 0; i < arrayOfSeatInfo.length; i++) {
				seatInfo = arrayOfSeatInfo[i];
				switch (seatInfo.getType()) {
					case 0:
						int paramLeft = (int) ((this.bitmapPosX + this.numWidth + this.seatNormal.getWidth() * seatInfo.getX() + seatSpace
						        * (1 + seatInfo.getX())) * this.scaleX);
						int paramTop = (int) ((this.bitmapPosY + this.seatNormalSelect.getHeight() * seatInfo.getY() + seatSpace
						        * (1 + seatInfo.getY())) * this.scaleY);
						int paramWidth = (int) (this.seatNormal.getWidth() * this.scaleX);
						int paramHeight = (int) (this.seatNormal.getHeight() * this.scaleY);
						if (isInRect(paramX, paramY, paramLeft, paramTop, paramWidth, paramHeight)) {
							localSeatInfo = seatInfo;
							return localSeatInfo;
						}
						break;

					default:
						break;
				}
			}
			return localSeatInfo;
		}
	}

	/**
	 * 是否在指定区域内
	 * 
	 * @param paramX
	 * @param paramY
	 * @param paramLeft
	 * @param paramTop
	 * @param paramWidth
	 * @param paramHeight
	 * @return
	 */
	public boolean isInRect(float paramX, float paramY, int paramLeft, int paramTop, int paramWidth, int paramHeight) {
		int x = (int) paramX;
		int y = (int) paramY;
		return (x > paramLeft) && (x < paramLeft + paramWidth) && (y > paramTop) && (y < paramTop + paramHeight);
	}

	public void setFocus(boolean isFocusable) {
		setFocusable(isFocusable);
		setClickable(isFocusable);
	}

	
    public boolean isSmallView() {
    	return isSmallView;
    }

	
    public void setSmallView(boolean isSmallView) {
    	this.isSmallView = isSmallView;
    }

}
