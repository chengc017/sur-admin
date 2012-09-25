package com.sohu.sur.model.exception;

/**
 * User: guoyong
 * Date: 11-3-23 下午4:00
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String uid) {
        super(new StringBuffer(uid).append(" not found.").toString());
    }
}
