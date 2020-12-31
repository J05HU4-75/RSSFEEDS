package com.demo.rssfeeds.webmvc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.rssfeeds.utils.AtomicSequence;
import com.demo.rssfeeds.webmvc.model.RSSFeed;

/**
 * The Service object that would handle all the business logic with RSSFeed objects
 */
@Service
public class RSSFeedService {

	Logger logger = LoggerFactory.getLogger(RSSFeedService.class);

	/**
	 * A more than basic runtime persistence, we should rely on a DAO / Database
	 */
	private Map<Long, RSSFeed> feeds;

	/**
	 * Default constructor
	 */
	public RSSFeedService() {
		logger.debug("Initializing RSSFeedService");
		feeds = new HashMap<Long, RSSFeed>();
	}

	/**
	 * Returns the RSSFeeds of the system matching a search criteria
	 * @param searchCriteria a text as search criteria on the URL of the RSSFeed to filter the results
	 * @return a List of RssFeeds
	 */
	public List<RSSFeed> findURLLike(final String searchCriteria) {
		logger.debug("findURLLike RSSFeeds from the system");
		List<RSSFeed> ret = new ArrayList<RSSFeed>(feeds.values());
		// filtering on search value if it is relevant
		if (searchCriteria != null && !searchCriteria.trim().isEmpty()) {
			final String strimmedSearchCriteria = searchCriteria.trim();
			List<RSSFeed> filtered = ret.stream().filter(line -> line.getUrl().toString().contains(strimmedSearchCriteria))
					.collect(Collectors.toList());
			ret = filtered;
		}
		logger.debug("All RSSFeeds like: {}", ret);

		return ret;
	}

	/**
	 * Creates a feed from its identifier
	 * @param the RSSFeed to be created
	 */
	public Long create(final RSSFeed feed) {
		logger.debug("create RSSFeed {} into the system", feed);
		Long id = AtomicSequence.getNext();
		feed.setId(id);
		feeds.put(id, feed);

		return id;
	}

	/**
	 * Finds a feed from its identifier
	 * @param id the unique identifier of a RSSFeed
	 */
	public RSSFeed findById(final Long id) {
		logger.debug("findById RSSFeed from id: {} ", id);

		return feeds.get(id);
	}

	/**
	 * Updates a feed
	 * @param feed the updated version of the object
	 */
	public void update(final RSSFeed feed) {
		logger.debug("update RSSFeed : {} ", feed);
		feeds.put(feed.getId(), feed);
	}

	/**
	 * Deletes a feed from its identifier
	 * @param id the unique identifier of a RSSFeed
	 */
	public void deleteById(final Long id) {
		logger.debug("deleteById RSSFeed with id : {} ", id);
		feeds.remove(id);
	}
}
