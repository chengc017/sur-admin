package com.sohu.sur.cache;

import com.sohu.sur.cache.exception.PageCacheException;

/**
 * @author "韩孝冰"
 * 
 */
public interface PageCache {

	void setPage(String key, String pageContent, int expires)
			throws PageCacheException;
}
