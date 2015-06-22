package com.godream.db;

import android.database.sqlite.SQLiteDatabase;



public class ContactTable {
	//表名
	public static final String TABLENAME = "Table_Contact";
	//字段
	public static final String F_CONTACTID = "F_CONTACTID";//联系人编号
	public static final String F_GROUPID = "F_GROUPID";//组织编号
	public static final String F_NAME = "F_NAME";//姓名
	public static final String F_BIRTHDAY = "F_BIRTHDAY";//生日birthday
	public static final String F_NAMEINDEX  = "F_NAMEINDEX";//联系人索引(姓名首字母)
	public static final String F_NAMELETTER = "F_NAMELETTER";//名字的汉语拼音
	public static final String F_GENDER = "F_GENDER";//性别gender
	public static final String F_ADDRESS = "F_ADDRESS";//联系地址
	public static final String F_TELEPHONE = "F_TELEPHONE";//办公电话
	public static final String F_EMAIL = "F_EMAIL";//个人邮箱
	public static final String F_PHOTO = "F_PHOTO";//头像，存储路径
	public static final String F_STATUS = "F_STATUS";//在线状态
	public static final String F_UPDATE_TIME = "F_UPDATE_TIME";//最近更新时间
	public static final String F_CREATE_TIME = "F_CREATE_TIME";//创建时间
	
	public static final String F_QQ = "F_QQ";//qq号
	public static final String F_WEIXIN = "F_WEIXIN";//微信号
	public static final String F_SKYPE = "F_SKYPE";//SKYPE号
	public static final String F_MSN = "F_MSN";//MSN
	
	public static final String F_SINAWEIBO = "F_DATA1";//新浪微博
	public static final String F_DATA2 = "F_DATA2";//
	public static final String F_DATA3 = "F_DATA3";//
	public static final String F_DATA4 = "F_DATA4";//
	public static final String F_DATA5 = "F_DATA5";//
	public static final String F_DATA6 = "F_DATA6";//
	public static final String F_DATA7 = "F_DATA7";//
	public static final String F_DATA8 = "F_DATA8";//
	public static final String F_DATA9 = "F_DATA9";//
	public static final String F_DATA10 = "F_DATA10";//

	
	//建表语句
	public static final String CREATESQL = "create table if not exists '" + TABLENAME + "' "
							+ " ( "
							+ F_CONTACTID	 + " VARCHAR(8) NOT NULL PRIMARY KEY , "
							+ F_GROUPID		 + " VARCHAR(30) , "
							+ F_NAME		 + " VARCHAR(32), "
							+ F_BIRTHDAY	 + " VARCHAR(16), "
							+ F_NAMEINDEX	 + " VARCHAR(8), "
							+ F_NAMELETTER	 + " VARCHAR(32), "
							+ F_GENDER		 + " VARCHAR(10), "
							+ F_ADDRESS		 + " VARCHAR(255), "
							+ F_TELEPHONE	 + " VARCHAR(32), "
							+ F_EMAIL		 + " VARCHAR(64), "
							+ F_PHOTO		 + " VARCHAR(255), "
							+ F_STATUS		 + " VARCHAR(3), "
							+ F_UPDATE_TIME	 + " date NOT NULL, "
							+ F_CREATE_TIME	 + " date NOT NULL "
							+ " ) ";
}






















