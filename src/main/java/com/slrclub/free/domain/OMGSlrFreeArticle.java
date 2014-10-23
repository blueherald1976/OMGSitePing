package com.slrclub.free.domain;

public class OMGSlrFreeArticle {
	private long cid;
	private long uid;
	private char cready = 'N';
	
	private String uname;
	private String ctime;
	private String ctitle;
	
	private String contents = null;

	
	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public char getCready() {
		return cready;
	}

	public void setCready(char cready) {
		this.cready = cready;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	@Override
	public String toString() {
		return "OMGSlrFreeArticle [cid=" + cid + ", uid=" + uid + ", cready="
				+ cready + ", uname=" + uname + ", ctime=" + ctime
				+ ", ctitle=" + ctitle + ", contents=" + contents + "]";
	}
}
