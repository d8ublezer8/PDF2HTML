package com.mlb.pdf2html.domain;

public class FileNameSet {
	private String rfilename;
	private String sfilename;

	public String getRfilename() {
		return rfilename;
	}

	public void setRfilename(String rfilename) {
		this.rfilename = rfilename;
	}

	public String getSfilename() {
		return sfilename;
	}

	public void setSfilename(String sfilename) {
		this.sfilename = sfilename;
	}

	@Override
	public String toString() {
		return "FileNameSetDao [rfilename=" + rfilename + ", sfilename=" + sfilename + "]";
	}

}
