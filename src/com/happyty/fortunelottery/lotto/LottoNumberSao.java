package com.happyty.fortunelottery.lotto;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;

import com.happyty.fortunelottery.lotto.model.LottoNum;
import com.happyty.fortunelottery.util.T;

public class LottoNumberSao {
	private final static String LOTTO645_SITE = "http://www.645lotto.net";
	private final static String API_LATEST_URL = "/resultall/dummy.asp";
	private final static String API_REQUEST_URL = "/ajax_jsonNew.asp?sGameNo=";

	public void getLatestNumber() {
		new RequestLotto().execute(LOTTO645_SITE + API_LATEST_URL);
	}

	public void getNumber(int seq) {
		new RequestLotto().execute(LOTTO645_SITE + API_REQUEST_URL + seq);
	}

	public HttpClient getHttpClient() {
		try {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	class RequestLotto extends AsyncTask<String, Void, HttpResponse> {

		@Override
		protected void onPostExecute(HttpResponse result) {
			if (result == null) {
				return;
			}

			LottoResponse response = new LottoResponse(result);
			T.d(new LottoNum(response.asJson()).toString());

			try {
				T.d(response.asString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			super.onPostExecute(result);
		}

		@Override
		protected HttpResponse doInBackground(String... params) {
			HttpGet request = new HttpGet(params[0]);
			try {
				HttpResponse httpResponse = getHttpClient().execute(request);
				return httpResponse;
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
