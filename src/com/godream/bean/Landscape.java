package com.godream.bean;

import android.graphics.Bitmap;

public class Landscape {
	private int days;
	private String title;
	private Bitmap image;
	
	public Landscape(int days,String title) {
		this.days = days;
		this.title = title;
	}
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	
}
