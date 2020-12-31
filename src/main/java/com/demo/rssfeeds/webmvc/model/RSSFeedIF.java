package com.demo.rssfeeds.webmvc.model;

import java.net.URL;

/**
 * An interface to describe the core of a RSS Feed
 */
public interface RSSFeedIF {
	/**
	 * Getter method
	 */
	public URL getUrl();

	/**
	 * Setter method
	 */
	public void setUrl(final URL url);

	/**
	 * Getter method
	 */
	public Long getRefreshRate();

	/**
	 * Setter method
	 */
	public void setRefreshRate(final Long refreshRate);
}
