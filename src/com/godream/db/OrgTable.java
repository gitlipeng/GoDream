package com.godream.db;

public class OrgTable {
	//表名
	public static final String TABLENAME = "Table_Org";
	//字段
	public static final String F_ID = "F_ID";//组织编号
	public static final String F_PARENTID = "F_PARENTID";//上级组织编号
	public static final String F_NAME = "F_NAME";//名称
	public static final String F_SHORTNAME = "F_SHORTNAME";//名称简称
	public static final String F_STATUS = "F_STATUS";//状态
	public static final String F_SYNCHRO = "F_SYNCHRO";//同步时间
	public static final String F_ROOTGUID = "F_ROOTGUID";//guid
	
	//建表语句
	public static final String CREATESQL = "create table if not exists '" + TABLENAME + "' "
			+ " ( "
			+ F_ID	 + " VARCHAR(30) PRIMARY KEY , "
			+ F_PARENTID		 + " VARCHAR(30) NULL, "
			+ F_NAME			 + " VARCHAR(30) NULL, "
			+ F_STATUS			 + " VARCHAR(30) NULL, "
			+ F_SYNCHRO			 + " CHAR(16) NULL, "
			+ F_ROOTGUID		 + " VARCHAR(30) NULL "
			+ " ) ";
	
	public static final String ADD_SHORTNAME = F_SHORTNAME + " VARCHAR(30) ";
	
}
