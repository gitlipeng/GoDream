package com.godream.contentprovider;

import com.godream.db.CompanyContactsDB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class ContactsProvider extends ContentProvider {

	/**
	 * author
	 */
	public static final String AUTHORITY = "com.godream.provider.Contacts";

	/**
	 * path
	 */

	public static final String PATH = "contacts";

	/**
	 * URI
	 */
	public static final Uri CONTACTS_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

	/**
	 * 全部的联系人
	 */
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.godream.contact";

	/**
	 * 单个联系人
	 */
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.godream.contact";

	/**
	 * uriMathcher
	 */
	private static final UriMatcher sUriMatcher;

	private static final int CONTACTS = 1;

	private static final int CONTACT_ID = 2;

	private static final int LIVE_FOLDER_CONTACTS = 3;

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(AUTHORITY, PATH, CONTACTS);
		sUriMatcher.addURI(AUTHORITY, PATH + "/#", CONTACT_ID);
		sUriMatcher.addURI(AUTHORITY, "live_folder/notes", LIVE_FOLDER_CONTACTS);
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		CompanyContactsDB db = new CompanyContactsDB(getContext());
		Cursor cursor = null;
		switch (sUriMatcher.match(uri)) {
			case CONTACTS:
				//查询全部联系人
				cursor = db.queryContactList();
				return cursor;
			case LIVE_FOLDER_CONTACTS:
				cursor = db.queryAllGroup();
				return cursor;
			case CONTACT_ID:
				String contactId = uri.getPathSegments().get(1);
				cursor = db.queryContactDetail(contactId);
				return cursor;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
			case CONTACTS:
			case LIVE_FOLDER_CONTACTS:
				return CONTENT_TYPE;
			case CONTACT_ID:
				return CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

}
