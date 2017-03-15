package com.tokuda.common.util;

import com.tokuda.common.constant.PropertyKeyConst;

/**
 * Created by s-tokuda on 2016/08/28.
 */
public class UtilString {

	public static String cnvNull(final String input) {
		String result;

		if (input != null) {
			result = input;
		} else {
			result = UtilProperty.getValue(PropertyKeyConst.Item_Blank.getValue());
		}
		return result;
	}

	public static boolean isEmpty(final String input) {
		return cnvNull(input).isEmpty();
	}

	public static String trim(final String target, final String trimStr) {
		String result = target;

		if (target.startsWith(trimStr)) {
			result = result.substring(trimStr.length());
		}
		if (target.endsWith(trimStr)) {
			result = result.substring(0, result.length() - trimStr.length());
		}
		return result;
	}

	public static String getYear(final String input) {
		String result;
		String[] date = UtilString.cnvNull(input).split(UtilProperty.getValue(PropertyKeyConst.Item_Slash.getValue()));

		if (date.length == 3) {
			result = date[0];
		} else {
			result = UtilProperty.getValue(PropertyKeyConst.Item_Blank.getValue());
		}
		return result;
	}

	public static String getMonth(final String input) {
		String result;
		String[] date = UtilString.cnvNull(input).split(UtilProperty.getValue(PropertyKeyConst.Item_Slash.getValue()));

		if (date.length == 3) {
			result = date[1];
		} else {
			result = UtilProperty.getValue(PropertyKeyConst.Item_Blank.getValue());
		}
		return result;
	}

	public static String getDay(final String input) {
		String result;
		String[] date = UtilString.cnvNull(input).split(UtilProperty.getValue(PropertyKeyConst.Item_Slash.getValue()));

		if (date.length == 3) {
			result = date[2];
		} else {
			result = UtilProperty.getValue(PropertyKeyConst.Item_Blank.getValue());
		}
		return result;
	}
}