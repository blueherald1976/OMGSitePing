package com.slrclub.ext;

public class OMGSlrContentsData {

	int track_no;
	String createdate;

	int uid;
	String uname;
	
	String subject;
	String issuetext;
	
	public OMGSlrContentsData(int track_no, String createdate, int uid,
			String uname, String subject, String issuetext) {
		
		this.track_no = track_no;
		this.createdate = createdate;
		this.uid = uid;
		this.uname = uname;
		this.subject = subject;
		this.issuetext = issuetext;
	}

	public int getTrack_no() {
		return track_no;
	}

	public void setTrack_no(int track_no) {
		this.track_no = track_no;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuetext() {
		return issuetext;
	}

	public void setIssuetext(String issuetext) {
		this.issuetext = issuetext;
	}
	
	@Override
	public String toString() {
		return "SlrIssueFreeData [track_no=" + track_no + ", createdate="
				+ createdate + ", uid=" + uid + ", uname=" + uname
				+ ", subject=" + subject + ", issuetext=" + issuetext + "]";
	}

}
