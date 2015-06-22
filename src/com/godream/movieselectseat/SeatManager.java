package com.godream.movieselectseat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SeatManager {

	
	private SeatInfo[] arrAllSeats;

	private int columnCount;

	private int limitCount;

	public ArrayList<SeatInfo> selectedSeatList;

	public static final int STATUS_CAN_SELECT = 1;

	public static final int STATUS_NOT_SEAT = 4;

	public static final int STATUS_SELECTED_BY_OTHERS = 3;

	public static final int STATUS_SELECTED_BY_SELF = 2;
	
	public static final int SELECTED_SIZE_REACH_LIMIT = 5;
	
	public static final int IS_MOVING = 0;
	
	public static final int SEAT_SELECTED = 6;
	
	public static final int SEAT_SELECTED_CANCEL = 7;

	private int maxX;

	private int maxY;

	public float curX = 0.0f;

	public float curY = 0.0f;

	public float curWidth = 0.0f;

	public float curHeigth = 0.0f;

	private List<SeatRowNameInfo> rowNameList;

	private SeatSelectListener seatSelectListener;

	private List<SeatInfo> selectSeats = new ArrayList<SeatInfo>();

	private float mainX;

	private float mainY;

	private float mainWidth;

	private float mainHeight;

	public float getMainX() {
		return mainX;
	}

	public void setMainX(float mainX) {
		this.mainX = mainX;
	}

	public float getMainY() {
		return mainY;
	}

	public void setMainY(float mainY) {
		this.mainY = mainY;
	}

	public float getMainWidth() {
		return mainWidth;
	}

	public void setMainWidth(float mainWidth) {
		this.mainWidth = mainWidth;
	}

	public float getMainHeight() {
		return mainHeight;
	}

	public void setMainHeight(float mainHeight) {
		this.mainHeight = mainHeight;
	}

	public SeatManager(SeatSelectListener seatSelectListener) {
		this.seatSelectListener = seatSelectListener;
	}

	public void setData(List<Seat> paramSeatList, int paramLimitCount, List<SeatRowNameInfo> seatRowNameList) {
		getMaxXY(paramSeatList);
		this.arrAllSeats = new SeatInfo[paramSeatList.size()];
		this.selectedSeatList = new ArrayList();
		this.limitCount = paramLimitCount;
		this.columnCount = maxX + 1;// 一排多少个座位
		this.rowNameList = seatRowNameList;
		for (int i = 0; i < paramSeatList.size(); i++) {
			Seat localSeat = (Seat) paramSeatList.get(i);
			SeatInfo localSeatInfo = new SeatInfo();
			localSeatInfo.setSeatNum(localSeat.getSeatNumber());
			localSeatInfo.setSeatId(localSeat.getId());
			localSeatInfo.setType(localSeat.getType());
			localSeatInfo.setX(localSeat.getX());
			localSeatInfo.setY(localSeat.getY());
			localSeatInfo.setSeatName(localSeat.getName());
			if (localSeat.getId() == 0) {
				localSeatInfo.setStatus(STATUS_NOT_SEAT);
			} else {
				if (localSeat.isStatus()) {
					localSeatInfo.setStatus(STATUS_CAN_SELECT);
				} else {
					localSeatInfo.setStatus(STATUS_SELECTED_BY_OTHERS);
				}
			}
			this.arrAllSeats[(this.columnCount * localSeat.getY() + localSeat.getX())] = localSeatInfo;
		}
	}

	/**
	 * 获取最大座位编号
	 */
	public void getMaxXY(List<Seat> paramSeatList) {
		if (paramSeatList == null) {
			return;
		}
		Seat localSeat;
		for (Iterator<Seat> iter = paramSeatList.iterator(); iter.hasNext();) {
			localSeat = iter.next();
			if (localSeat.getX() > this.maxX) {
				this.maxX = localSeat.getX();
			}
			if (localSeat.getY() > this.maxY) {
				this.maxY = localSeat.getY();
			}
		}
	}

	public SeatInfo[] getAllSeats() {
		return this.arrAllSeats;
	}

	/**
	 * 根据座位的状态来进行不同操作
	 * 
	 * @return
	 */
	public int chooseSeat(SeatInfo paramSeatInfo) {
		switch (paramSeatInfo.getStatus()) {
			case STATUS_CAN_SELECT:
				// 可选择
				if (selectSeats.size() >= limitCount) {
					if (seatSelectListener != null) {
						seatSelectListener.onSelect(paramSeatInfo, SELECTED_SIZE_REACH_LIMIT);
					}
				} else {
					paramSeatInfo.setStatus(STATUS_SELECTED_BY_SELF);
					selectSeats.add(paramSeatInfo);
					if (seatSelectListener != null) {
						seatSelectListener.onSelect(paramSeatInfo, SEAT_SELECTED);
					}
				}
				break;
			case STATUS_SELECTED_BY_SELF:
				// 点击了已选择的座位，更改为取消选择
				paramSeatInfo.setStatus(STATUS_CAN_SELECT);
				selectSeats.remove(paramSeatInfo);
				if (seatSelectListener != null) {
					seatSelectListener.onSelect(paramSeatInfo, SEAT_SELECTED_CANCEL);
				}
				break;
			case STATUS_SELECTED_BY_OTHERS:
				// 被别人选择了
				if (seatSelectListener != null) {
					seatSelectListener.onSelect(paramSeatInfo, STATUS_SELECTED_BY_OTHERS);
				}
				break;
			case STATUS_NOT_SEAT:
				// 没有座位
				if (seatSelectListener != null) {
					seatSelectListener.onSelect(paramSeatInfo, STATUS_NOT_SEAT);
				}
				break;
			default:
				break;
		}

		return 1;
	}

	public void isMoving(int type) {
		if (seatSelectListener != null) {
			seatSelectListener.isMoving(type);
		}
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public List<SeatRowNameInfo> getRowNameList() {
		return rowNameList;
	}

	public void setRowNameList(List<SeatRowNameInfo> rowNameList) {
		this.rowNameList = rowNameList;
	}

	public List<SeatInfo> getSelectSeats() {
		return selectSeats;
	}

	public void setSelectSeats(List<SeatInfo> selectSeats) {
		this.selectSeats = selectSeats;
	}
	
	public SeatInfo getSeatInfoById(int seatId){
		SeatInfo seatInfo = null;
		for (int i = 0; i < selectSeats.size(); i++) {
			if(seatId == selectSeats.get(i).getSeatId()){
				seatInfo = selectSeats.get(i);
			}
		}
		return seatInfo;
	}

}
