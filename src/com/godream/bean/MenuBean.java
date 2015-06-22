package com.godream.bean;


public class MenuBean {
	private String content;
	private int imageId;
	private int id;
	
	public MenuBean(int id,int imageId,String content) {
		this.id = id;
		this.imageId = imageId;
		this.content = content;
	}
	
	public MenuBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
