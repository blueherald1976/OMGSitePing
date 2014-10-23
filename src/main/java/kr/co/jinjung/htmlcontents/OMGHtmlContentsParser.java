package kr.co.jinjung.htmlcontents;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OMGHtmlContentsParser {
	private static final String TAG = "OMGHtmlContentsParser";

	static private final Logger log = LoggerFactory.getLogger(OMGHtmlContentsParser.class);
	 
	
	private static final Pattern pattern_object 	= Pattern.compile("<object .*?>(.*?)</object>", /*Pattern.CASE_INSENSITIVE|*/Pattern.MULTILINE|Pattern.DOTALL);
	
	private static final Pattern pattern_embed 		= Pattern.compile("<embed .*?</embed>", /*Pattern.CASE_INSENSITIVE|*/Pattern.MULTILINE|Pattern.DOTALL);
	private static final Pattern pattern_iframe 	= Pattern.compile("<iframe .*?</iframe>", /*Pattern.CASE_INSENSITIVE|*/Pattern.MULTILINE|Pattern.DOTALL);
	private static final Pattern pattern_src 		= Pattern.compile("src=[\"'](.*?)[\"']", /*Pattern.CASE_INSENSITIVE|*/Pattern.DOTALL); //  // "src=\"(.*?)\""
	private static final Pattern pattern_flashvars 	= Pattern.compile("flashvars=\"(.*?)\"", /*Pattern.CASE_INSENSITIVE|*/Pattern.DOTALL);
	
	private static final String iframe_youtube = "<iframe id=\"ytplayer\" allowfullscreen frameborder=\"0\" width=\"320\" height=\"180\" src=\"http://www.youtube.com/embed/";
	private static final String iframe_daum    = "<iframe class=\"daum-player\" allowfullscreen frameborder=\"0\" width=\"320\" height=\"240\" src=\"http://videofarm.daum.net/controller/video/viewer/Video.html?play_loc=tvpot&m_player_type=inline_ad&m_controller_visibility=true&";
	private static final String iframe_pandora = "<iframe allowfullscreen frameborder=\"0\" width=\"320\" height=\"240\" src=\"http://channel.pandora.tv/php/embed.fr1.ptv?";
	private static final String iframe_megaplug = "<iframe allowfullscreen frameborder=\"0\" width=\"320\" height=\"240\" src=\"";
	
	// private static final String iframe_naver_nmv = "<iframe frameborder=\"0\" scrolling=\"no\" width=\"320px\" height=\"240\" src=\"http://serviceapi.nmv.naver.com/view/ugcPlayer.nhn?";
	// private static final String iframe_b_naver_nmv = "&pType=html5\" allowfullscreen=\"\"></iframe><br>";
	private static final String iframe_naver_nmv = "<iframe width=\"320\" height=\"220\" src=\"http://serviceapi.nmv.naver.com/flash/convertIframeTag.nhn?";
	private static final String iframe_b_naver_nmv = "&frameborder=\"no\" scrolling=\"no\"></iframe><br />";

	private static final String iframe_b = "\" type=\"text/html\"></iframe><br>";
	
	private static String slrCommentKey(String str_no) {
		char m[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v'};

		long org_no = Long.parseLong(str_no);
		long no = org_no;
		if(no < 32) {
			return "" + m[(int) no];
			// return new String(m[no]);
		} else {
			String result = "";
			while(true) {
				int quo = (int)no / 32;
				int mod = (int)no % 32;
				result += ("" + m[mod]);
				if (quo < 32 ) {
					result += ("" + m[quo]);
					break;
				} else {
					no = quo;
				}
			}
			
			String reverse = new StringBuffer(result).reverse().toString();
			log.debug(TAG + "cmt(" + org_no + ") key : " + reverse);
			return reverse;
		}
	}
	
//	public static String makeHtmlContents(Message item, String contensts, String templateHtml) {
//		String subject = "";
//		String uname = "";
//		String time  = "";
//		String uid   = "";
//		String no    = "";
//		
//    	if(templateHtml == null)
//    		return contensts;
//		
//		time    = item.getDate();
//		uname   = item.getUserName();
//		uid     = item.getMemberNo();
//		subject = item.getTitle();
//		no      = item.getArticleNo();
//		// getActivity().setTitle(subject);
//		
//		String cmt_ref   = slrCommentKey(no);
//		String html_full = null;
//		
////		html_full = templateHtml.replace("@@subject@@", subject);
////		html_full = html_full.replace("@@uname@@", uname);
////		html_full = html_full.replace("@@time@@", time);
////		
////		html_full = html_full.replaceAll("@@uid@@", uid);
////		html_full = html_full.replaceAll("@@no@@", no);
////		html_full = html_full.replaceAll("@@comment@@", cmt_ref);
//		
//		html_full = StringUtils.replace(templateHtml, "@@comment@@", cmt_ref);
//		html_full = StringUtils.replace(html_full,    "@@uid@@",     uid);
//		html_full = StringUtils.replace(html_full,    "@@no@@",      no);
//		
//		html_full = StringUtils.replaceOnce(html_full,    "@@subject@@", subject);
//		html_full = StringUtils.replaceOnce(html_full,    "@@uname@@",   uname);
//		html_full = StringUtils.replaceOnce(html_full,    "@@time@@",    time);
//		
//		String backup_contensts = parseContents(contensts);
//
//		html_full = html_full.replace("@@contents@@", backup_contensts);
//
//		return html_full;
//	}
//	
	public static String parseContents(String backup_contensts) {
		if(backup_contensts == null || backup_contensts.equals(""))
			return "";
		
		Matcher matcher_object = pattern_object.matcher(backup_contensts);
		while (matcher_object.find()) {
			String object_html = matcher_object.group();
			String inner_object_html = matcher_object.group(0x01);
			// TMT Log.d(TAG, "object_html:" + object_html);
			
			backup_contensts = backup_contensts.replace(object_html, inner_object_html);
			// inner_object_html  += object_html;
		}
		
		// <object classid='clsid:d27cdb6e-ae6d-11cf-96b8-444553540000' codebase='http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0' width='720' height='438' id='NFPlayer00365' align='middle'><embed src="http://serviceapi.nmv.naver.com/flash/NFPlayer.swf?vid=63AAF3A41B42F21966AB7F0CCCD89C1657BB&outKey=V12934a2fadc088d70ea2ed51fd7a4a9ac4e9fa4ca86bd3955035ed51fd7a4a9ac4e9" wmode="window" width="720" height="438" allowscriptaccess="always" name="NFPlayer00365" allowfullscreen="true" type="application/x-shockwave-flash"></embed></object><br />
		Matcher matcher_embed = pattern_embed.matcher(backup_contensts);
		while (matcher_embed.find()) {
			String  embed_html  = new String(matcher_embed.group());
			Matcher matcher_src = pattern_src.matcher(embed_html);
			if (matcher_src.find()) {
				String  url_src = matcher_src.group(0x01);
				
				// TMT Log.d(TAG, "embed_html:" + embed_html);
				if(url_src.contains("youtube.com/") || url_src.contains("youtube-nocookie.com/")) {
					// <embed src="//www.youtube.com/v/AtU2ssBhhhA?hl=ko_KR&amp;version=3&amp;vq=hd720&amp;autoplay=1" width="640" height="385" allowscriptaccess="always" allowfullscreen="true"></embed>
					// Matcher matcher_src = pattern_src.matcher(embed_html);
					//if (matcher_src.find()) {
					//	String  url_src = matcher_src.group(0x01);
						if(url_src.startsWith("//")) url_src = "http:" + url_src;
						// TMT Log.d(TAG, "url_src:" + url_src);
						
						try {
							URL url = new URL(url_src);
							log.debug(TAG + "url_path:" + url.getPath());
							String url_path = url.getPath();
							
							boolean retslash = url_path.contains("/");
							if(retslash && (url_path.length() > 0x04)) {
								String youtube_id = url_path.substring( url_path.lastIndexOf('/')+0x01, url_path.length() );
								String youtube_iframe =  iframe_youtube + youtube_id + iframe_b ;
								backup_contensts = backup_contensts.replace(embed_html, youtube_iframe);
							}	
							// Log.d(TAG, "url_file:" + url.getFile());
						} catch (Exception e) {
							log.debug(TAG + "url :" + url_src);
							// e.printStackTrace();
						}
					//}
				} else if(url_src.contains("daum.net/")) {
					Matcher matcher_flashvars = pattern_flashvars.matcher(embed_html);
					if (matcher_flashvars.find()) {
						String  flashvars = matcher_flashvars.group(0x01);
						
						if((flashvars != null) && (flashvars.length() > 0x04)) {
							// TMT Log.d(TAG, "flashvars:" + flashvars);
							String daum_iframe =  iframe_daum + flashvars + iframe_b ;
							backup_contensts = backup_contensts.replace(embed_html, daum_iframe);
						}	
					}
				} else if(url_src.contains("megaplug.kr/")) {
					// TODO
					// 아래와 같이 할 경우. 최신 폰에서는 보여지는 듯하나, 예전 단말기에서는 보여지지 않음.
					// 32422856, 32422720
					Matcher matcher_flashvars = pattern_flashvars.matcher(embed_html);
					if (matcher_flashvars.find()) {
						String  flashvars = matcher_flashvars.group(0x01);
						
						String  file = null;
						
						Pattern pattern_file = Pattern.compile("file=(.*?)[&$]", /*Pattern.CASE_INSENSITIVE|*/Pattern.DOTALL); //  // "src=\"(.*?)\""
						Matcher matcher_file = pattern_file.matcher(flashvars);
						if(matcher_file.find()) {
							file = matcher_file.group(0x01);
						}

						// flashvars="file=http://abcd.megaplug.kr/an.mp4&amp;skin=http://abcd.megaplug.kr/logo/jc.swf&amp;logo.file
						if((file != null) && (file.length() > 0x10)) {
							// TMT Log.d(TAG, "flashvars:" + flashvars);
							String daum_iframe =  iframe_megaplug + file + iframe_b ;
							backup_contensts = backup_contensts.replace(embed_html, daum_iframe);
						}	
					}
				} else if(url_src.contains("serviceapi.nmv.naver.com/")) {
					//Matcher matcher_src = pattern_src.matcher(embed_html);
					//if (matcher_src.find()) {
					//	String  url_src = matcher_src.group(0x01);
						if(url_src.startsWith("//")) url_src = "http:" + url_src;
						// TMT Log.d(TAG, "url_src:" + url_src);
						
						try {
							URL url = new URL(url_src);
							// TMT Log.d(TAG, "url_Query:" + url.getQuery());
							String url_path = url.getQuery();
							
							String youtube_iframe =  iframe_naver_nmv + url_path + iframe_b_naver_nmv ;
							backup_contensts = backup_contensts.replace(embed_html, youtube_iframe);
							// TMT Log.d(TAG, "url_file:" + url.getFile());
						} catch (Exception e) {
							log.debug(TAG + "url :" + url_src);
							// e.printStackTrace();
						}
					//}
				}  else if(url_src.contains("pandora.tv/")) {
					// userid=looottt&prgid=46205773&countryChk=ko&skin=1&autoPlay=false&share=on
					// Matcher matcher_src = pattern_src.matcher(embed_html);
					// if (matcher_src.find()) {
					//	String  url_src = matcher_src.group(0x01);
						if(url_src.startsWith("//")) url_src = "http:" + url_src;
						// TMT Log.d(TAG, "url_src:" + url_src);
						
						String[] splits = url_src.split(".dll/");
						if(splits.length == 0x02) {
							String youtube_iframe =  iframe_pandora + splits[0x01] + iframe_b ;
							backup_contensts = backup_contensts.replace(embed_html, youtube_iframe);	
						}
					// }
				}
			}
		}
		return backup_contensts;
	}
}
