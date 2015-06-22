package com.godream.bean;

import java.util.ArrayList;
import java.util.List;

import com.godream.R;

public class B2C_Comm_Bean {
	private String id;
	private int ImageId;
	private int ImageId_Pressed;
	private String catName;
	private String catDetail;
	
	public static List<B2C_Comm_Bean> getData(){
		List<B2C_Comm_Bean> list = new ArrayList<B2C_Comm_Bean>();
		B2C_Comm_Bean bean1 = new B2C_Comm_Bean("a",R.drawable.b2c_cat_mrhf_1,R.drawable.b2c_cat_mrhf_2,"美容护肤","(男士专区 塑身美体 成人用品)");
		B2C_Comm_Bean bean2 = new B2C_Comm_Bean("b",R.drawable.b2c_cat_spbj_1,R.drawable.b2c_cat_spbj_2,"食品保健","(营养滋补 肉类零食 保健食品)");
		B2C_Comm_Bean bean3 = new B2C_Comm_Bean("c",R.drawable.b2c_cat_ydhw_1,R.drawable.b2c_cat_ydhw_2,"户外运动","(球拍运动 团队运动 健身器材)");
		B2C_Comm_Bean bean4 = new B2C_Comm_Bean("d",R.drawable.b2c_cat_jydq_1,R.drawable.b2c_cat_jydq_2,"家用电器","(个人护理 厨房电器 影音电器)");
		B2C_Comm_Bean bean5 = new B2C_Comm_Bean("e",R.drawable.b2c_cat_sjsm_1,R.drawable.b2c_cat_sjsm_2,"手机数码","(办公设备 摄影摄像 电玩)");
		B2C_Comm_Bean bean6 = new B2C_Comm_Bean("f",R.drawable.b2c_cat_sbsp_1,R.drawable.b2c_cat_sbsp_2,"手表饰品","(黄金K金 品牌手表 军刀火机)");
		B2C_Comm_Bean bean7 = new B2C_Comm_Bean("g",R.drawable.b2c_cat_myyp_1,R.drawable.b2c_cat_myyp_2,"母婴用品","(玩具书包 孕妇专区 童装童鞋)");
		B2C_Comm_Bean bean8 = new B2C_Comm_Bean("h",R.drawable.b2c_cat_jzsh_1,R.drawable.b2c_cat_jzsh_2,"居家生活","(餐饮用品 时尚家饰 汽车用品)");
		B2C_Comm_Bean bean9 = new B2C_Comm_Bean("i",R.drawable.b2c_cat_fzxb_1,R.drawable.b2c_cat_fzxb_2,"服装箱包","(品质男鞋 潮流女包 功能箱包)");
		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		list.add(bean4);
		list.add(bean5);
		list.add(bean6);
		list.add(bean7);
		list.add(bean8);
		list.add(bean9);
		return list;
	}
	
	public B2C_Comm_Bean(String id,int imageId,int imageId_pressed,String catName,String catDetail) {
		this.id = id;
		this.ImageId = imageId;
		this.ImageId_Pressed = imageId_pressed;
		this.catName = catName;
		this.catDetail = catDetail;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getImageId() {
		return ImageId;
	}
	public void setImageId(int imageId) {
		ImageId = imageId;
	}
	public int getImageId_Pressed() {
		return ImageId_Pressed;
	}
	public void setImageId_Pressed(int imageId_Pressed) {
		ImageId_Pressed = imageId_Pressed;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getCatDetail() {
		return catDetail;
	}
	public void setCatDetail(String catDetail) {
		this.catDetail = catDetail;
	}
	
	
}
