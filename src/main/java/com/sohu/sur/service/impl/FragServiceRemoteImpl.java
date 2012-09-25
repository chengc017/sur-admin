package com.sohu.sur.service.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ehcache.annotations.Cacheable;
import com.sohu.sur.service.FragService;

public class FragServiceRemoteImpl implements FragService {

	@Override
	public String getFragByKey(String key) {
		BasicResponseHandler responseHandler = new BasicResponseHandler();
		try {
			HttpGet httpGet = new HttpGet(key);
			String response = httpClient.execute(httpGet, responseHandler);
			if (logger.isDebugEnabled())
			logger.debug("httpGet {} response:{}", key, response);
			return response;
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException:{}", e);
		} catch (IOException e) {
			logger.error("IOException:{}", e);
		} catch (Exception e) {
			logger.error("Exception:{}", e);
		}
		return "";
	}

	@Override
	@Cacheable(cacheName="mallItems")
	public String getMallRecommend() {
		return getFragByKey(this.mallRecommendUrl);
	}

	private static final Logger logger = LoggerFactory
			.getLogger(FragServiceRemoteImpl.class);
	private HttpClient httpClient;
	private String mallRecommendUrl = "http://gift.sohu.com/service/product/jifen-recommend.html";

	public FragServiceRemoteImpl(HttpClient httpClient, String mallRecommendUrl) {
		this.httpClient = httpClient;
		this.mallRecommendUrl = mallRecommendUrl;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getMallRecommendUrl() {
		return mallRecommendUrl;
	}

	public void setMallRecommendUrl(String mallRecommendUrl) {
		this.mallRecommendUrl = mallRecommendUrl;
	}
}
