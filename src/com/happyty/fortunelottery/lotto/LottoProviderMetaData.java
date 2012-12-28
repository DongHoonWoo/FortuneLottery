package com.happyty.fortunelottery.lotto;

import android.net.Uri;
import android.provider.BaseColumns;

public class LottoProviderMetaData implements BaseColumns {
	public static final String AUTHORITY = "com.happyty.fortunelottery.provider.LottoProvider";

	public static final String DATABASE_NAME = "lotto.db";
	public static final int DATABASE_VERSION = 1;
	public static final String BOOKS_TABLE_NAME = "lottos";

	private LottoProviderMetaData() {
	}

	public static class LottoTableMetaData implements BaseColumns {

		private LottoTableMetaData() {
		}

		public static final String TABLE_NAME = "lottos";
		//uri and MIME type definitions
		public static final Uri CONTENT_URI =
				Uri.parse("content://" + AUTHORITY + "/lottos");

		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/vnd.fortunelottery.lotto";

		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd.fortunelottery.lotto";

		public static final String DEFAULT_SORT_ORDER = "xth ASC";

		//Additional Columns start here.
		//int type
		public static final String NUMBER_OF_TIME = "xth";

		//long type
		public static final String DATE = "date";

		//int type
		public static final String LUCKY_NUMBER_1 = "luckyNum1";
		public static final String LUCKY_NUMBER_2 = "luckyNum2";
		public static final String LUCKY_NUMBER_3 = "luckyNum3";
		public static final String LUCKY_NUMBER_4 = "luckyNum4";
		public static final String LUCKY_NUMBER_5 = "luckyNum5";
		public static final String LUCKY_NUMBER_6 = "luckyNum6";
		public static final String LUCKY_NUMBER_BONUS = "luckyNumBonus";
	}

}
