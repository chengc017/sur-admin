/**
 * 
 */
package com.sohu.sur.util;

import java.util.LinkedHashMap;

/**
 * @author 韩孝冰
 * @creation 2011-3-7 下午02:40:53
 * @version 1.0
 * 
 * LinkedHashMap的LRU扩展
 */
@SuppressWarnings("serial")
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> {
	
	private static final int DEFAULTMAXSIZE = 1000;

	private int maxSize;
	
	/**
	 * 
	 */
	public LRULinkedHashMap() {
		this(DEFAULTMAXSIZE);
	}
	
	/**
	 * 
	 */
	public LRULinkedHashMap(int maxSize) {
		super(16,0.75f,true);
		this.maxSize = maxSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> arg0) {
		return size() > maxSize;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
