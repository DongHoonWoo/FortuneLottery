package com.happyty.fortunelottery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.happyty.fortunelottery.lotto.LottoNumberSao;
import com.happyty.fortunelottery.lotto.LottoProviderMetaData;
import com.happyty.fortunelottery.lotto.LottoResponse;
import com.happyty.fortunelottery.lotto.model.LottoNum;
import com.happyty.fortunelottery.lotto.model.WinNumbers;
import com.happyty.fortunelottery.util.T;

public class MainActivity extends Activity {

	private TextView num01;
	private TextView num02;
	private TextView num03;
	private TextView num04;
	private TextView num05;
	private TextView num06;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		num01 = (TextView)findViewById(R.id.num_01);
		num02 = (TextView)findViewById(R.id.num_02);
		num03 = (TextView)findViewById(R.id.num_03);
		num04 = (TextView)findViewById(R.id.num_04);
		num05 = (TextView)findViewById(R.id.num_05);
		num06 = (TextView)findViewById(R.id.num_06);
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
				new RequestLotto().execute();
			}
		});
		Button bt2 = (Button)findViewById(R.id.getNumber);
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new LottoNumberSao().getNumber(500);
			}
		});

		Button bt3 = (Button)findViewById(R.id.getLottoDB);
		bt3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getLottoDB();
			}
		});
		return true;
	}

	private void getLottoDB() {
		Uri uri = LottoProviderMetaData.LottoTableMetaData.CONTENT_URI;
		try {
			Cursor c = managedQuery(uri,
				null, //projection
				null, //selection string
				null, //selection args array of strings
				null); //sort order

			if (c == null) {
				return;
			}

			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				LottoNum num = new LottoNum(c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.NUMBER_OF_TIME))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.DATE))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_1))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_2))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_3))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_4))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_5))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_6))
					, c.getInt(c.getColumnIndex(LottoProviderMetaData.LottoTableMetaData.LUCKY_NUMBER_BONUS)));
				T.d(num.toString());
				WinNumbers.getInstance().insertNumber(num.getXth(), num);
			}
		} catch (SQLException e) {
			T.w(e.toString());
		}

		WinNumbers.getInstance().calculateNum();
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

	private Drawable getNumberBg(int num) {
		if (num >= 1 && num <= 10) {
			return getResources().getDrawable(R.drawable.num_01);
		} else if (num >= 11 && num <= 20) {
			return getResources().getDrawable(R.drawable.num_10);
		} else if (num >= 21 && num <= 30) {
			return getResources().getDrawable(R.drawable.num_20);
		} else if (num >= 31 && num <= 40) {
			return getResources().getDrawable(R.drawable.num_30);
		} else if (num >= 41) {
			return getResources().getDrawable(R.drawable.num_40);
		}
		return null;
	}

	private void updateNum(LottoNum lottoNum) {
		num01.setText(String.valueOf(lottoNum.getLuckyNumber1()));
		num01.setBackgroundDrawable(getNumberBg(lottoNum.getLuckyNumber1()));
		num02.setText(String.valueOf(lottoNum.getLuckyNumber2()));
		num02.setBackgroundDrawable(getNumberBg(lottoNum.getLuckyNumber2()));
		num03.setText(String.valueOf(lottoNum.getLuckyNumber3()));
		num03.setBackgroundDrawable(getNumberBg(lottoNum.getLuckyNumber3()));
		num04.setText(String.valueOf(lottoNum.getLuckyNumber4()));
		num04.setBackgroundDrawable(getNumberBg(lottoNum.getLuckyNumber4()));
		num05.setText(String.valueOf(lottoNum.getLuckyNumber5()));
		num05.setBackgroundDrawable(getNumberBg(lottoNum.getLuckyNumber5()));
		num06.setText(String.valueOf(lottoNum.getLuckyNumber6()));
		num06.setBackgroundDrawable(getNumberBg(lottoNum.getLuckyNumber6()));
	}

	class RequestLotto extends AsyncTask<Void, Void, HttpResponse> {

		@Override
		protected void onPostExecute(HttpResponse result) {
			if (result == null) {
				return;
			}

			LottoResponse response = new LottoResponse(result);
			LottoNum lottoNum = new LottoNum(response.asJson());
			T.d(lottoNum.toString());
			updateNum(lottoNum);

			try {
				T.d(response.asString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

		@Override
		protected HttpResponse doInBackground(Void... params) {
			try {
				return new LottoNumberSao().getLatestNumberSync();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
