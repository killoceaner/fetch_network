package net.trustie.webmagic.utils;

import us.codecraft.webmagic.pipeline.Pipeline;

public abstract class DBPipeline implements Pipeline {
	private String listHtmlTableName = "";
	private String detailHtmlTableName = "";
	private String errorPageTableName = "";
	private String errorUrlTableName = "";
	/**
	 * @return the listHtmlTableName
	 */
	public String getListHtmlTableName() {
		return listHtmlTableName;
	}
	/**
	 * @return the detailHtmlTableName
	 */
	public String getDetailHtmlTableName() {
		return detailHtmlTableName;
	}
	/**
	 * @return the errorPageTableName
	 */
	public String getErrorPageTableName() {
		return errorPageTableName;
	}
	/**
	 * @return the errorUrlTableName
	 */
	public String getErrorUrlTableName() {
		return errorUrlTableName;
	}
	/**
	 * @param listHtmlTableName the listHtmlTableName to set
	 */
	public void setListHtmlTableName(String listHtmlTableName) {
		this.listHtmlTableName = listHtmlTableName;
	}
	/**
	 * @param detailHtmlTableName the detailHtmlTableName to set
	 */
	public void setDetailHtmlTableName(String detailHtmlTableName) {
		this.detailHtmlTableName = detailHtmlTableName;
	}
	/**
	 * @param errorPageTableName the errorPageTableName to set
	 */
	public void setErrorPageTableName(String errorPageTableName) {
		this.errorPageTableName = errorPageTableName;
	}
	/**
	 * @param errorUrlTableName the errorUrlTableName to set
	 */
	public void setErrorUrlTableName(String errorUrlTableName) {
		this.errorUrlTableName = errorUrlTableName;
	}

	
}
