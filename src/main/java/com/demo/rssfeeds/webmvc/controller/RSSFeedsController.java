package com.demo.rssfeeds.webmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.rssfeeds.exception.InvalidRequestException;
import com.demo.rssfeeds.webmvc.model.RSSFeed;
import com.demo.rssfeeds.webmvc.model.RSSFeedIF;
import com.demo.rssfeeds.webmvc.model.RSSFeedIn;
import com.demo.rssfeeds.webmvc.service.RSSFeedService;

/**
 * This class is the REST Controller for the RSSFeed object. It handles the HTTP (JSON) requests for CRUD operations on RSSFeed object
 */
@RestController
@RequestMapping(value = "/rssfeeds")
public class RSSFeedsController {
	Logger logger = LoggerFactory.getLogger(RSSFeedsController.class);

	/** the service which would handle persistence */
	@Qualifier("feedService")
	@Autowired
	private RSSFeedService feedService;

	/**
	 * Finds all the RSSFeeds of the system
	 * @return a List of RSSFeeds
	 */
	@GetMapping
	public Map<String, List<RSSFeed>> findRSSFeedWithURLLike(@RequestParam final String search) {
		logger.debug("findURLLike search: {}", search);

		Map<String, List<RSSFeed>> ret = new HashMap<String, List<RSSFeed>>();
		ret.put("rssFeeds", feedService.findURLLike(search));

		return ret;
	}

	/**
	 * Retrieves a particular RSSFeed of the system from its unique identifier
	 * @param id the unique identifier of a RSSFeed
	 * @return a RSSFeed if any matching the unique identifier
	 */
	@GetMapping(value = "/{id}")
	public RSSFeed findRSSFeedById(@PathVariable("id") final Long id) {
		logger.debug("findById - id: {}", id);
		RSSFeed ret = feedService.findById(id);
		logger.debug("Found by id - feed: {}", ret);
		checkNotNullAndHandle(ret);

		return ret;
	}

	/**
	 * Creates a RSSFeed in the system
	 * @param feedIn the RSS Feed to be created in the system
	 * @return the unique identifier of the newly created RSSFeed
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Long createRSSFeed(@RequestBody final RSSFeedIn feedIn) {
		logger.debug("create - feed: {}", feedIn);
		checkValidAndHandle(feedIn);
		checkUnicityOfFeed(feedIn);

		RSSFeed feed = new RSSFeed();
		feed.setUrl(feedIn.getUrl());
		feed.setRefreshRate(feedIn.getRefreshRate());
		return feedService.create(feed);
	}

	/**
	 * Updates a RSSFeed in the system
	 * @param feedIn the RSS Feed to be created in the system
	 * @return the unique identifier of the newly created RSSFeed
	 */
	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateRSSFeed(@PathVariable("id") final Long id, @RequestBody final RSSFeedIn feedIn) {
		logger.debug("update - id: {} feed: {}", id, feedIn);
		checkValidAndHandle(feedIn);
		RSSFeed existingFeed = feedService.findById(id);
		checkNotNullAndHandle(existingFeed);
		// if the URL is getting updated, check for unicity
		if (!existingFeed.getUrl().equals(feedIn.getUrl())) {
			checkUnicityOfFeed(feedIn);
		}

		existingFeed.setUrl(feedIn.getUrl());
		existingFeed.setRefreshRate(feedIn.getRefreshRate());
		feedService.update(existingFeed);
	}

	/**
	 * Deletes a RSSFeed of the system
	 * @param id the unique identifier of the RSSFeed to be deleted in the system
	 */
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteRSSFeed(@PathVariable("id") final Long id) {
		logger.debug("delete - id: {}", id);
		checkNotNullAndHandle(feedService.findById(id));
		feedService.deleteById(id);
	}

	/**
	 * A helper method to handle the situation where one of the parameters of the RSS Feed is not correct
	 * @param feed the RSS Feed to be checked
	 */
	private void checkValidAndHandle(final RSSFeedIF feed) {
		if (feed == null) {
			throw new InvalidRequestException("The RSS Feed should not be null");
		}
		if (feed.getUrl() == null || feed.getRefreshRate() == null || feed.getRefreshRate() <= 0) {
			throw new InvalidRequestException(
					"Check that attributes url and refreshRate are present in the RSS Feed and that refreshRate is strictly positive");
		}
	}

	/**
	 * A helper method to handle the situation where the retrieved RSS Feed is null
	 * @param feed the RSS Feed to be checked
	 */
	private void checkNotNullAndHandle(final RSSFeedIF feed) {
		if (feed == null) {
			throw new InvalidRequestException("The RSS Feed is null or does not exist in the system");
		}
	}

	/**
	 * A helper method to check that we are not getting two identical feeds via creation
	 * @param feed the RSS Feed to be check against URL unicity
	 */
	private void checkUnicityOfFeed(final RSSFeedIF feed) {
		// check unicity of the URL, do not create two feeds with the same URL
		if (!feedService.findURLLike(feed.getUrl().toString()).isEmpty()) {
			throw new InvalidRequestException("A RSS Feed with the same URL already exists: " + feed.getUrl().toString());
		}
	}
}
