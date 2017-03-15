package com.tokuda.common.util;

/**
 * Created by s-tokuda on 2016/08/17.
 */
public class UtilMessage {

	public static String build(final String message, final String... args) {
		String result = message;

		for (int i = 0; i < args.length; i++) {
			result = result.replace("{" + i + "}", UtilString.cnvNull(args[i]));
		}
		return result;
	}
}
