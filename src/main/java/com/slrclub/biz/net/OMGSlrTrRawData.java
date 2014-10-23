package com.slrclub.biz.net;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.slrclub.free.domain.OMGSlrFreeArticle;
import com.slrclub.free.domain.SlrContentsItem;

public class OMGSlrTrRawData {
	private static enum articleDescEnum { EX_UID, EX_UNAME, EX_NO };
	
	
	public OMGSlrTrRawData() {
		super();
	}
	
	public OMGSlrTrRawData(String tdListNum, String tdListSbj,
			String tdListName, String tdListDate, String tdListVote,
			String tdListClick) {
		super();
		this.tdListNum = tdListNum;
		this.tdListSbj = tdListSbj;
		this.tdListName = tdListName;
		this.tdListDate = tdListDate;
		this.tdListVote = tdListVote;
		this.tdListClick = tdListClick;
	}

	private String tdListNum;	// xuid
	private String tdListSbj;	// no, title
	private String tdListName;	// xuid, track, lop
	private String tdListDate;
	private String tdListVote;
	private String tdListClick;
	
	public String getTdListNum() {
		return tdListNum;
	}
	public void setTdListNum(String tdListNum) {
		this.tdListNum = tdListNum;
	}
	public String getTdListSbj() {
		return tdListSbj;
	}
	public void setTdListSbj(String tdListSbj) {
		this.tdListSbj = tdListSbj;
	}
	public String getTdListName() {
		return tdListName;
	}
	public void setTdListName(String tdListName) {
		this.tdListName = tdListName;
	}
	public String getTdListDate() {
		return tdListDate;
	}
	public void setTdListDate(String tdListDate) {
		this.tdListDate = tdListDate;
	}
	public String getTdListVote() {
		return tdListVote;
	}
	public void setTdListVote(String tdListVote) {
		this.tdListVote = tdListVote;
	}
	public String getTdListClick() {
		return tdListClick;
	}
	public void setTdListClick(String tdListClick) {
		this.tdListClick = tdListClick;
	}
	@Override
	public String toString() {
		return "OMGSlrTrRawData [tdListNum=" + tdListNum + ", tdListSbj="
				+ tdListSbj + ", tdListName=" + tdListName + ", tdListDate="
				+ tdListDate + ", tdListVote=" + tdListVote + ", tdListClick="
				+ tdListClick + "]";
	}
	
	/*
	 * tr list
	 * <td class="list_num no_att">32625184</td>
	 * <td class="sbj"><a href="vx2.php?id=free&divpage=5382&amp;no=32625184">이건희 많이 호전됐다고 뉴스에 나오던데</a> [3]</td>
	 * <td class="list_name"><span data-xuid="538108" data-track="32625184" class="lop">한지민과이연희♥</span></td>
	 * <td class="list_date no_att">18:32:47</td>
	 * <td class="list_vote no_att">0</td>
	 * <td class="list_click no_att">34</td>
	 */
	private static final Pattern pattern_no   = Pattern.compile("<td.*?>(\\d+)</td>",  Pattern.DOTALL);
	private static final Pattern pattern_atag = Pattern.compile("<a href=\".+?\">(.+?)( [\\d+])?</a>");
	private static final Pattern pattern_date = Pattern.compile("<td class=\"list_date no_att\">(.+?)</td>");
	private static final Pattern pattern_name = Pattern.compile("<span data-xuid=\"(\\d+)\" data-track=\"(\\d+)\" class=\"lop\">(.+?)</span>");
	
	public SlrContentsItem convertToSlrContentsItem() {
		String no     = articleNumber();
		String span[] = articleDescription();
		String uid   = null;
		String uname = null;
		
//		if( (no != null) && (putKeyOnNonExist(no) == false)) {
//			System.out.println("convertSlrFreeArticle[AA] >> " + no);
//			return null;
//		}
		
		if(span != null) {
			if(no == null)
				no = span[articleDescEnum.EX_NO.ordinal()];
			
			uid   = span[articleDescEnum.EX_UID.ordinal()];
			uname = span[articleDescEnum.EX_UNAME.ordinal()];
		}
		
		String title = articleTitle();
		String date  = articleDate();
		
		if((no != null) && (uid != null) && (uname != null) && (title != null) && (date != null)) {
			SlrContentsItem osfa = new SlrContentsItem();
			
//			osfa.setCid(Long.parseLong(no));
//			osfa.setUid(Long.parseLong(uid));
			
			osfa.setCid(Integer.parseInt(no));
			osfa.setUid(Integer.parseInt(uid));
			
			osfa.setUname(uname);
			osfa.setCtitle(title);
			osfa.setCtime(date);
			
			return osfa;
		}
		return null;
	}
	
	
	// private enum   articleDescEnum { EX_UID, EX_UNAME, EX_NO };
	private String[] articleDescription() {
		String str = tdListName;
		
		if(str == null)
			return null;
		
	    Matcher matcher = pattern_name.matcher(str);
	    
	    if (matcher.find()) {
			String span_xuid  = matcher.group(0x01);
			String span_no    = matcher.group(0x02);
			String span_name  = matcher.group(0x03);
	    	
	        //System.out.println("span_xuid:"+span_xuid + ", span_track:" + span_track + ", span_name:"+span_name);
	        
	        String span[] = new String[0x03]; //  {span_xuid, span_name, span_no};
	        span[articleDescEnum.EX_UID.ordinal()]   = span_xuid;
	        span[articleDescEnum.EX_UNAME.ordinal()] = span_name;
	        span[articleDescEnum.EX_NO.ordinal()]    = span_no;
	        
	        return span;
	    }
	    return null;
	}
	
	private String articleTitle() {
		String str = tdListSbj;
		
		if(str == null)
			return null;
		
	    final Matcher matcher = pattern_atag.matcher(str);
	    
	    if (matcher.find()) {
	    	String a_tag = matcher.group(0x01);
	        //System.out.println(a_tag); // Prints [apple, orange, pear]
	        return a_tag;
	    }
	    return null;
	}
	
	private String articleDate() {
		final String str = tdListDate;
		
		if(str == null)
			return null;
		
	    final Matcher matcher = pattern_date.matcher(str);
	    
	    if (matcher.find()) {
	    	String span_date = matcher.group(0x01);
	    	// 2014-10-09 01:31:01

	    	
	    	SimpleDateFormat formatsArticle = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat transFormat    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	    	Date             dateNow        = new Date();
	    	Calendar         cal            = Calendar.getInstance();
	    	String           stringArticle  = formatsArticle.format(dateNow) + " " + span_date;

	    	cal.setTime(dateNow);
			cal.add(Calendar.HOUR, 0x01);
			dateNow = cal.getTime();
			
	    	try {
				Date genDate = transFormat.parse(stringArticle);
				int  cmp         = genDate.compareTo(dateNow);
				if(cmp > 0x00) {
					System.out.println(">>> DATE MINUS >>> genDate : " + genDate + ", dateNow:" + dateNow);
					cal.setTime(genDate);
					cal.add(Calendar.DATE, -0x01);
					genDate = cal.getTime();
				}
				
				span_date = transFormat.format(genDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				span_date = stringArticle;
			}

	        return span_date;
	    }
	    return null;
	}
	
	private String articleNumber() {
		String item = tdListNum; 
		
		if(item == null)
			return null;
		
		Matcher no_matcher = pattern_no.matcher(item);
		if(no_matcher.find()) {
			try {
				String s_no = no_matcher.group(0x01);
				long   l_no = Long.parseLong(s_no);
				
				return "" + l_no;
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}

}
