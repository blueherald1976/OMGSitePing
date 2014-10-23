package kr.co.jinjung.net;

import java.util.List;

import org.apache.http.NameValuePair;

public class OMGHttpRequest {
	public String getConnectionUrl() {
		return url;
	}
	public void setConnectionUrl(String url) {
		this.url = url;
	}
	public String getHttpMethod() {
		return method;
	}
	public void setHttpMethod(String method) {
		this.method = method;
	}
	public List<NameValuePair> getHeads() {
		return heads;
	}
	public void setHeads(List<NameValuePair> heads) {
		this.heads = heads;
	}
	public List<NameValuePair> getParams() {
		return params;
	}
	public void setParams(List<NameValuePair> params) {
		this.params = params;
	}

	public String getEncoding() {
		return encoding;
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	@Override
	public String toString() {
		return "OMGHttpRequest [encoding=" + encoding + ", url=" + url
				+ ", method=" + method + ", heads=" + heads + ", params="
				+ params + "]";
	}

	private String encoding = null;
	private String url = null;
	private String method = null;
	private List<NameValuePair> heads = null;
	private List<NameValuePair> params = null;
}
