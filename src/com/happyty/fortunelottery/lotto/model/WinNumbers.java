package com.happyty.fortunelottery.lotto.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.happyty.fortunelottery.util.T;

public class WinNumbers {
	private static int MAX_LOTTONUM = 46;
	private static WinNumbers instance = null;
	private static final HashMap<Integer, LottoNum> map = new HashMap<Integer, LottoNum>();
	private static final int[] freq = new int[MAX_LOTTONUM];

	public static final WinNumbers getInstance() {
		if (instance == null) {
			instance = new WinNumbers();
		}
		return instance;
	}

	public void insertNumber(int key, LottoNum num) {
		map.put(key, num);
	}

	public void calculateNum() {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			LottoNum a = (LottoNum)pairs.getValue();
			freq[a.getLuckyNumber1()]++;
			freq[a.getLuckyNumber2()]++;
			freq[a.getLuckyNumber3()]++;
			freq[a.getLuckyNumber4()]++;
			freq[a.getLuckyNumber5()]++;
			freq[a.getLuckyNumber6()]++;
			freq[a.getLuckyNumberBonus()]++;
		}

		for (int i = 1; i < MAX_LOTTONUM; i++) {
			T.d("Frequency [" + i + "] => " + freq[i]);
		}
	}
}
