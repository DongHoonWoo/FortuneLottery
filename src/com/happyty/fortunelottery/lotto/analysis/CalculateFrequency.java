package com.happyty.fortunelottery.lotto.analysis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.happyty.fortunelottery.lotto.model.LottoNum;

public class CalculateFrequency {
	private static final byte[] frequencyMatrix = new byte[46 * 1024];

	private void checkFrequency(HashMap<Integer, LottoNum> numbers) {
		Iterator it = numbers.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry)it.next();
			LottoNum a = (LottoNum)pairs.getValue();
			frequencyMatrix[i * 46 + a.getLuckyNumber1()] = 1;
			frequencyMatrix[i * 46 + a.getLuckyNumber2()] = 1;
			frequencyMatrix[i * 46 + a.getLuckyNumber3()] = 1;
			frequencyMatrix[i * 46 + a.getLuckyNumber4()] = 1;
			frequencyMatrix[i * 46 + a.getLuckyNumber5()] = 1;
			frequencyMatrix[i * 46 + a.getLuckyNumber6()] = 1;
			frequencyMatrix[i * 46 + a.getLuckyNumberBonus()] = 1;
		}
	}
}
