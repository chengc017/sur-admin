package com.sohu.sur.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.xml.XMLSerializer;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.googlecode.ehcache.annotations.Cacheable;
import com.sohu.sur.model.MallItem;
import com.sohu.sur.model.MallItemsGiftResponse;
import com.sohu.sur.model.MallItemsLotteryGiftResponse;
import com.sohu.sur.service.MallItemService;

public class MallItemServiceImpl implements MallItemService {

	@Override
	public List<MallItem> getMallItemByMedalCode(String medalCode) {
		return getAllMallItem();
	}

	@Override
	@Cacheable(cacheName="mallItems")
	public List<MallItem> getAllMallItem() {
		List<MallItem> mallItems = new LinkedList<MallItem>();
		BasicResponseHandler responseHandler = new BasicResponseHandler();
		HttpGet httpGet = new HttpGet(lotteryGiftRemoteUrl);
		try {
			String response = httpClient.execute(httpGet, responseHandler);
			
			if (logger.isDebugEnabled())
			logger.debug("httpGet {} response:{}", lotteryGiftRemoteUrl,
					response);
			MallItemsLotteryGiftResponse mallItemsResponse = JSON.parseObject(
					xmlToJsonStr(response), MallItemsLotteryGiftResponse.class);
			mallItems.addAll(mallItemsResponse.getLotteryGift());
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException:{}", e);
		} catch (IOException e) {
			logger.error("IOException:{}", e);
		} catch (Exception e) {
			logger.error("Exception:{}", e);
		}

		httpGet = new HttpGet(giftRemoteUrl);
		try {
			String response = httpClient.execute(httpGet, responseHandler);
			if (logger.isDebugEnabled())
			logger.debug("httpGet {} response:{}", giftRemoteUrl, response);
			MallItemsGiftResponse mallItemsResponse = JSON.parseObject(
					xmlToJsonStr(response), MallItemsGiftResponse.class);
			mallItems.addAll(mallItemsResponse.getGift());
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException:{}", e);
		} catch (IOException e) {
			logger.error("IOException:{}", e);
		} catch (Exception e) {
			logger.error("Exception:{}", e);
		}
		Collections.sort(mallItems, new Comparator<MallItem>() {// 按商品创建的倒序排列
			@Override
			public int compare(MallItem o1, MallItem o2) {
				return (int)(o2.getId() - o1.getId());
			}
		});
		return mallItems;
	}
	
	/**
	 * 将XML转化成JSON字符串
	 * @param xmlStr
	 * @return
	 */
	private String xmlToJsonStr(String xmlStr){
		XMLSerializer xmlSerializer = new XMLSerializer();
		net.sf.json.JSON json= xmlSerializer.read(xmlStr);
		return json.toString();
	}

	public MallItemServiceImpl() {
	}

	public MallItemServiceImpl(HttpClient httpClient, String giftRemoteUrl,
			String lotteryGiftRemoteUrl) {
		this.httpClient = httpClient;
		this.giftRemoteUrl = giftRemoteUrl;
		this.lotteryGiftRemoteUrl = lotteryGiftRemoteUrl;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(MallItemServiceImpl.class);
	private HttpClient httpClient;
	private String giftRemoteUrl;
	private String lotteryGiftRemoteUrl;

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getGiftRemoteUrl() {
		return giftRemoteUrl;
	}

	public void setGiftRemoteUrl(String giftRemoteUrl) {
		this.giftRemoteUrl = giftRemoteUrl;
	}

	public String getLotteryGiftRemoteUrl() {
		return lotteryGiftRemoteUrl;
	}

	public void setLotteryGiftRemoteUrl(String lotteryGiftRemoteUrl) {
		this.lotteryGiftRemoteUrl = lotteryGiftRemoteUrl;
	}

}
