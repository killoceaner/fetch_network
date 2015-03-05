package net.trustie.webmagic.one.model;

public abstract class AbstractHtml {
	private int id;
	private String url;
	private String html;
	private String crawledTime;
	private String urlMd5 = "";
	private String pageMd5 = "";
	private int statusCode = 200;
	// 页面类型
	private String type = "Detail";

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}

	/**
	 * @return the crawledTime
	 */
	public String getCrawledTime() {
		return crawledTime;
	}

	/**
	 * @return the urlMd5
	 */
	public String getUrlMd5() {
		return urlMd5;
	}

	/**
	 * @return the pageMd5
	 */
	public String getPageMd5() {
		return pageMd5;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param html
	 *            the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * @param crawledTime
	 *            the crawledTime to set
	 */
	public void setCrawledTime(String crawledTime) {
		this.crawledTime = crawledTime;
	}

	/**
	 * @param urlMd5
	 *            the urlMd5 to set
	 */
	public void setUrlMd5(String urlMd5) {
		this.urlMd5 = urlMd5;
	}

	/**
	 * @param pageMd5
	 *            the pageMd5 to set
	 */
	public void setPageMd5(String pageMd5) {
		this.pageMd5 = pageMd5;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
