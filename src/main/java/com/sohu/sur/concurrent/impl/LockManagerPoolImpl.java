/**
 *
 */
package com.sohu.sur.concurrent.impl;

import com.sohu.sur.concurrent.LockManager;
import com.sohu.sur.util.LRULinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 韩孝冰
 * @version 1.0
 * @creation 2011-3-8 上午09:54:03
 */
public class LockManagerPoolImpl implements LockManager {

    private static Logger logger = LoggerFactory.getLogger(LockManagerPoolImpl.class);

    /**
     *
     */
    public LockManagerPoolImpl() {
        lockPool = Collections.synchronizedMap(new LRULinkedHashMap<String, Lock>());
    }

    /**
     *
     */
    public LockManagerPoolImpl(int poolSize) {
        lockPool = Collections.synchronizedMap(new LRULinkedHashMap<String, Lock>(poolSize));
    }


    /* (non-Javadoc)
      * @see com.sohu.sur.concurrent.LockManager#getLock(java.lang.String)
      */

    @Override
    public Lock getLock(String key) {
        if (!lockPool.containsKey(key)) {
            newLock(key);
        }
        return lockPool.get(key);
    }

    private synchronized void newLock(String key) {
        if (!lockPool.containsKey(key)) {
        	if (logger.isDebugEnabled())
            logger.debug("will create a new lock for {}", key);
            Lock lock = new ReentrantLock(true);
            lockPool.put(key, lock);
        }
    }

    private Map<String, Lock> lockPool;
}
