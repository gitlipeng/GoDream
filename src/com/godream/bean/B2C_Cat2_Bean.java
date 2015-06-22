package com.godream.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2C_Cat2_Bean {
	private String id;
	private String parentId;
	private String catName;
	static List<B2C_Cat2_Bean> list;
	public static List<B2C_Cat2_Bean> getData() {
		list = new ArrayList<B2C_Cat2_Bean>();
		list.addAll(getDatas("a","男士专区","塑身美体" ,"成人用品","时尚彩妆","魅力香水","面部护理","美妆工具","个人护理"));
		list.addAll(getDatas("b","营养滋补","肉类零食","保健食品","酒水饮料","进口零食","坚果干货","茶叶茗品","各地特产","粮油干货","糖果零食"));
		list.addAll(getDatas("c","球拍运动","团队运动","健身器材","自行车","瑜伽美体","休闲运动","跑步","户外运动"));
		list.addAll(getDatas("d","个人护理","厨房电器","影音电器","生活家电","大家电","灯饰照明"));
		list.addAll(getDatas("e","办公设备","摄影摄像","电玩","G3天地","电脑配件","网络产品","数码配件","电脑整机","时尚影音","外设产品"));
		list.addAll(getDatas("f","黄金K金","品牌手表","军刀火机","时尚饰品","钻石饰品","眼镜/太阳镜","翡翠晶石"));
		list.addAll(getDatas("g","玩具书包","孕妇专区","童装童鞋","婴儿用品","尿片湿巾","婴儿食品","童车童床","宝宝洗护"));
		list.addAll(getDatas("h","餐饮用品","时尚家饰","汽车用品","精品家具","玩具模型","卫浴用品","名品家纺","宠物生活","生活日用"));
		list.addAll(getDatas("i","品质男鞋","潮流女包","功能箱包","配件","精品男装","时尚女装","内衣","潮流女鞋","精品男包"));
		return list;
	}

	public static List<B2C_Cat2_Bean> getDatas(String parentId,String ...strings) {
		int length = strings.length;
		List<B2C_Cat2_Bean> list = new ArrayList<B2C_Cat2_Bean>();
		for(int i = 0; i < length; i++){
			B2C_Cat2_Bean bean = new B2C_Cat2_Bean(parentId,parentId + i, strings[i]);
			list.add(bean);
		}
		return list;
	}
	
	public static List<B2C_Cat2_Bean> getDatasByParentId(String parendId){
		List<B2C_Cat2_Bean> returnList = new ArrayList<B2C_Cat2_Bean>();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getParentId().equals(parendId)){
				returnList.add(list.get(i));
			}
		}
		return returnList;
	}
	
	public B2C_Cat2_Bean(String parentId, String id, String catName) {
		this.parentId = parentId;
		this.id = id;
		this.catName = catName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

}
