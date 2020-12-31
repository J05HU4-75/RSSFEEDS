package com.demo.rssfeeds.webmvc.model;

import java.net.URL;

/**
 * This class is used as a in parameter for creation / update of a RSSFeed
 */
public class RSSFeedIn implements RSSFeedIF {
	/** the URL of the feed */
	protected URL url;

	/** the refresh rate of the feed in minutes */
	protected Long refreshRate;

	/**
	 * Default Constructor
	 */
	public RSSFeedIn() {
	}

	/**
	 * Getter method
	 */
	@Override
	public URL getUrl() {
		return url;
	}

	/**
	 * Setter method
	 */
	@Override
	public void setUrl(final URL url) {
		this.url = url;
	}

	/**
	 * Getter method
	 */
	@Override
	public Long getRefreshRate() {
		return refreshRate;
	}

	/**
	 * Setter method
	 */
	@Override
	public void setRefreshRate(final Long refreshRate) {
		this.refreshRate = refreshRate;
	}

	/**
	 * String representation of the RSSFeed
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("RSSFeed: ").append(" / URL:").append(url).append(" / refreshRate:").append(refreshRate);
		return sb.toString();
	}
}
