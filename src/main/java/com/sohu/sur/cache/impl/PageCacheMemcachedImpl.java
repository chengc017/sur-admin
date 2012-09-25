package com.sohu.sur.cache.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import com.sohu.sur.cache.PageCache;
import com.sohu.sur.cache.exception.PageCacheException;

/**
 * @author "韩孝冰"
 *
 */
public class PageCacheMemcachedImpl implements PageCache {

	private MemcachedClient memcachedClient;

	public PageCacheMemcachedImpl() {
	}

	public PageCacheMemcachedImpl(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Override
	public void setPage(String key, String pageContent, int expires)
			throws PageCacheException {
		try {
			memcachedClient.set(key, expires, pageContent);
		} catch (Exception e) {
			throw new PageCacheException(e.getMessage());
		}
	}
}
