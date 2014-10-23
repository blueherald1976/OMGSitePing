package kr.co.jinjung.net;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookiePolicy;
//import java.net.CookieStore;
//import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.NameValuePair;
import org.hccp.net.CookieManager;

// import android.util.Log;
//import java.net.CookieManager;

public class OMGHttpURLConnection {
	//CookieManager cmanager = null;
	private static final String TAG = "OMGHttpURLConnection";
	
	private CookieManager cm = CookieManager.getInstance();
	private OMGHttpRequest httpReq;

	public OMGHttpURLConnection() {
		this(null);
	}
	
	public OMGHttpURLConnection(OMGHttpRequest httpReq) {
		super();
		//cmanager = new CookieManager();
        //cmanager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        //CookieHandler.setDefault(cmanager);
        // CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
        
		this.httpReq = httpReq;
	}
	
	public OMGHttpRequest getHttpRequest() {
		return httpReq;
	}

	public void setHttpRequest(OMGHttpRequest httpReq) {
		this.httpReq = httpReq;
	}
	
	private String httpQueryString(List<NameValuePair> params, String encoding) {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			String name  = null;
			String value = null;
			
			try {
				name  = URLEncoder.encode(pair.getName(), encoding);
				value = URLEncoder.encode(pair.getValue(), encoding);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				continue;
			}
			
			if (first)
				first = false;
			else
				result.append("&");

			result.append(name);
			result.append("=");
			result.append(value);
		}

		return result.toString();
	}

//	public void getCookieUsingCookieHandler() { 
//	    try {       
//	        // Instantiate CookieManager;
//	        // make sure to set CookiePolicy
//	        CookieManager manager = new CookieManager();
//	        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//	        CookieHandler.setDefault(manager);
//
//	        // get content from URLConnection;
//	        // cookies are set by web site
//	        URL url = new URL("http://host.example.com");
//	        URLConnection connection = url.openConnection();
//	        connection.getContent();
//
//	        // get cookies from underlying
//	        // CookieStore
//	        CookieStore cookieJar =  manager.getCookieStore();
//	        List <HttpCookie> cookies =  cookieJar.getCookies();
//	        for (HttpCookie cookie: cookies) {
//	          System.out.println("CookieHandler retrieved cookie: " + cookie);
//	        }
//	    } catch(Exception e) {
//	        System.out.println("Unable to get cookie using CookieHandler");
//	        e.printStackTrace();
//	    }
//	}
//	
//	public void setCookieUsingCookieHandler() {
//	    try {
//	        // instantiate CookieManager
//	        CookieManager manager = new CookieManager();
//	        CookieHandler.setDefault(manager);
//	        CookieStore cookieJar =  manager.getCookieStore();
//
//	        // create cookie
//	        HttpCookie cookie = new HttpCookie("UserName", "John Doe");
//
//	        // add cookie to CookieStore for a particular URL
//	        URL url = new URL("http://host.example.com");
//	        cookieJar.add(url.toURI(), cookie);
//	        System.out.println("Added cookie using cookie handler");
//	    } catch(Exception e) {
//	        System.out.println("Unable to set cookie using CookieHandler");
//	        e.printStackTrace();
//	    }
//	}
	
	// http://www.hccp.org/java-net-cookie-how-to.html
	public InputStream getInputStream( ) {
		URL url = null;
		try {
			url = new URL(httpReq.getConnectionUrl());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(TAG + "start => new URL(open_url)");
			return null;
		}
		
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			cm.setCookies(conn);
			// http://czheng035.wordpress.com/2012/06/18/cookie-management-in-android-webview-development/
			// Set cookies in requests
			//CookieManager cookieManager = (CookieManager) CookieManager.getDefault();//  .getInstance();

			String        url_string    = conn.getURL().toString();
			String cookie = null;
			
			if (cookie != null) conn.setRequestProperty("Cookie", cookie);

			String up_method = httpReq.getHttpMethod();
			String target_method = null;
			
			if(up_method == null) up_method = "GET";
			if(up_method.equals("POST") || up_method.equals("GET")) target_method = up_method;
			else													target_method = "GET";

			conn.setRequestMethod(target_method);
			//conn.setReadTimeout(10000);
			//conn.setConnectTimeout(15000);
			List<NameValuePair> httpRequestProperty = httpReq.getHeads();
			if(httpRequestProperty != null) {
				for (NameValuePair pair : httpRequestProperty) {
					String name  = pair.getName();
					String value = pair.getValue();

					if(name != null && value != null && !name.equals("") && !value.equals(""))
						conn.setRequestProperty(name, value);
				}
			}
			
			String referer = conn.getRequestProperty("Referer");
			if(referer == null || referer.equals("")) {
				conn.setRequestProperty("Referer", "");
			}
			
			String encoding = httpReq.getEncoding();
			if(encoding == null || encoding.equals("")) encoding = "UTF-8";

			// TODO
			// GET PARAMS
			List<NameValuePair> postBodyParams = httpReq.getParams();
			if(postBodyParams != null) {
				conn.setDoOutput(true);
				OutputStream os = conn.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, encoding));
				writer.write(httpQueryString(postBodyParams, encoding));
				writer.flush();
				writer.close();
				os.close();
			}

			conn.connect();
			cm.storeCookies(conn);
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println(TAG + "start => conn.getResponseCode : " + conn.getResponseCode());
				return null;
			}

			// Get cookies from responses and save into the cookie manager
//			List<String> cookieList = conn.getHeaderFields().get("Set-Cookie");
//			if (cookieList != null) {
//				for (String cookieString : cookieList) {
//					cookieManager.setCookie(url_string, cookieString);
//				}
//			}

			//Get Response
			InputStream is = conn.getInputStream();
			String      contentEncoding = conn.getContentEncoding();
			if(contentEncoding != null && contentEncoding.toLowerCase().equals("gzip")) {
				is = new GZIPInputStream(is);
			}
			
			return is;
		} catch (/*IO*/ Exception e) {
			e.printStackTrace();
			System.out.println(TAG + "start => IOException");
		}
			
		return null;
	}
}
