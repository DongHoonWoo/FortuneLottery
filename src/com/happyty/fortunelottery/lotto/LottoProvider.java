package com.happyty.fortunelottery.lotto;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.happyty.fortunelottery.lotto.LottoProviderMetaData.LottoTableMetaData;

public class LottoProvider extends ContentProvider {
	private static final String TAG = LottoProvider.class.getSimpleName();

	private static final UriMatcher sUriMatcher;
	private static final int INCOMING_LOTTO_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMING_SINGLE_LOTTO_URI_INDICATOR = 2;

	private static HashMap<String, String> sLottoProjectionMap;
	static
	{
		sLottoProjectionMap = new HashMap<String, String>();
		sLottoProjectionMap.put(LottoTableMetaData._ID,
			LottoTableMetaData._ID);

		sLottoProjectionMap.put(LottoTableMetaData.NUMBER_OF_TIME,
			LottoTableMetaData.NUMBER_OF_TIME);
		sLottoProjectionMap.put(LottoTableMetaData.DATE,
			LottoTableMetaData.DATE);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_1,
			LottoTableMetaData.LUCKY_NUMBER_1);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_2,
			LottoTableMetaData.LUCKY_NUMBER_2);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_3,
			LottoTableMetaData.LUCKY_NUMBER_3);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_4,
			LottoTableMetaData.LUCKY_NUMBER_4);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_5,
			LottoTableMetaData.LUCKY_NUMBER_5);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_6,
			LottoTableMetaData.LUCKY_NUMBER_6);
		sLottoProjectionMap.put(LottoTableMetaData.LUCKY_NUMBER_BONUS,
			LottoTableMetaData.LUCKY_NUMBER_BONUS);
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(LottoProviderMetaData.AUTHORITY, "lottos",
			INCOMING_LOTTO_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(LottoProviderMetaData.AUTHORITY, "lottos/#",
			INCOMING_SINGLE_LOTTO_URI_INDICATOR);

	}

	@Override
	public boolean onCreate() {
		Log.d(TAG, "onCreate");
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
			case INCOMING_LOTTO_COLLECTION_URI_INDICATOR:
				count = db.delete(LottoTableMetaData.TABLE_NAME,
					where, whereArgs);
				break;

			case INCOMING_SINGLE_LOTTO_URI_INDICATOR:
				String rowId = uri.getPathSegments().get(1);
				count = db.delete(LottoTableMetaData.TABLE_NAME,
					LottoTableMetaData._ID + "=" + rowId
						+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""),
					whereArgs);
				break;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
			case INCOMING_LOTTO_COLLECTION_URI_INDICATOR:
				return LottoTableMetaData.CONTENT_TYPE;

			case INCOMING_SINGLE_LOTTO_URI_INDICATOR:
				return LottoTableMetaData.CONTENT_ITEM_TYPE;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (sUriMatcher.match(uri)
				!= INCOMING_LOTTO_COLLECTION_URI_INDICATOR)
		{
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (values.containsKey(LottoTableMetaData.NUMBER_OF_TIME) == false) {
			throw new SQLException(
				"Failed to insert row because NUMBER_OF_TIME is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.DATE) == false) {
			throw new SQLException(
				"Failed to insert row because DATE is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_1) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_1 is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_2) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_2 is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_3) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_3 is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_4) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_4 is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_5) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_5 is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_6) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_6 is needed " + uri);
		} else if (values.containsKey(LottoTableMetaData.LUCKY_NUMBER_BONUS) == false) {
			throw new SQLException(
				"Failed to insert row because LUCKY_NUMBER_BONUS is needed " + uri);
		}

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = db.insert(LottoTableMetaData.TABLE_NAME,
			LottoTableMetaData.NUMBER_OF_TIME, values);
		if (rowId > 0) {
			Uri insertedLottokUri =
					ContentUris.withAppendedId(
						LottoTableMetaData.CONTENT_URI, rowId);
			getContext()
				.getContentResolver()
				.notifyChange(insertedLottokUri, null);

			return insertedLottokUri;
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (sUriMatcher.match(uri)) {
			case INCOMING_LOTTO_COLLECTION_URI_INDICATOR:
				qb.setTables(LottoTableMetaData.TABLE_NAME);
				qb.setProjectionMap(sLottoProjectionMap);
				break;

			case INCOMING_SINGLE_LOTTO_URI_INDICATOR:
				qb.setTables(LottoTableMetaData.TABLE_NAME);
				qb.setProjectionMap(sLottoProjectionMap);
				qb.appendWhere(LottoTableMetaData._ID + "="
					+ uri.getPathSegments().get(1));
				break;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		// If no sort order is specified use the default
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = LottoTableMetaData.DEFAULT_SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}

		// Get the database and run the query
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection,
			selectionArgs, null, null, orderBy);

		//example of getting a count
		int i = c.getCount();

		// Tell the cursor what uri to watch,
		// so it knows when its source data changes
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		int count;
		switch (sUriMatcher.match(uri)) {
			case INCOMING_LOTTO_COLLECTION_URI_INDICATOR:
				count = db.update(LottoTableMetaData.TABLE_NAME,
					values, where, whereArgs);
				break;

			case INCOMING_SINGLE_LOTTO_URI_INDICATOR:
				String rowId = uri.getPathSegments().get(1);
				count = db.update(LottoTableMetaData.TABLE_NAME,
					values, LottoTableMetaData._ID + "=" + rowId
						+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""),
					whereArgs);
				break;

			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	private DatabaseHelper mOpenHelper;

	/**
	 * Setup/Create Database
	 * This class helps open, create, and upgrade the database file.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context,
				LottoProviderMetaData.DATABASE_NAME,
				null,
				LottoProviderMetaData.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			Log.d(TAG, "inner oncreate called");
			db.execSQL("CREATE TABLE " + LottoTableMetaData.TABLE_NAME + " ("
				+ LottoTableMetaData._ID + " INTEGER PRIMARY KEY,"
				+ LottoTableMetaData.NUMBER_OF_TIME + " INTEGER,"
				+ LottoTableMetaData.DATE + " LONG,"
				+ LottoTableMetaData.LUCKY_NUMBER_1 + " INTEGER,"
				+ LottoTableMetaData.LUCKY_NUMBER_2 + " INTEGER,"
				+ LottoTableMetaData.LUCKY_NUMBER_3 + " INTEGER"
				+ LottoTableMetaData.LUCKY_NUMBER_4 + " INTEGER"
				+ LottoTableMetaData.LUCKY_NUMBER_5 + " INTEGER"
				+ LottoTableMetaData.LUCKY_NUMBER_6 + " INTEGER"
				+ LottoTableMetaData.LUCKY_NUMBER_BONUS + " INTEGER"
				+ ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.d(TAG, "inner onupgrade called");
			Log.w(TAG, "Upgrading database from version "
				+ oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " +
				LottoTableMetaData.TABLE_NAME);
			onCreate(db);
		}
	}
}
