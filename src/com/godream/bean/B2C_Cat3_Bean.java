package com.godream.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B2C_Cat3_Bean {
	private String parentId;
	private String id;
	private String catName;
	public static List<B2C_Cat3_Bean> list;
	public static List<B2C_Cat3_Bean> getData(){
		list = new ArrayList<B2C_Cat3_Bean>();
		list.addAll(getDatas("a0", "男士护肤","男士洁面","男士沐浴","剃须用品","男士套装"));
		list.addAll(getDatas("a1", "去角质","丰胸美乳","瘦身纤体","精油","止汗香体","脱毛"));
		list.addAll(getDatas("a2","两性护理","情趣用品","情趣内衣","避孕用品"));
		list.addAll(getDatas("a3","粉底液/膏","眼影","隔离霜/妆前乳","腮红","唇彩/唇蜜","BB霜","底妆","卸妆","眉笔/眉粉","唇膏/口红","眼线","睫毛膏","蜜粉/散粉"));
		list.addAll(getDatas("a4","中性香水","女士香水","礼盒/套装","男士香水"));
		list.addAll(getDatas("a5","面膜/面贴","洁面","护肤套装","乳液/面霜","防晒/爽肤水","眼霜","精华","磨砂/去角质"));
		list.addAll(getDatas("a6","化妆包","美体工具","美发工具","美甲工具","彩妆工具"));
		list.addAll(getDatas("a7","洗浴护体","润唇膏","纤体产品","护发没法","护手霜","身体乳"));
		list.addAll(getDatas("b0","夏季固元膏","灵芝片","参类","鹿茸","冬虫夏草","燕窝","宁夏枸杞","实惠礼包","阿胶","蜂胶","杨槐蜂蜜","东北雪蛤油"));
		list.addAll(getDatas("b1","靖江肉铺","温州鸭舌","鸭肉","凤爪","海味","武汉鸭脖","牛肉"));
		list.addAll(getDatas("b2","螺旋藻","维生素E","深海鱼油","左旋肉碱","维生素c","葡萄籽","减肥","胶原蛋白","蛋白质粉","排毒","补血","美白","实惠礼包"));
		list.addAll(getDatas("b3","可可粉","咖啡","黄酒","白酒","红酒","袋泡茶","洋酒","饮料","奶粉"));
		list.addAll(getDatas("b4","糕点","咖啡","巧克力","糖果"));
		list.addAll(getDatas("b5","松子","核桃","实惠礼包","香榧","夏威夷果","长生果","巴旦木","杏仁","红松"));
		list.addAll(getDatas("b6","毛尖","竹叶青","白茶","铁观音","红茶","瓜片","龙井","碧螺春","大红袍","普洱"));
		list.addAll(getDatas("b7","所有分类"));
		list.addAll(getDatas("b8","大闸蟹","干菜","食用油","调味品","大米"));
		list.addAll(getDatas("b9","糖果","蜜饯","糕点","巧克力","西饼"));
		list.addAll(getDatas("c0","乒乓球","羽毛球","壁球","网球"));
		list.addAll(getDatas("c1","篮球","棒球","足球","排球"));
		list.addAll(getDatas("c2","踏步机","跳舞毯","健腹器","跑步机"));
		list.addAll(getDatas("c3","骑行服","折叠车","山地车","配件"));
		list.addAll(getDatas("c4","瑜伽垫","肚皮舞","瑜伽服","拉丁舞"));
		list.addAll(getDatas("c5","棋牌游戏","轮滑","休闲泳衣","高尔夫"));
		list.addAll(getDatas("c6","运动服","休闲鞋","跑步鞋","运动鞋","风衣","夹克","板鞋","卫衣"));
		list.addAll(getDatas("c7","登山包","户外鞋","望远镜","冲锋衣","帐篷","冬帽","睡袋","抓绒衣","渔具"));
		list.addAll(getDatas("d0","电吹风","按摩棒","鼻毛清洁器","电动牙刷","电子秤","剃须刀","其他护理电器"));
		list.addAll(getDatas("d1","煎烤机","电饭煲","面包机","热水壶","煮蛋器","豆浆机","其他厨房电器","酸奶机","压力锅","微波炉","榨汁机"));
		list.addAll(getDatas("d2","硬盘播放器","HIFI音响","功放","组合音响","便捷DVD"));
		list.addAll(getDatas("d3","电吹风","饮水机","电饭煲","吸尘器","除湿机","挂烫机","电熨斗","鼻毛清洁器","足浴盆"));
		list.addAll(getDatas("d4","燃气灶","吸尘器","抽油烟机","洗碗机","燃气热水器","洗衣机","消毒柜","电热水器","空调","冰箱","电视机"));
		list.addAll(getDatas("d5","创意灯","台灯","工作灯"));
		list.addAll(getDatas("e0","一体机","传真机","碎纸机","扫描仪","点钞机","学习机","投影机"));
		list.addAll(getDatas("e1","单反配件","单反镜头","摄像机","单电/微单相机","数码相机","单反相机","滤镜"));
		list.addAll(getDatas("e2","掌机","摇杆","方向盘","家用机","手柄"));
		list.addAll(getDatas("e3","Ophone热卖机型","G3产品","手机配件","G3业务定制","G3上网卡","按商品展示","品牌手机","G3上网本","家庭宽带"));
		list.addAll(getDatas("e4","电源","机箱","散热器","显示器","主板","内存","硬盘","显卡"));
		list.addAll(getDatas("e5","3G上网","路由器","网络存储","交换机","网卡"));
		list.addAll(getDatas("e6","移动电源","读卡器","存储卡","相机清洁","三脚架","数码贴膜","电池/充电器"));
		list.addAll(getDatas("e7","平板电脑","台式机","笔记本配件","笔记本","上网本"));
		list.addAll(getDatas("e8","麦克风","MP3/MP4","录音笔","电子书/辞典","数码相框"));
		list.addAll(getDatas("e9","移动硬盘","电视盒","摄像头","U盘","外置盒","电脑工具","手写板","鼠标垫"));
		list.addAll(getDatas("f0","金条/金币","黄金/铂金","黄金摆件"));
		list.addAll(getDatas("f1","石英表","机械表","女表","男表","情侣表"));
		list.addAll(getDatas("f2","瑞士军刀","烟具","打火机","酒壶"));
		list.addAll(getDatas("f3","耳饰","发饰","戒指","项链","手链","脚链"));
		list.addAll(getDatas("f4","结婚对戒"));
		return list;
	}  
	

	public static List<B2C_Cat3_Bean> getDatas(String parentId,String ...strings) {
		int length = strings.length;
		List<B2C_Cat3_Bean> list = new ArrayList<B2C_Cat3_Bean>();
		for(int i = 0; i < length; i++){
			B2C_Cat3_Bean bean = new B2C_Cat3_Bean(parentId,parentId + i, strings[i]);
			list.add(bean);
		}
		return list;
	}	
	
	public static List<B2C_Cat3_Bean> getDatasByParentId(String parendId){
		List<B2C_Cat3_Bean> returnList = new ArrayList<B2C_Cat3_Bean>();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getParent_id().equals(parendId)){
				returnList.add(list.get(i));
			}
		}
		return returnList;
	}
	
	public B2C_Cat3_Bean(String parentId,String id,String catName) {
		this.parentId = parentId;
		this.id = id;
		this.catName = catName;
	}
	
	
	public String getParent_id() {
		return parentId;
	}


	public void setParent_id(String parent_id) {
		this.parentId = parent_id;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	
}
