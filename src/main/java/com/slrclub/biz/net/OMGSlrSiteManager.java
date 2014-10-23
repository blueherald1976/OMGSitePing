package com.slrclub.biz.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpHeaders;

import com.slrclub.ext.OMGSlrContentsData;
import com.slrclub.free.domain.OMGSlrFreeArticle;
import com.slrclub.free.domain.SlrContentsItem;

import kr.co.jinjung.htmlcontents.OMGHtmlContentsParser;
import kr.co.jinjung.jdbc.SlrClubDB;
import kr.co.jinjung.net.OMGHttpRequest;
import kr.co.jinjung.net.OMGHttpURLConnection;

public class OMGSlrSiteManager {
//	private String slrId;
//	private String slrPwd;
	private boolean bSaveDb = false;
	
	
	private String           slrOnUrl;
	private String           slrOnHttpMethod;
	private OMGSlrHttpParams slrOnhttpParams;
	
	public OMGSlrHttpParams getSlrOnhttpParams() {
		return slrOnhttpParams;
	}

	public void setSlrOnhttpParams(OMGSlrHttpParams slrOnhttpParams) {
		this.slrOnhttpParams = slrOnhttpParams;
	}

	
	public String getSlrOnHttpMethod() {
		return slrOnHttpMethod;
	}

	public void setSlrOnHttpMethod(String slrOnHttpMethod) {
		this.slrOnHttpMethod = slrOnHttpMethod;
	}

	public String getSlrOnUrl() {
		return slrOnUrl;
	}

	public void setSlrOnUrl(String slrOnUrl) {
		this.slrOnUrl = slrOnUrl;
	}


//	public String getSlrId() {
//		return slrId;
//	}
//
//	public void setSlrId(String slrId) {
//		this.slrId = slrId;
//	}
//
//	public String getSlrPwd() {
//		return slrPwd;
//	}
//
//	public void setSlrPwd(String slrPwd) {
//		this.slrPwd = slrPwd;
//	}
	
	public String helloWorld() {
		return "this is hello!!" + slrOnhttpParams;
	}
	
	private OMGHttpRequest slrOnHttpRequest() {
//		String url_0 = "http://m.slrclub.com/login/process.php";
		OMGHttpRequest httpReq = new OMGHttpRequest();

		httpReq.setConnectionUrl(slrOnUrl);
		httpReq.setHttpMethod(slrOnHttpMethod);

		List<NameValuePair> conHttpParams  = new ArrayList<NameValuePair>();
		List<NameValuePair> conHttpHeads   = new ArrayList<NameValuePair>();
	    Iterator            bodyIterator   = ((OMGSlrHttpParams) slrOnhttpParams).getBodyParams().entrySet().iterator();
	    Iterator            headerIterator = ((OMGSlrHttpParams) slrOnhttpParams).getHeaderParams().entrySet().iterator();
	    
	    while (bodyIterator.hasNext()) {
	        Map.Entry pairs = (Map.Entry)bodyIterator.next();
	        
	        String key   = (String)pairs.getKey();
	        String value = (String)pairs.getValue();
	        
	        System.out.println("params key/value ==> " + key + "/" + value);
	        conHttpParams.add(new BasicNameValuePair(key, value));
	    }
		httpReq.setParams(conHttpParams);	    

	    while (headerIterator.hasNext()) {
	        Map.Entry pairs = (Map.Entry)headerIterator.next();
	        // System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        String key   = (String)pairs.getKey();
	        String value = (String)pairs.getValue();
	        
	        System.out.println("heads key/value ==> " + key + "/" + value);
	        conHttpHeads.add(new BasicNameValuePair(key, value));
	    }
		httpReq.setHeads(conHttpHeads);

//		conHttpParams.add(new BasicNameValuePair("user_id",   slrId));
//		conHttpParams.add(new BasicNameValuePair("password",  slrPwd));
//		conHttpParams.add(new BasicNameValuePair("r_url",     "http://m.slrclub.com/l/free"));   // 코맨트 페이지.
//		conHttpParams.add(new BasicNameValuePair("ip_secure", "global")); // 코맨트 리미트.
//		conHttpParams.add(new BasicNameValuePair("id_save",   "on")); // 코맨트 총 갯수.
//		conHttpParams.add(new BasicNameValuePair("x",         "53"));
//		conHttpParams.add(new BasicNameValuePair("y",         "14"));

//		conHttpHeads.add(new BasicNameValuePair("Content-Type" /*HttpHeaders.CONTENT_TYPE*/,    "application/x-www-form-urlencoded; charset=UTF-8"));
//		conHttpHeads.add(new BasicNameValuePair("Accept-Encoding" /*HttpHeaders.ACCEPT_ENCODING*/, "gzip, deflate"));
//		conHttpHeads.add(new BasicNameValuePair("Referer" /*HttpHeaders.REFERER*/,         "http://m.slrclub.com/l/free"));

		return httpReq;
	}
	
	private OMGHttpRequest reportContentsHttpRequest(String no) {
		System.out.println(">> reportContentsHttpRequest no :" + no);
		
		// String url_0 = "http://www.slrclub.com/service/report/show.slr";
		OMGHttpRequest httpReq = new OMGHttpRequest();
		// OMGHttpURLConnection httpURLCon = new OMGHttpURLConnection();
		httpReq.setConnectionUrl("http://m.slrclub.com/service/report/show.slr");
		httpReq.setHttpMethod("POST");
		
		List<NameValuePair> httpParams = new ArrayList<NameValuePair>();
		httpParams.add(new BasicNameValuePair("id", "free"));
		httpParams.add(new BasicNameValuePair("no",  no));
		httpReq.setParams(httpParams);
		
		List<NameValuePair> httpHeads = new ArrayList<NameValuePair>();
		httpHeads.add(new BasicNameValuePair(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8"));
		httpHeads.add(new BasicNameValuePair(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate"));
		httpHeads.add(new BasicNameValuePair(HttpHeaders.REFERER, "http://m.slrclub.com/v/free/" + no));
		httpReq.setHeads(httpHeads);
		
		return httpReq;
	}
	
	public String doShowContents(int no) {
		return doShowContents(Integer.toString(no));
	}
	public String doShowContents(String no) {
		OMGHttpRequest httpReq = reportContentsHttpRequest(no);
		
		OMGHttpURLConnection httpURLCon = new OMGHttpURLConnection();
		httpURLCon.setHttpRequest(httpReq);
		InputStream is = httpURLCon.getInputStream();
		
		String text = stringFromInputStream(is);
		// System.out.println("showContents:" + text);
		
		String STATUS_NOT_LOGIN = "<script>alert('로그인이 필요합니다');</script>";
		if(text.equals(STATUS_NOT_LOGIN)) {
			boolean isok = doSlrOn();
			System.out.println("login ok:" + isok);
			
			return null;
		} else {
			String rtn_text = StringEscapeUtils.unescapeXml(text);
			
			// 본문의 내용을 변경.
			// rtn_text = OMGHtmlContentsParser.parseContents(rtn_text);
			return rtn_text;
		}
	}
	
	private boolean doSlrOn() {
		OMGHttpRequest httpReq = slrOnHttpRequest();
		
		OMGHttpURLConnection httpURLCon = new OMGHttpURLConnection();
		httpURLCon.setHttpRequest(httpReq);
		InputStream is = httpURLCon.getInputStream();
		
		String text = stringFromInputStream(is);
		System.out.println("login msg:" + text);
		String STATUS_LOGIN_OK = "http://m.slrclub.com/l/free";
		if(text.contains(STATUS_LOGIN_OK))
			return true;
		else
			return false;
	}
	
//    public String getKey(String key) { 
//    	return keyHashMap.get(key);
//    }


	
	public ArrayList<SlrContentsItem> getSlrContentsItemList() {
		OMGHttpRequest req = new OMGHttpRequest();
		req.setConnectionUrl("http://www.slrclub.com/bbs/zboard.php?id=free");
		OMGHttpURLConnection urlcon = new OMGHttpURLConnection(req);
		InputStream is = urlcon.getInputStream();
		if(is == null) {
			return null;
		}
		
		String text = stringFromInputStream(is);
		if(text == null) {
			return null;
		}

		ArrayList<SlrContentsItem> items = parseSlrArticlesHtml(text);
		for (SlrContentsItem contents : items) {
			String no = Integer.toString(contents.getCid());
			String html_body = doShowContents(no);
			if(html_body == null) {
				html_body = doShowContents(no);
			}
			
			if(html_body != null) {
				contents.setContents(html_body);
				System.out.println("item no:" +no+ " :: " + html_body);
			}
		}
		
		return items;
	}
	
	private static final Pattern pattern_tr   = Pattern.compile("<tr>.*?</tr>", Pattern.MULTILINE|Pattern.DOTALL);
	private static final Pattern pattern_td   = Pattern.compile("<td.*?</td>",  Pattern.DOTALL);
//	private static final Pattern pattern_no   = Pattern.compile("<td.*?>(\\d+)</td>",  Pattern.DOTALL);
//	private static final Pattern pattern_atag = Pattern.compile("<a href=\".+?\">(.+?)</a>");
//	private static final Pattern pattern_name = Pattern.compile("<span data-xuid=\"(\\d+)\" data-track=\"(\\d+)\" class=\"lop\">(.+?)</span>");
//	private static final Pattern pattern_date = Pattern.compile("<td class=\"list_date no_att\">(.+?)</td>");

	/*
	<tr>
			<td class="list_num no_att">32625184</td>
			<td class="sbj"><a href="vx2.php?id=free&divpage=5382&amp;no=32625184">이건희 많이 호전됐다고 뉴스에 나오던데</a> [3]</td>
			<td class="list_name"><span data-xuid="538108" data-track="32625184" class="lop">한지민과이연희♥</span></td>
			<td class="list_date no_att">18:32:47</td>
			<td class="list_vote no_att">0</td>
			<td class="list_click no_att">34</td>
	</tr>
	*/
	static final int TD_COUNT = 0x06;
	private ArrayList<SlrContentsItem> parseSlrArticlesHtml(String text) {
		if(text == null) {
			System.out.println("ERROR >> slrHtmlParse text IS NULL !!");
		}
		
		text = StringEscapeUtils.unescapeXml(text);
		Matcher matcher_tr = pattern_tr.matcher(text);
		ArrayList<SlrContentsItem> slrContentsItemList = new ArrayList<SlrContentsItem>();
		
		ArrayList<String> tr_items    = new ArrayList<String>();
		while (matcher_tr.find()) {
			tr_items.add(matcher_tr.group());
		}
		
		
		for(int idx_list=0x00; idx_list<tr_items.size(); idx_list++) {
			String  td_items   = tr_items.get(idx_list);
			Matcher matcher_td = pattern_td.matcher(td_items);
			int     idx        = 0x00;
			String  td_array[] = new String[TD_COUNT];

			while(matcher_td.find()) {
				if(idx < TD_COUNT) {
					td_array[idx] = matcher_td.group();
					idx++;
				} else {
					idx = 0x00;
					break;
				}
			}
			
			if(idx != 0x00) {
				OMGSlrTrRawData trRaw = new OMGSlrTrRawData(td_array[0x00], td_array[0x01], td_array[0x02], td_array[0x03], td_array[0x04], td_array[0x05]);
				
				SlrContentsItem sfa = trRaw.convertToSlrContentsItem(); 
				if(sfa != null) {
					int db_key = sfa.getCid();
					if(OMGSlrArticlesInMemoryDB.containsKey(db_key) == false) {
						OMGSlrArticlesInMemoryDB.putKey(db_key);
						System.out.println("TR >> " + sfa);
						
						slrContentsItemList.add(sfa);
					}
					// System.out.println("TR[RA] >> " + trRaw);
				}
			}
			
			
		}

		return slrContentsItemList;
//		if(bSaveDb) {
//			SlrClubDB.putSlrIssueFreeData(issue);
//		} else {
//			System.out.println("PUT DB : " + issue);
//		}
	}

//	private String checkArticleNumber(String item) {
//		Matcher no_matcher = pattern_no.matcher(item);
//		if(no_matcher.find()) {
////			System.out.println("pattern_no YES : " + item);
//			try {
//				String s_no = no_matcher.group(0x01);
//				long no = Long.parseLong(s_no);
//				
//				return "" + no;
//			} catch (NumberFormatException e) {
//				return null;
//			}
//		}
////			System.out.println("pattern_no NO : " + item);
//		return null;
//	}
//	
//
//
//	private static List<String> getTitle(final String str) {
//	    final List<String> tagValues = new ArrayList<String>();
//	    final Matcher matcher = pattern_atag.matcher(str);
//	    
//	    if (matcher.find()) {
//	    	String a_tag = matcher.group(1);
//	        tagValues.add(a_tag);
//	        System.out.println(a_tag); // Prints [apple, orange, pear]
//	    }
//	    
////	    while (matcher.find()) {
////	    	String a_tag = matcher.group(1);
////	        tagValues.add(a_tag);
////	        System.out.println(a_tag); // Prints [apple, orange, pear]
////	    }
//	    return tagValues;
//	}
//
//	private static String getTitle(final String str) {
//	    final Matcher matcher = pattern_atag.matcher(str);
//	    
//	    if (matcher.find()) {
//	    	String a_tag = matcher.group(0x01);
//	        //System.out.println(a_tag); // Prints [apple, orange, pear]
//	        return a_tag;
//	    }
//	    return null;
//	}
//	
//	private static String[] getXuidTrackNoName(final String str) {
//	    final Matcher matcher = pattern_name.matcher(str);
//	    
//	    if (matcher.find()) {
//	    	String span_xuid  = matcher.group(0x01);
//	    	String span_track = matcher.group(0x02);
//	    	String span_name  = matcher.group(0x03);
//	    	
//	        //System.out.println("span_xuid:"+span_xuid + ", span_track:" + span_track + ", span_name:"+span_name);
//	        
//	        String info[] = new String[] {span_xuid, span_track, span_name};
//	        
//	        return info;
//	    }
//	    return null;
//	}
//	
//	private static String getDate(final String str) {
//	    final Matcher matcher = pattern_date.matcher(str);
//	    
//	    if (matcher.find()) {
//	    	String span_date = matcher.group(0x01);
//	    	
//	        //System.out.println("span_date:"+span_date);
//	        return span_date;
//	    }
//	    return null;
//	}
//	
	private String stringFromInputStream(InputStream is) {
		if(is == null)
			return null;
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			StringWriter   sw = new StringWriter();
			char[]         buffer = new char[1024 * 4];
			int            n = 0;
			while (-1 != (n = br.read(buffer)))  
				sw.write(buffer, 0, n);
			br.close();
			is.close();
			
			String text = sw.toString();
			sw = null;
			buffer = null;
			// System.out.println("InputStream:" + text);			
			return text;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("InputStream: null or error");
			return null;
		}
	}
}
