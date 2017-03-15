package com.tokuda.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.tokuda.attachecase.SystemData;

/**
 * Created by s-tokuda on 2017/03/15.
 */
public class UtilProperty {

	protected static Map<String, String> properties = new HashMap<>();

	static {
		ResourceBundle common = ResourceBundle.getBundle("resource/property/common", Locale.getDefault());
		Enumeration<String> commonKeys = common.getKeys();

		while (commonKeys.hasMoreElements()) {
			String key = commonKeys.nextElement();
			properties.put(key, common.getString(key));
		}

		SystemData.config.getProducts().stream().forEach(product -> {
			ResourceBundle resource = ResourceBundle.getBundle("resource/property/" + product, Locale.getDefault());
			Enumeration<String> resourceKeys = resource.getKeys();

			while (resourceKeys.hasMoreElements()) {
				String key = resourceKeys.nextElement();
				properties.put(key, resource.getString(key));
			}
		});
	}

	public static String getValue(final String key) {
		return UtilString.trim(properties.get(key), "\"");
	}
}
