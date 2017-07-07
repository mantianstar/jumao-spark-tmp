package org.jumao.spark.googleAnalytics.utils;



import org.jumao.spark.googleAnalytics.exception.ParamErrorException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *  @Author Kartty
 *  @Date 2014-11-21
 *  @version 2.0
 */
public class DateUtils {
	
	public static final String DATE_STRING = "date string";

    private static final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat NO_SEC_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat NO_MIN_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH");
    private static final SimpleDateFormat NO_HOUR_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat NO_DAY_FORMAT = new SimpleDateFormat("yyyy-MM");
	
	public static String formatToFullF(Timestamp timestamp) {
		return FULL_FORMAT.format(timestamp);
	}

	public static String formatToFullF(Date date) {
		return FULL_FORMAT.format(date);
		
	}
	
	public static String changeToFFStr(String dateTimeStr) throws Exception {
		try {
			return FULL_FORMAT.format(FULL_FORMAT.parse(dateTimeStr));
		} catch (Exception e) {
			throw new ParamErrorException(DATE_STRING, dateTimeStr, "not in format  yyyy-MM-dd HH:mm:ss");
		}
	}
	
	public static Date parseToFFDate(String dateTimeStr) throws Exception {
		try {
		    return FULL_FORMAT.parse(dateTimeStr);
		} catch (Exception e) {
			throw new ParamErrorException(DATE_STRING, dateTimeStr, "not in format  yyyy-MM-dd HH:mm:ss");
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	
	public static String formatToNoSecF(Date date) {
		return NO_SEC_FORMAT.format(date);
	}
	
	public static String formatToNoSecF(Timestamp timestamp) {
		return NO_SEC_FORMAT.format(timestamp);
	}
	
	public static String changeToNoSecF(String dateTimeStr) throws Exception {
		return NO_SEC_FORMAT.format(NO_SEC_FORMAT.parse(dateTimeStr));
	}
	
	public static Date parseToNoSecF(String dateTimeStr) throws Exception {
		try {
		    return NO_SEC_FORMAT.parse(dateTimeStr);
		} catch (Exception e) {
			throw new ParamErrorException(DATE_STRING, dateTimeStr, "not in format  yyyy-MM-dd HH:mm");
		}
	}
	
	
    /////////////////////////////////////////////////////////////////////////////////
	
	
	public static String changeToNoHourF(String dateTimeStr) throws Exception {
		return NO_HOUR_FORMAT.format(NO_HOUR_FORMAT.parse(dateTimeStr));
	}
	
	public static String formatToNoHourF(Date date) throws Exception {
		return NO_HOUR_FORMAT.format(date);
	}
	
	public static Date parseToNoHourF(String dateTimeStr) throws Exception {
		try {
		    return NO_HOUR_FORMAT.parse(dateTimeStr);
		} catch (Exception e) {
			throw new ParamErrorException(DATE_STRING, dateTimeStr, "not in format  yyyy-MM-dd");
		}
	}
	
    /////////////////////////////////////////////////////////////////////////////////
	
	
	public static String changeToNoDayF(String dateTimeStr) throws Exception {
		return NO_DAY_FORMAT.format(NO_DAY_FORMAT.parse(dateTimeStr));
	}
	
	public static String formatToNoDayF(Date date) throws Exception {
		return NO_DAY_FORMAT.format(date);
	}
	
	public static Date parseToNoDayF(String dateTimeStr) throws Exception {
		try {
		    return NO_DAY_FORMAT.parse(dateTimeStr);
		} catch (Exception e) {
			throw new ParamErrorException(DATE_STRING, dateTimeStr, "not in format  yyyy-MM-dd");
		}
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	
	public static Date parseStartWithFF(String dateTimeStr) throws Exception {
		Date date = null;
		try {
			date = FULL_FORMAT.parse(dateTimeStr);
		} catch (Exception e0) {
			date = parseStartWithNoSecF(dateTimeStr);
		}
		return date;
	}
	
	
	public static Date parseStartWithNoSecF(String dateTimeStr) throws Exception {
		Date date = null;
		try {
			date = NO_SEC_FORMAT.parse(dateTimeStr);
		} catch (Exception e) {
			try {
				date = NO_MIN_FORMAT.parse(dateTimeStr);
			} catch (Exception e2) {
				try {
					date = NO_HOUR_FORMAT.parse(dateTimeStr);
				} catch (Exception e3) {
					throw new ParamErrorException(DATE_STRING, dateTimeStr, "not an effictive date string");
				}
			}
		}
		return date;
	}
	
}
