package org.jumao.google.analytics.utils;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class StringUtils {

	private static final Logger LOG = LoggerFactory.getLogger(StringUtils.class);
	
	private static ExecutorService executor = Executors.newFixedThreadPool(10);

	private static Random ran = new Random();

	public static final String NEW_LINE = "\r\n";
	
	public static final String PASSWORD_STOP_WORD = "#";
	
	public static final String UTF8 = "utf8";
	
	
	/**
	 * Six numbers with random rule
	 * 
	 * @return
	 */
	public static String getVerificationCode() {
		return getRandomStr(6);
	}

	public static String getRandomStr(int len) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < len; i++) {
			sb.append(ran.nextInt(10));
		}

		return sb.toString();
	}
	
	/**
	 * [0-9A-Za-z] 
	 */
	public static String getComplexRandomStr(int len) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < len; i++) {
			int term = ran.nextInt(62);
			if (term >= 0 && term <= 9) {
				sb.append((char)(48 + term));
			} else if (term >= 10 && term <= 35) {
				term = term - 10;
				sb.append((char)(65 + term));
			} else {
				term = term - 36;
				sb.append((char)(97 + term));
			}
		}

		return sb.toString();
	}
	
	public static String getStringFromStream(InputStream input) throws Exception {
//		byte[] content = getContentFromStream(input);
		byte[] content = IOUtils.toByteArray(input);
		if (content.length > 0) {
			try {
				return new String(content, "UTF-8");
			} catch (UnsupportedEncodingException e) {}
		}
		
		return "";
	}
	
	
	public static byte[] getContentFromStream(final InputStream input) {
		try {
			Callable<byte[]> readTask = new Callable<byte[]>() {
		        @Override
		        public byte[] call() throws Exception {
		        	ByteArrayOutputStream bao = new ByteArrayOutputStream(1024);
					byte[] bb = new byte[1024];
					int len = 0;
					while ((len = input.read(bb)) > 0) {
						bao.write(bb, 0, len);
					}
					
		            return bao.toByteArray();
		        }
		    };

	        Future<byte[]> future = executor.submit(readTask);
	        byte[] readBytes = future.get(5, TimeUnit.SECONDS);
	        if (readBytes != null && readBytes.length > 0) {
	        	return readBytes;
	        }
	        
	        throw new IOException("Read timeout by Future");
		} catch (Exception e) {
			LOG.warn("Cannot read input stream. cause:" + e.getMessage(), e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					LOG.warn("Cannot close input stream. cause:" + e.getMessage());
				}
			}
		}

		return new byte[0];
	}
	
	/**
	 * Avoid to transfer 'null' to front-end 
	 */
	public static String replaceNullWithBlank(String str) {
		return str == null ? "" : str;
	}
	
	public static String withBlankStr(String str) {
		return Verifier.isEffectiveStr(str) ? str : "";
	}
	
	
	public static String populateException(String msg) {
		JSONObject result = new JSONObject();
		try {
			result.put("code", 1000);
			result.put("msg", msg);
		} catch (JSONException e) {
			LOG.error("", e);
		}
		
		return result.toString();
	}
	
	
	@SuppressWarnings("rawtypes")
	public static String[] getStrArrFromCollection(Collection coll) {
		String[] childrenArr = new String[coll.size()];
		int i = 0;
		for (Object lo : coll) {
			childrenArr[i] = String.valueOf(lo);
			i++;
		}
		return childrenArr;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static int[] getIntArrFromSet(Set set) {
		int[] childrenArr = new int[set.size()];
		int i = 0;
		for (Object lo : set) {
			childrenArr[i] = Integer.parseInt(lo.toString());
			i++;
		}
		return childrenArr;
	}
	
	
	public static String joinStr(Object... objs) {
		if (objs == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			sb.append(obj);
		}
		return sb.toString();
	}
	
	
	public static String kickBlank(String str) {
		if (str == null) {
			return "";
		}
		return str.replace(" ", "");
	}
	
	
	public static byte[] getUtf8Bytes(String str) throws Exception {
		return str.getBytes(UTF8);
	}


	public static String getUtf8Str(byte[] bytes) throws Exception {
		return new String(bytes, UTF8);
	}
	

}
