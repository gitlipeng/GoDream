package com.godream.db;

import android.content.Context;
import android.database.Cursor;

import com.godream.util.LogUtil;

/**
 * 类名：CompanyContactsProvider
 * 描述：公司通讯录数据提供类,使用此类中的方法如果外部调用open,然后操作方法,最后一定记得调用close方法关闭数据库
 * 
 * @author lipeng
 */
public class CompanyContactsDB extends BeemDBAdapter {

	public CompanyContactsDB(Context context) {
		super(context);
	}

	/**
	 * 方法名：queryContactList 描述：查询联系人列表
	 * 
	 * @param queryContent查询条件
	 * @return
	 */
	public synchronized Cursor queryContactList() {
		boolean chkflg = false;
		if (db == null) {
			// 内部打开数据库
			open();
			chkflg = true;
		}
		if (db != null) {
			String selectSql = "SELECT " + "a." + ContactTable.F_CONTACTID + ", " + "a." + ContactTable.F_GROUPID + ", " + "a." + ContactTable.F_NAME
			        + ", " + "a." + ContactTable.F_NAMEINDEX + ", " + "a." + ContactTable.F_NAMELETTER + ", " + "a." + ContactTable.F_TELEPHONE
			        + ", " + "a." + ContactTable.F_PHOTO + ", " + "a." + ContactTable.F_UPDATE_TIME + ", " + "b." + OrgTable.F_SHORTNAME
			        + " AS groupName " + " FROM '" + ContactTable.TABLENAME + "' a" + " LEFT JOIN '" + OrgTable.TABLENAME + "' b " + " ON  a."
			        + ContactTable.F_GROUPID + " = b." + OrgTable.F_ID + " " + " ORDER BY a." + ContactTable.F_NAMELETTER + " ";

			LogUtil.getInstance().i("selectSql:	" + selectSql);
			Cursor cursor = db.rawQuery(selectSql, null);

			return cursor;
		}
		if (chkflg) {
			close();// 内部打开，内部关闭
			db = null;
		}
		return null;
	}

	/**
	 * 方法名：queryContactDetail 描述：查询联系人详情
	 */
	public synchronized Cursor queryContactDetail(String contactId) {
		boolean chkflg = false;
		if (db == null) {
			// 内部打开数据库
			open();
			chkflg = true;
		}
		if (db != null) {
			String selectSql = "SELECT a.*,b." + OrgTable.F_SHORTNAME + " AS groupName FROM '" + ContactTable.TABLENAME + "' a" + " LEFT JOIN '"
			        + OrgTable.TABLENAME + "' b " + " ON  a." + ContactTable.F_GROUPID + " = b." + OrgTable.F_ID + " " + " WHERE a."
			        + ContactTable.F_CONTACTID + " = '" + contactId + "' ";
			Cursor cursor = db.rawQuery(selectSql, null);
			return cursor;
		}
		if (chkflg) {
			close();// 内部打开，内部关闭
			db = null;
		}
		return null;
	}

	public Cursor queryAllGroup() {
		Cursor cursor = null;
		boolean chkflg = false;
		if (db == null) {
			// 内部打开数据库
			open();
			chkflg = true;
		}
		if (db != null) {
			String selectSql = "SELECT * FROM '" + OrgTable.TABLENAME + "'";
			cursor = db.rawQuery(selectSql, null);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex(OrgTable.F_ID));
				String pId = cursor.getString(cursor.getColumnIndex(OrgTable.F_PARENTID));
				String name = cursor.getString(cursor.getColumnIndex(OrgTable.F_NAME));
				String shortName = cursor.getString(cursor.getColumnIndex(OrgTable.F_SHORTNAME));
				String updateTime = cursor.getString(cursor.getColumnIndex(OrgTable.F_SYNCHRO));
				String guid = cursor.getString(cursor.getColumnIndex(OrgTable.F_ROOTGUID));
				LogUtil.getInstance().i("groupid:" + id + ",pid:" + pId + ",name:" + name + ",time:" + updateTime);
			}
			return cursor;
		}

		if (chkflg) {
			// 内部打开内部关闭，否则外部关闭
			close();
			db = null;
		}
		return cursor;
	}
}
