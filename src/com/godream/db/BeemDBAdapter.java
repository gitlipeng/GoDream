package com.godream.db;

import com.godream.common.Common;
import com.godream.common.Constant;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class BeemDBAdapter {
	public SQLiteDatabase db;
	public Context context;
	public BeemDBAdapter(Context context) {
		this.context = context;
	}

	public BeemDBAdapter open() throws SQLException { 
		//调用getWriteableDatabase可能因为磁盘空间或权限问题失败
		try{
			db = SQLiteDatabase.openOrCreateDatabase(Common.DB_DIR, null);
		}catch (SQLiteException ex){
// 			db = dbHelper.getReadableDatabase();
			ex.printStackTrace();
		}
		return this;
	}

	public void close() {
		if(db != null)
			db.close();
	}

}
