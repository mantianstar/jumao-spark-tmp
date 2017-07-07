package org.jumao.spark.googleAnalytics.utils.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class BasicVerifier {

	private static final Logger LOG = LoggerFactory.getLogger(BasicVerifier.class);
	
	private static final Pattern FIRST_P = Pattern.compile("\\s*|\t*|\r*|\n*");
	private static final Pattern SECOND_P = Pattern.compile(
			"[`~!@#$%^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ +-\\\\_\"]");
	
	//fail to filter emoji after try many times
	//private static final Pattern EMOJI_P = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
	
	protected static boolean isMessyCode(String srcStr) {
        String after = FIRST_P.matcher(srcStr).replaceAll("");
        String temp = SECOND_P.matcher(after).replaceAll("").trim();
        //temp = EMOJI_P.matcher(temp).replaceAll("");
        
        if (temp.length() == 0) {
			return false;
		}
        
        char[] ch = temp.toCharArray();
        float notLetrOrDigitCount = 0 ;
        float messyCount = 0;
        
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    messyCount = messyCount + 1;
                }
                notLetrOrDigitCount++; 
            }
        }
        float result = messyCount / notLetrOrDigitCount ;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }
	
	
	private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
	
	
}
