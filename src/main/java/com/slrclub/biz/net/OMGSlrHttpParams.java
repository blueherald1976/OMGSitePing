package com.slrclub.biz.net;

import java.util.HashMap;

public class OMGSlrHttpParams {
	public HashMap<String, String> getBodyParams() {
		return bodyParams;
	}
	
	public void setBodyParams(HashMap<String, String> bodyParams) {
		this.bodyParams = bodyParams;
	}
	
	public HashMap<String, String> getHeaderParams() {
		return headerParams;
	}
	
	public void setHeaderParams(HashMap<String, String> headerParams) {
		this.headerParams = headerParams;
	}
	
	@Override
	public String toString() {
		return "OMGSlrHttpParams [bodyParams=" + bodyParams + ", headerParams="
				+ headerParams + "]";
	}
	
	private HashMap<String, String> bodyParams;
	private HashMap<String, String> headerParams;
}
