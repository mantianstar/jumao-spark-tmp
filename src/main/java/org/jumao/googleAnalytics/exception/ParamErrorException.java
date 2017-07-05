package org.jumao.googleAnalytics.exception;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public class ParamErrorException extends Exception {

	private static final Logger LOG = LoggerFactory.getLogger(ParamErrorException.class);
	
	private String paramName = "";
	private Object paramVal = "";
	private String reason = "";
	
	private Object[] nameValArr;
	
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public Object getParamVal() {
		return paramVal;
	}
	public void setParamVal(Object paramVal) {
		this.paramVal = paramVal;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	public ParamErrorException() {
	}
	
	public ParamErrorException(Object... nameValArr) {
		this.nameValArr = nameValArr;
	}
	
	public ParamErrorException(String paramName, Object paramVal) {
		this.paramName = paramName;
		this.paramVal = paramVal;
	}
	
	public ParamErrorException(String paramName, Object paramVal, String reason) {
		this.paramName = paramName;
		this.paramVal = paramVal;
		this.reason = reason;
	}
	
	public String toString() {
		JSONObject toStr = new JSONObject();
		try {
			if (nameValArr == null) {
				toStr.put("paramName", paramName).put("paramVal", paramVal.toString()).put("reason", reason);
			} else {
				int len = nameValArr.length;
				if (len != 0 && len % 2 == 0) {
					toStr.put("keyIsParamName", "valIsParamVal");
					for (int i = 0; i < len; i += 2) {
						toStr.put(nameValArr[i].toString(), nameValArr[i + 1].toString());
					}
				}
			}
		} catch (JSONException e) {
			LOG.error("" ,e);
		}
		return toStr.toString();
	}
	
	
}
