package com.slrclub.free.domain;

import java.io.Serializable;

public class SlrContentsItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2655687189585758581L;

	private int cid;
	private int uid;
	private String uname;
	private String ctime;
	private String ctitle;
	private String contents;
	private char cready = 'N';
	
	public char getCready() {
		return cready;
	}
	public void setCready(char cready) {
		this.cready = cready;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		if(contents != null) {
			cready = 'Y';
		}
		this.contents = contents;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getCtitle() {
		return ctitle;
	}
	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	@Override
	public String toString() {
		return "SlrContentsItem [cid=" + cid + ", uid=" + uid + ", uname="
				+ uname + ", ctime=" + ctime + ", ctitle=" + ctitle + ", contents=" + contents + "]";
	}
}
