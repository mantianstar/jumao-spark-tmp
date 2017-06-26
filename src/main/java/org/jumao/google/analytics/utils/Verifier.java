package org.jumao.google.analytics.utils;


public class Verifier {


	public static boolean isEffectiveStr(String str) {
		if (str != null && !str.trim().isEmpty()) {
			return true;
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
	
}
