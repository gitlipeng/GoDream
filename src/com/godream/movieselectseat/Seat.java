package com.godream.movieselectseat;

public class Seat {

	private int id;//id为0代表走廊，没有座位显示

	private boolean isSelect;

	private String name;

	private String seatNumber;

	private boolean status;//true：可选，false：不可选

	private int type;

	private int x;

	private int y;

	
    public Seat(int id,String name,int x,int y) {
    	this.id = id;
    	this.name = name;
    	this.x = x;
    	this.y = y;
    }
	
    public int getId() {
    	return id;
    }

	
    public void setId(int id) {
    	this.id = id;
    }

	
    public boolean isSelect() {
    	return isSelect;
    }

	
    public void setSelect(boolean isSelect) {
    	this.isSelect = isSelect;
    }

	
    public String getName() {
    	return name;
    }

	
    public void setName(String name) {
    	this.name = name;
    }

	
    public String getSeatNumber() {
    	return seatNumber;
    }

	
    public void setSeatNumber(String seatNumber) {
    	this.seatNumber = seatNumber;
    }

	
    public boolean isStatus() {
    	return status;
    }

	
    public void setStatus(boolean status) {
    	this.status = status;
    }

	
    public int getType() {
    	return type;
    }

	
    public void setType(int type) {
    	this.type = type;
    }

	
    public int getX() {
    	return x;
    }

	
    public void setX(int x) {
    	this.x = x;
    }

	
    public int getY() {
    	return y;
    }

	
    public void setY(int y) {
    	this.y = y;
    }
	
	
}
