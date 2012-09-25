package com.sohu.sur.model;

import java.io.Serializable;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;

/**
 * @author 韩孝冰 积分名人堂
 */
public class ProductMedalStars  implements Serializable{
    @Property("pcode")
	private String productCode;
    @Property("pname")
	private String productName;

	public ProductMedalStars() {
	}

	public ProductMedalStars(String productCode, String productName,
			List<MedalStar> medalStars) {
		this.productCode = productCode;
		this.productName = productName;
		this.medalStars = medalStars;
	}

	@Embedded("stars")
	private List<MedalStar> medalStars;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<MedalStar> getMedalStars() {
		return medalStars;
	}

	public void setMedalStars(List<MedalStar> medalStars) {
		this.medalStars = medalStars;
	}
}
