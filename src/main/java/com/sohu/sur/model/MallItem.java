package com.sohu.sur.model;

import java.io.Serializable;

public class MallItem  implements Serializable {
	private long id;
	private int itemType;
	private String name;
	private String url;
	private int marketValue;
	private int saleValue;
	private int limitPrice;
	private String logo;
	private String medals;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(int marketValue) {
		this.marketValue = marketValue;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getMedals() {
		return medals;
	}

	public void setMedals(String medals) {
		this.medals = medals;
	}
	
	public int getItemType() {
		return itemType;
	}

	protected void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public int getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(int limitPrice) {
		this.limitPrice = limitPrice;
	}

	public void setSaleValue(int saleValue) {
		this.saleValue = saleValue;
	}

	public int getSaleValue() {
		return this.saleValue;
	}

	public int getShowValue() {
		return this.saleValue > 0 ? this.saleValue
				: (this.limitPrice > 0 ? this.limitPrice : this.marketValue);
	}
}
