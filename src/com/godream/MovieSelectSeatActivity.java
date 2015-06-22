package com.godream;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.godream.R;
import com.godream.defineview.SeatSelectView;
import com.godream.movieselectseat.Seat;
import com.godream.movieselectseat.SeatInfo;
import com.godream.movieselectseat.SeatManager;
import com.godream.movieselectseat.SeatRowNameInfo;
import com.godream.movieselectseat.SeatSelectListener;

public class MovieSelectSeatActivity extends Activity implements SeatSelectListener,OnClickListener {

	public static final int SHOW_FLAG = 0;

	public static final int HIDDEN_FLAG = 1;
	
	public static final int LIMIT_TICKET = 4;

	/**
	 * 服务器端座位集合
	 */
	private List<Seat> seatList;

	/**
	 * 每一排的名称
	 */
	private List<SeatRowNameInfo> seatRowNameList;

	/**
	 * 选座view
	 */
	private SeatSelectView mMainSurfaceView;

	/**
	 * 缩略图
	 */
	private SeatSelectView mSamllSurfaceView;
	
	/**
	 * 显示剩余座位信息区域
	 */
	private RelativeLayout mSeatMsgLayout;

	/**
	 * 显示剩余座位信息
	 */
	private TextView mSeatMsg;

	/**
	 * 显示选择座位信息区域
	 */
	private LinearLayout mSelectSeatMsgLayout;

	/**
	 * 显示选择座位信息
	 */
	private LinearLayout mSelectSeatLayout;

	/**
	 * 确认购买按钮
	 */
	private Button mSubmitOrderBtn;

	/**
	 * 电影票单价
	 */
	private float unitPrice;

	/**
	 * 座位管理类
	 */
	private SeatManager seatmanager;
	
	private LayoutInflater inflate;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_FLAG:
					mSamllSurfaceView.setVisibility(View.VISIBLE);
					handler.sendMessageDelayed(handler.obtainMessage(HIDDEN_FLAG), 1000);
					break;
				case HIDDEN_FLAG:
					mSamllSurfaceView.setVisibility(View.GONE);
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_movie_select_seat);
		seatRowNameList = new ArrayList<SeatRowNameInfo>();
		seatList = new ArrayList<Seat>();
		seatmanager = new SeatManager(this);
		inflate = LayoutInflater.from(this);
		initView();
		getDatas();

	}

	private void initView() {
		mMainSurfaceView = (SeatSelectView) findViewById(R.id.mainsurfaceview);
		mSamllSurfaceView = (SeatSelectView) findViewById(R.id.smallsurfaceview);
		mSamllSurfaceView.setFocus(false);
		mSamllSurfaceView.setSmallView(true);
		mSeatMsgLayout = (RelativeLayout) findViewById(R.id.rl_seatmsg);
		mSeatMsg = (TextView) findViewById(R.id.seatmsg);
		mSelectSeatMsgLayout = (LinearLayout) findViewById(R.id.ll_select_seatmsg);
		mSelectSeatLayout = (LinearLayout) findViewById(R.id.ll_selectseat);
		mSubmitOrderBtn = (Button) findViewById(R.id.submitorder);
		mSubmitOrderBtn.setOnClickListener(this);
		((TextView) findViewById(R.id.theatrename)).setText("金逸影城苏州文化宫店");// 影院
		((TextView) findViewById(R.id.filmname)).setText("无人区");// 电影名称
		((TextView) findViewById(R.id.screenname)).setText("2号厅");// 电影场次
		((TextView) findViewById(R.id.filmdate)).setText("今天(12月9日)");// 电影日期
		((TextView) findViewById(R.id.filmtime)).setText("12:29");// 电影时间
		mSeatMsg.setText("共XX个座位，剩余XX个座位");
	}

	@Override
	public void onSelect(SeatInfo paramSeatInfo, int paramInt) {
		switch (paramInt) {
			case SeatManager.SEAT_SELECTED:
				mSamllSurfaceView.setVisibility(View.VISIBLE);
				handler.removeMessages(HIDDEN_FLAG);
				handler.handleMessage(handler.obtainMessage(SHOW_FLAG));
				addSelectSeats(paramSeatInfo);
				break;
			case SeatManager.SEAT_SELECTED_CANCEL:
				handler.removeMessages(HIDDEN_FLAG);
				handler.handleMessage(handler.obtainMessage(SHOW_FLAG));
				deleteSelectSeats(paramSeatInfo);
				break;
			case SeatManager.STATUS_SELECTED_BY_OTHERS:
				handler.removeMessages(HIDDEN_FLAG);
				handler.handleMessage(handler.obtainMessage(SHOW_FLAG));
				ShowMessage(SeatManager.STATUS_SELECTED_BY_OTHERS);
				break;
			case SeatManager.STATUS_NOT_SEAT:
				handler.removeMessages(HIDDEN_FLAG);
				handler.handleMessage(handler.obtainMessage(SHOW_FLAG));
				ShowMessage(SeatManager.STATUS_NOT_SEAT);
				break;
			case SeatManager.SELECTED_SIZE_REACH_LIMIT:
				handler.removeMessages(HIDDEN_FLAG);
				handler.handleMessage(handler.obtainMessage(SHOW_FLAG));
				ShowMessage(SeatManager.SELECTED_SIZE_REACH_LIMIT);
			default:
				break;
		}
	}

	@Override
	public void isMoving(int type) {
		switch (type) {
			case SeatManager.IS_MOVING:
				handler.removeMessages(HIDDEN_FLAG);
				handler.handleMessage(handler.obtainMessage(SHOW_FLAG));
				break;
			default:
				break;
		}
	}
	

	private void addSelectSeats(SeatInfo paramSeatInfo) {
		mSeatMsgLayout.setVisibility(View.GONE);
		mSelectSeatMsgLayout.setVisibility(View.VISIBLE);
		
		View itemView = inflate.inflate(R.layout.select_seat_item, null);
		itemView.setTag(paramSeatInfo.getSeatId());
		itemView.setOnClickListener(this);
		
		TextView mSeat = (TextView) itemView.findViewById(R.id.selectseat);
		mSeat.setText(paramSeatInfo.getSeatName());
		mSeat.setGravity(Gravity.CENTER);
		
		mSelectSeatLayout.addView(itemView, mSelectSeatLayout.getChildCount());
		mSelectSeatLayout.setGravity(Gravity.CENTER);
		mSubmitOrderBtn.setText("立即购买 | ￥" + calcTicketPrice());
	}

	private void deleteSelectSeats(SeatInfo paramSeatInfo) {
		int selectCount = mSelectSeatLayout.getChildCount();
		for (int i = 0; i < selectCount; i++) {
			RelativeLayout mSeat = (RelativeLayout) mSelectSeatLayout.getChildAt(i);
			if (((Integer) mSeat.getTag()) == paramSeatInfo.getSeatId()) {
				mSelectSeatLayout.removeView(mSeat);
				mSubmitOrderBtn.setText("立即购买 | ￥" + calcTicketPrice());
				break;
			}
		}
		setNormal();
	}

	private float calcTicketPrice() {
		int ticketCount = seatmanager.getSelectSeats().size();
		float price = ticketCount * unitPrice;
		price = (float) (Math.round(price * 100)) / 100;
		return price;
	}

	private void ShowMessage(int type){
		switch(type){
			case SeatManager.STATUS_SELECTED_BY_OTHERS:
				Toast.makeText(MovieSelectSeatActivity.this, "该座位已被别人预定，请选择其他座位", Toast.LENGTH_SHORT).show();
				break;
			case SeatManager.STATUS_NOT_SEAT:
				Toast.makeText(MovieSelectSeatActivity.this, "这里不是座位", Toast.LENGTH_SHORT).show();
				break;
			case SeatManager.SELECTED_SIZE_REACH_LIMIT:
				Toast.makeText(MovieSelectSeatActivity.this, "一个订单一次只能选择" + LIMIT_TICKET + "张座位票", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
	}
	
	private void getDatas() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				getData();
				seatmanager.setData(seatList, LIMIT_TICKET, seatRowNameList);
				mMainSurfaceView.setManager(seatmanager);
				mSamllSurfaceView.setManager(seatmanager);
			}
		}).start();
	}

	private void getData() {
		for (int i = 0; i < 9; i++) {
			SeatRowNameInfo info = new SeatRowNameInfo();
			info.setRowId(i);
			if (i > 4) {
				info.setRowName(String.valueOf((i)));
			} else if (i == 4) {
				info.setRowName("");
			} else {
				info.setRowName(String.valueOf((i + 1)));
			}
			seatRowNameList.add(info);
		}
		int id = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 10; j++) {
				Seat seat = new Seat(id, String.valueOf(j + 1), j, i);
				if (i == 4) {
					seat.setId(0);
				}
				seat.setStatus(true);
				if (i == 5 || j == 6) {
					seat.setStatus(false);
				}
				seat.setType(0);
				seat.setSeatNumber(String.valueOf(j + 1));
				seat.setName(i + "排" + j + "列");
				seatList.add(seat);
				id++;
			}
		}
	}

	private void setNormal(){
		if(mSelectSeatLayout.getChildCount() == 0){
			mSeatMsgLayout.setVisibility(View.VISIBLE);
			mSelectSeatMsgLayout.setVisibility(View.GONE);
			mSubmitOrderBtn.setText("立即购买");
		}
	}
	
	private void checkOrder(){
		if(seatmanager.getSelectSeats().size()>0){
			Toast.makeText(MovieSelectSeatActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(MovieSelectSeatActivity.this, "请选座位", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
    public void onClick(View v) {
		if(v.getParent() == mSelectSeatLayout){
			SeatInfo seatInfo = seatmanager.getSeatInfoById((Integer)v.getTag());
			seatInfo.setStatus(SeatManager.STATUS_CAN_SELECT);
			mSelectSeatLayout.removeView(v);
			seatmanager.getSelectSeats().remove(seatInfo);
			setNormal();
		}else{
			switch(v.getId()){
				case R.id.submitorder:
					checkOrder();
					break;
				default:
					break;
			}
		}
    }
}
