package com.happyty.fortunelottery.lotto;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.happyty.fortunelottery.util.StringUtils;
import com.happyty.fortunelottery.util.T;

public class LottoResponse {
	private String responseAsString = null;
	private HttpResponse httpResponse;
	private byte[] data = null;

	public LottoResponse(HttpResponse response) {
		this.httpResponse = response;
		try {
			InputStream is = response.getEntity().getContent();
			if (is != null
				&& "gzip".equals(response.getEntity().getContentEncoding())) {
				is = new GZIPInputStream(is);
			}
			BufferedInputStream bis = new BufferedInputStream(is);
			int len = StringUtils.toInt(this.getHeader("Content-Length"), -1);
			int n = 0;
			if (len == -1) {
				data = new byte[512];
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				while ((n = bis.read(data)) > 0) {
					os.write(data, 0, n);
				}
				data = os.toByteArray();
			} else {
				data = new byte[len];
				int readed = 0;
				do {
					n = bis.read(data, readed, len - readed);
					readed += n;
				} while (n > 0 && readed < len);
			}
		} catch (Exception e) {
			T.e(e.toString());
		}
	}

	public int getStatusCode() {
		return this.httpResponse.getStatusLine().getStatusCode();
	}

	public String getHeader(String name) {
		if (httpResponse != null) {
			Header header = httpResponse.getFirstHeader(name);
			if (header != null) {
				return header.getValue();
			}
		}
		return null;
	}

	public String asString() throws Exception {
		try {
			if (responseAsString == null) {
				responseAsString = new String(data, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			throw new Exception("UnsupportedEncodingException", e);
		}
		return responseAsString;
	}

	public JSONObject asJson() {
		try {
			return new JSONObject(asString().replace("[", "").replace("]", ""));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
