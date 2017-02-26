package com.tokuda.common.util;

import com.tokuda.common.constant.CommonConst;

/**
 * Created by s-tokuda on 2016/08/28.
 */
public class UtilString {

	public static String cnvNull(final String input) {
		String result;

		if (input != null) {
			result = input;
		} else {
			result = CommonConst.Blank.getValue();
		}
		return result;
	}

	public static boolean isEmpty(final String input) {
		return cnvNull(input).isEmpty();
	}

	public static String getYear(final String input) {
		String result;
		String[] date = UtilString.cnvNull(input).split(CommonConst.Slash.getValue());

		if (date.length == 3) {
			result = date[0];
		} else {
			result = CommonConst.Blank.getValue();
		}
		return result;
	}

	public static String getMonth(final String input) {
		String result;
		String[] date = UtilString.cnvNull(input).split(CommonConst.Slash.getValue());

		if (date.length == 3) {
			result = date[1];
		} else {
			result = CommonConst.Blank.getValue();
		}
		return result;
	}

	public static String getDay(final String input) {
		String result;
		String[] date = UtilString.cnvNull(input).split(CommonConst.Slash.getValue());

		if (date.length == 3) {
			result = date[2];
		} else {
			result = CommonConst.Blank.getValue();
		}
		return result;
	}
}