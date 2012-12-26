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
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.happyty.fortunelottery.lotto.LottoNumberSao;

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
				loadLottoCSV();
			}
		});

		Button bt1 = (Button)findViewById(R.id.getLatest);
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new LottoNumberSao().getLatestNumber();
			}
		});
		return true;
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

		Log.d("Lotto", "parse =>" + num
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
