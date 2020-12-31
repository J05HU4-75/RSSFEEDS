package com.demo.rssfeeds.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * A utility class to get a unique identifier for our demo purpose (fake persistence)
 */
public class AtomicSequence {
	/** the sequence value */
	private static AtomicLong value = new AtomicLong(1);

	/** get the next identifier from the sequence */
	public static Long getNext() {
		return new Long(value.getAndIncrement());
	}
}
