package org.jumao.spark.googleAnalytics.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/** 
 * @Author kartty
 * @Since  2015-1-5
 * @version 1.0
 */
public class DigestUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DigestUtils.class);

	private static final String UTF8 = "utf8";
	private static Base64 coder = new Base64();

	/**
	 * Encode with SHA-1
	 * @param str
	 * @return
	 */
	public static String encodeSHA1(String str) {
		try {
			return encodeSHA1(str.getBytes(UTF8));
		} catch (Exception e) {
			LOG.warn("", e);
			return null;
		}
	}
	
	public static String encodeSHA1(byte[] content) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");

			byte[] result = md.digest(content);
			return getHexByBytes(result);
		} catch (Exception e) {
			LOG.warn("", e);
			return null;
		}
	}
	
	public static String encodeMD5(byte[] content) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");

			byte[] result = md.digest(content);
			return getHexByBytes(result);
		} catch (Exception e) {
			LOG.warn("", e);
			return null;
		}
	}
	
	private static String getHexByBytes(byte[] result) {
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			hexValue.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return hexValue.toString();
	}

	/**
	 * Encode with BASE64
	 */
	public static String encodeBase64(String str) {
		try {
			if (str == null) return "";
			return new String(coder.encode(str.getBytes(UTF8)), UTF8);
		} catch (Exception e) {
			LOG.warn("", e);
			return "";
		}
	}

	/**
	 * @param encoded
	 */
	public static String decodeBase64(String encoded) {
		try {
			if (encoded == null) return "";
			byte[] b = coder.decode(encoded.getBytes(UTF8));
			return new String(b, UTF8);
		} catch (Exception e) {
			LOG.warn("", e);
			return "";
		}
	}
	
	
}
