package com.godream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.godream.bean.CompanyContactBean;
import com.godream.db.ContactTable;
import com.godream.util.LogUtil;
import com.godream.util.UtilTools;


public class CompanyContactsActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.company_contact);
	    Intent i = getIntent();
	    Uri uri = i.getData();
	    if(uri != null){
	    	ContentResolver resolver = getContentResolver();
	    	Cursor cursor = resolver.query(uri, null, null, null, null);
	    	for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
				CompanyContactBean bean = new CompanyContactBean();
				bean.setContactId(cursor.getString(cursor.getColumnIndex(ContactTable.F_CONTACTID)));
				bean.setGroupId(cursor.getString(cursor.getColumnIndex(ContactTable.F_GROUPID)));
				bean.setName(cursor.getString(cursor.getColumnIndex(ContactTable.F_NAME)));
				bean.setNameIndex(cursor.getString(cursor.getColumnIndex(ContactTable.F_NAMEINDEX)));
				bean.setNameLetter(cursor.getString(cursor.getColumnIndex(ContactTable.F_NAMELETTER)));
				bean.setTelephone(cursor.getString(cursor.getColumnIndex(ContactTable.F_TELEPHONE)));
				bean.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.F_PHOTO)));
				bean.setUpdatetime(cursor.getString(cursor.getColumnIndex(ContactTable.F_UPDATE_TIME)));
				bean.setGroupName(cursor.getString(cursor.getColumnIndex("groupName")));
				LogUtil.getInstance().i("id:" + bean.getContactId() + ",name:" + bean.getName() + ",groupName:" + bean.getGroupName());
			}
			cursor.close();
	    }
	}
}
