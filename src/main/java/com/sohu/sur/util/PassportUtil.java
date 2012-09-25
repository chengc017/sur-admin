package com.sohu.sur.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author "韩孝冰"
 * 
 */
public abstract class PassportUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(PassportUtil.class);

	/**
	 * 将空间xpt解码成用户的passport
	 * @param xpt
	 * @return
	 */
	public static final String decodeFromXpt(String xpt) {
		try {
			return new String(Base64.decodeBase64(xpt), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException:{}", e);
			return null;
		}
	}
}
