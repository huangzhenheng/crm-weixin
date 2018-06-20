package com.tianque.util;

import java.io.IOException;
import java.util.Properties;

import com.tianque.exception.DataAccessException;

/**
 * 用来读取config.properties文件
 */
public class ConfigUtil {

	private static Properties properties = new Properties();

	static {
		try {
			properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new DataAccessException(e, "读取config.properties文件失败");
		}
	}

	public static String get(String key) {

		return properties.getProperty(key);
	}

	public static String get(String key, String defaultValue) {

		return properties.getProperty(key, defaultValue);
	}
}
