package com.happyty.fortunelottery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.happyty.fortunelottery.lotto.LottoNumberSao;
import com.happyty.fortunelottery.lotto.LottoProviderMetaData;
import com.happyty.fortunelottery.util.T;

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

		Button load = (Button)findViewById(R.id.load_csv);
		load.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initializeLottoDB();
			}
		});

		Button bt1 = (Button)findViewById(R.id.getLatest);
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new LottoNumberSao().getLatestNumber();
			}
		});
		Button bt2 = (Button)findViewById(R.id.getNumber);
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new LottoNumberSao().getNumber(500);
			}
		});
		return true;
	}

	private void initializeLottoDB() {
		Uri uri = LottoProviderMetaData.LottoTableMetaData.CONTENT_URI;
		try {
			Cursor c = managedQuery(uri,
				null, //projection
				null, //selection string
				null, //selection args array of strings
				null); //sort order
			if (c != null && c.moveToFirst()) {
				T.d("lotto db =>" + c.getCount());
			} else {
				loadLottoCSV();
			}
		} catch (SQLException e) {
			T.w(e.toString());
			loadLottoCSV();
		}
	}

	private void loadLottoCSV() {
		AssetManager am = getAssets();
		try {
			InputStream is = am.open("lotto.csv");
			Reader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null) {
				//				Log.d("Lotto", line);
				parseLine(line);
			}
		} catch (IOException e) {
			Log.e("Lotto", e.toString());
		}
	}

	private void parseLine(String line) {
		String[] sep = line.replace(" ", "").split(",");

		int num;
		Date date = new Date();
		int n1, n2, n3, n4, n5, n6, bonus;

		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");

		num = Integer.parseInt(sep[0]);
		try {
			date = format.parse(sep[1]);
			date.setHours(20);
			date.setMinutes(40);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		n1 = Integer.parseInt(sep[2]);
		n2 = Integer.parseInt(sep[3]);
		n3 = Integer.parseInt(sep[4]);
		n4 = Integer.parseInt(sep[5]);
		n5 = Integer.parseInt(sep[6]);
		n6 = Integer.parseInt(sep[7]);
		bonus = Integer.parseInt(sep[8]);

		ContentValues cv = new ContentValues();
		cv.put(LottoProviderMetaData.LottoTableMetaData.NUMBER_OF_TIME, num);
		cv.put(LottoProviderMetaData.LottoTableMetaData.DATE, date.getTime());
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_1, n1);
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_2, n2);
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_3, n3);
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_4, n4);
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_5, n5);
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_6, n6);
		cv.put(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_BONUS, bonus);

		ContentResolver cr = getContentResolver();
		Uri uri = LottoProviderMetaData.LottoTableMetaData.CONTENT_URI;
		Uri insertedUri = cr.insert(uri, cv);
		T.d("inserted uri:" + insertedUri);

		T.d("parse =>" + num
			+ "|" + date.toString()
			+ "|" + n1
			+ "|" + n2
			+ "|" + n3
			+ "|" + n4
			+ "|" + n5
			+ "|" + n6
			+ "|" + bonus);
	}
}
