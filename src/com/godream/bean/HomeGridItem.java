package com.godream.bean;

public class HomeGridItem {
	private int id;
	private String name;
	private int imageId;
	
	public HomeGridItem(int id,String name,int imageId) {
		this.id = id;
		this.imageId = imageId;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	
}
