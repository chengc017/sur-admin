package com.sohu.sur.concurrent;

import java.util.concurrent.locks.Lock;

/**
 * 用户更新锁
 *
 * @author 韩孝冰
 * @creation 2011-3-8 上午09:51:47
 * @version 1.0
 */
public interface LockManager {

    /**
     * 根据键值取得锁 
     * @param key 锁的键值
     * @return Lock
     */
    Lock getLock(String key);
}
