package org.jumao.spark.googleAnalytics.utils;

import org.jumao.spark.googleAnalytics.utils.basic.BasicVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifier extends BasicVerifier {

	private static final Logger LOG = LoggerFactory.getLogger(Verifier.class);

	private static final String NS_ID_SUFFIX = "n";

	static Pattern phoneP  = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
	static Pattern mailP   = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");
	static Pattern idCardP = Pattern.compile("^\\d{14}(\\d|X|x)$|^\\d{17}(\\d|X|x)$");
	static Pattern namespaceP = Pattern.compile("^\\w{3,20}$");


	/**
	 * With effective element
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEffectiveStr(String str) {
		return isEffectiveStr(str, false);
	}
	
	public static boolean isEffectiveStr(String str, boolean checkMessy) {
		if (str != null && !str.trim().isEmpty()) {
			if (checkMessy) {
				if (isMessyCode(str)) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	public static boolean isEfficStrs(String... strs) {
		for (String str : strs) {
			if (!isEffectiveStr(str)) {
				return false;
			}
		}
		return true;
	}


	/**
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> boolean isEffectiveList(List<T> list) {
		return list != null && list.size() > 0;
	}
	
	public static <T> boolean isEffectiveSet(Set<T> set) {
		return set != null && set.size() > 0;
	}
	
	public static <T> boolean isEffectiveHashSet(HashSet<T> set) {
		return set != null && set.size() > 0;
	}

	/**
	 * @param cardNum
	 * @return
	 */
	public static boolean isEffectivIDCard(String cardNum) {
		if (!isEffectiveStr(cardNum)) {
			return false;
		}

		Matcher m = idCardP.matcher(cardNum);
		return m.find();
	}

	/**
	 * Effective phone number in China. <br>
	 * 13X, 15X, 18X
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static boolean isEffectivePhoneNum(String phoneNum) {
		if (!isEffectiveStr(phoneNum)) {
			return false;
		}

		Matcher m = phoneP.matcher(phoneNum);
		return m.find();
	}

	/**
	 * Effective mail address with wildcard
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean isEffectiveMail(String mail) {
		if (!isEffectiveStr(mail)) {
			return false;
		}

		Matcher m = mailP.matcher(mail);
		return m.find();
	}


	public static boolean isEffectiveDirPath(String path) {
		return isEffectiveStr(path) && !path.equals("/") && path.startsWith("/");
	}


	public static boolean isEffectiveNsName(String name) {
    	 return isEffectiveStr(name) && name.endsWith(NS_ID_SUFFIX);
    }


	/**
	 * To verify verification code
	 * 
	 * @param expect ramaining
	 * @param current incoming from front-end
	 * @return
	 */
	public static boolean verifyCode(String expect, String current) {
		if (!isEffectiveCode(expect)) {
			LOG.warn("Original code doesnot fit for code rule : " + expect);
			return false;
		}

		return (expect != null) && expect.equals(current);
	}

	private static boolean isEffectiveCode(String code) {
		if (code == null || code.length() != 6) {
			return false;
		}

		try {
			Integer.parseInt(code);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	public static boolean isEffectiveStrs(String... strs) {
		for (String str : strs) {
			if (!isEffectiveStr(str)) {
				return false;
			}
		}
		return true;
	}


	public static boolean isNumber(String str) {
		if (!isEffectiveStr(str)) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		try {
//			String gbk = new String("斯蒂芬".getBytes("utf8"), "gbk");
//			System.out.println(gbk);
//			checkCanEncodeAndThrow("47", gbk);345
			System.out.println(isMessyCode("[\"81088\",\"83384\"]"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
