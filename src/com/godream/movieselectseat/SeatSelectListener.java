package com.godream.movieselectseat;



public interface SeatSelectListener {
	public void onSelect(SeatInfo paramSeatInfo, int paramInt);
	public void isMoving(int type);
}
