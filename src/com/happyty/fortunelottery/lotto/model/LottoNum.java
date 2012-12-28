package com.happyty.fortunelottery.lotto.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.happyty.fortunelottery.util.T;

public class LottoNum {

	private final static String[] BALL_NAMES = {"First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Bonus"};

	private int xth;
	private long drawDate;
	private int luckyNumber1;
	private int luckyNumber2;
	private int luckyNumber3;
	private int luckyNumber4;
	private int luckyNumber5;
	private int luckyNumber6;
	private int luckyNumberBonus;

	public LottoNum(int xth, long time, int num1, int num2, int num3, int num4, int num5, int num6, int bonus) {
		this.xth = xth;
		this.drawDate = time;
		this.luckyNumber1 = num1;
		this.luckyNumber2 = num2;
		this.luckyNumber3 = num3;
		this.luckyNumber4 = num4;
		this.luckyNumber5 = num5;
		this.luckyNumber6 = num6;
		this.luckyNumberBonus = bonus;
	}

	public LottoNum(JSONObject json) {
		try {
			this.xth = json.getInt("GRWNO");
			this.luckyNumber1 = json.getInt("FirstBall");
			this.luckyNumber2 = json.getInt("SecondBall");
			this.luckyNumber3 = json.getInt("ThirdBall");
			this.luckyNumber4 = json.getInt("FourthBall");
			this.luckyNumber5 = json.getInt("FifthBall");
			this.luckyNumber6 = json.getInt("SixthBall");
			this.luckyNumberBonus = json.getInt("BonusBall");
		} catch (JSONException e) {
			T.e(e.toString());
		}
	}

	public int getXth() {
		return xth;
	}

	public int getLuckyNumber1() {
		return luckyNumber1;
	}

	public int getLuckyNumber2() {
		return luckyNumber2;
	}

	public int getLuckyNumber3() {
		return luckyNumber3;
	}

	public int getLuckyNumber4() {
		return luckyNumber4;
	}

	public int getLuckyNumber5() {
		return luckyNumber5;
	}

	public int getLuckyNumber6() {
		return luckyNumber6;
	}

	public int getLuckyNumberBonus() {
		return luckyNumberBonus;
	}

	public String toString() {
		return xth + "th : " + luckyNumber1 + " | " + luckyNumber2 + " | " + luckyNumber3 + " | "
			+ luckyNumber4 + " | " + luckyNumber5 + " | " + luckyNumber6 + " | " + luckyNumberBonus;
	}
}
