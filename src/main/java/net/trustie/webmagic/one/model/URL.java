package net.trustie.webmagic.one.model;

import java.sql.Timestamp;


/**
 * Created by Administrator on 2014/11/27.
 */

public class URL {
	private int id;
	private String url;
	private String type="Detail";
	//private String created
	
	/**
	 * @param id
	 * @param url
	 * @param createdAt
	 */
	public URL(int id, String url, Timestamp createdAt) {
		this.id = id;
		this.url = url;
		this.createdAt = createdAt;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 */
	public URL() {
	}

	private Timestamp createdAt;

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
	 * @return the createdAt
	 */
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	
}
