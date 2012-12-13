package com.happyty.fortunelottery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);

		addBook(this);
		showBooks(this);

		return true;
	}

	public void addBook(Context context)
	{
		//		String tag = "Exercise BookProvider";
		//		Log.d(tag, "Adding a book");
		//		ContentValues cv = new ContentValues();
		//		cv.put(BookProviderMetaData.BookTableMetaData.BOOK_NAME, "book1");
		//		cv.put(BookProviderMetaData.BookTableMetaData.BOOK_ISBN, "isbn-1");
		//		cv.put(BookProviderMetaData.BookTableMetaData.BOOK_AUTHOR, "author-1");
		//
		//		ContentResolver cr = context.getContentResolver();
		//		Uri uri = BookProviderMetaData.BookTableMetaData.CONTENT_URI;
		//		Log.d(tag, "book insert uri:" + uri);
		//		Uri insertedUri = cr.insert(uri, cv);
		//		Log.d(tag, "inserted uri:" + insertedUri);
	}

	public void showBooks(Context context)
	{
		//		String tag = "Exercise BookProvider";
		//		Uri uri = BookProviderMetaData.BookTableMetaData.CONTENT_URI;
		//		Activity a = (Activity)context;
		//		Cursor c = a.managedQuery(uri,
		//			null, //projection
		//			null, //selection string
		//			null, //selection args array of strings
		//			null); //sort order
		//
		//		int iname = c.getColumnIndex(
		//			BookProviderMetaData.BookTableMetaData.BOOK_NAME);
		//
		//		int iisbn = c.getColumnIndex(
		//			BookProviderMetaData.BookTableMetaData.BOOK_ISBN);
		//		int iauthor = c.getColumnIndex(
		//			BookProviderMetaData.BookTableMetaData.BOOK_AUTHOR);
		//
		//		//Report your indexes
		//		Log.d(tag, "name,isbn,author:" + iname + iisbn + iauthor);
		//
		//		//walk through the rows based on indexes
		//		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		//		{
		//			//Gather values
		//			String id = c.getString(1);
		//			String name = c.getString(iname);
		//			String isbn = c.getString(iisbn);
		//			String author = c.getString(iauthor);
		//
		//			//Report or log the row
		//			StringBuffer cbuf = new StringBuffer(id);
		//			cbuf.append(",").append(name);
		//			cbuf.append(",").append(isbn);
		//			cbuf.append(",").append(author);
		//			Log.d(tag, cbuf.toString());
		//		}
		//
		//		//Report how many rows have been read
		//		int numberOfRecords = c.getCount();
		//		Log.d(tag, "Num of Records:" + numberOfRecords);
		//
		//		//Close the cursor
		//		//ideally this should be done in
		//		//a finally block.
		//		c.close();
	}

}
