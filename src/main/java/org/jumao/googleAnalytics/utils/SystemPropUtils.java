package org.jumao.googleAnalytics.utils;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.Path;
import org.jumao.google.analytics.constants.Key;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;


public class SystemPropUtils {

//	private static final Log DLOG = LogFactory.getLog(SystemPropUtils.class);
	private static Properties prop = new Properties();

	/**
	 * main 方法中手动初始化。
	 */
	public static void initPropFile(String confPath) {
		InputStream input = null;
		BufferedReader reader = null;
		try {
			if (confPath.startsWith("hdfs")) {
				input = HDFSUtils.instance().open(new Path(confPath));
			} else {
				input = new FileInputStream(confPath);
			}
			reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

			prop.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
			DLOG.error("Failed to load config from '" + confPath
					+ "', cause:" + e.getMessage());
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * Obtains value by specified key
	 * @param key
	 * @return might be null if this key is absent
	 */
	public static String get(String key) {
		return prop.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}


}

