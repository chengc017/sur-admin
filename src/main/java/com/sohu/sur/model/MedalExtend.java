package com.sohu.sur.model;

import java.io.Serializable;
import java.util.List;

public class MedalExtend  implements Serializable{
	private Product product;
	private AccountActivity accountActivity;
	private Medal curMedal;
	private Medal nextMedal;
	private ProductMedalStars productMedalStars;
	private List<MallItem> mallItems;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public AccountActivity getAccountActivity() {
		return accountActivity;
	}

	public void setAccountActivity(AccountActivity accountActivity) {
		this.accountActivity = accountActivity;
	}

	public Medal getCurMedal() {
		return curMedal;
	}

	public void setCurMedal(Medal curMedal) {
		this.curMedal = curMedal;
	}

	public Medal getNextMedal() {
		return nextMedal;
	}

	public void setNextMedal(Medal nextMedal) {
		this.nextMedal = nextMedal;
	}

	public ProductMedalStars getProductMedalStars() {
		return productMedalStars;
	}

	public void setProductMedalStars(ProductMedalStars productMedalStars) {
		this.productMedalStars = productMedalStars;
	}

	public List<MallItem> getMallItems() {
		return mallItems;
	}

	public void setMallItems(List<MallItem> mallItems) {
		this.mallItems = mallItems;
	}
	
}
