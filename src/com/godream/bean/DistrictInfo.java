package com.godream.bean;

/**
 * 小区信息实体类
 * @author liuzc 
 * @url    http://liuzhichao.com
 * @mail   liuzc89@gmail.com
 */
public class DistrictInfo {

	private String id;     //小区编号
	private String name;	//小区名称
	private double lat;		//经度
	private double lng;		//纬度
	private String address;  //地址
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	

	
}
