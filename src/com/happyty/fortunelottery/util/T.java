/*
***************************************************************************
**          WiseStone Co. Ltd. CONFIDENTIAL AND PROPRIETARY
**        This source is the sole property of WiseStone Co. Ltd.
**      Reproduction or utilization of this source in whole or in part 
**    is forbidden without the written consent of WiseStone Co. Ltd.
***************************************************************************
**                 Copyright (c) 2007 WiseStone Co. Ltd.
**                           All Rights Reserved
***************************************************************************
** Revision History:
** Author                 Date          Version      Description of Changes
** ------------------------------------------------------------------------
** dhwoo     2010. 3. 12.        1.0              Created
*/

package com.happyty.fortunelottery.util;

import android.util.Log;

public class T
{
	private final static String PREFIX = "FortuneLotto";
	private static Boolean m_isEnabled = true;

	public static void enable(Boolean isEnable)
	{
		m_isEnabled = isEnable;
	}

	public static void e(String strMsg)
	{
		if (m_isEnabled)
		{
			Exception e = new Exception();
			StackTraceElement callerElement = e.getStackTrace()[1];
			Log.e(PREFIX, "[" + callerElement.getFileName() + ":"
				+ callerElement.getLineNumber() + "]" + strMsg);
		}
	}

	public static void w(String strMsg)
	{
		if (m_isEnabled)
		{
			Exception e = new Exception();
			StackTraceElement callerElement = e.getStackTrace()[1];
			Log.w(PREFIX, "[" + callerElement.getFileName() + ":"
				+ callerElement.getLineNumber() + "]" + strMsg);
		}
	}

	public static void i(String strMsg)
	{
		if (m_isEnabled)
		{
			Exception e = new Exception();
			StackTraceElement callerElement = e.getStackTrace()[1];
			Log.i(PREFIX, "[" + callerElement.getFileName() + ":"
				+ callerElement.getLineNumber() + "]" + strMsg);
		}
	}

	public static void d(String strMsg)
	{
		if (m_isEnabled)
		{
			Exception e = new Exception();
			StackTraceElement callerElement = e.getStackTrace()[1];
			Log.d(PREFIX, "[" + callerElement.getFileName() + ":"
				+ callerElement.getLineNumber() + "]" + strMsg);
		}
	}
}
