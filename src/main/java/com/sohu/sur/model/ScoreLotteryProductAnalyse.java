package com.sohu.sur.model;

import java.util.Date;

import javax.persistence.Transient;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

/**
 * 商品 兑换 抽奖统计实体
 * 
 * @author xuewuhao
 * 
 */
@Entity("score_lottery_product_analyse")
public class ScoreLotteryProductAnalyse implements Comparable {

	@Id
	private ObjectId id;
	// 统计日期
	@Indexed
	private Date cdate;
	//1兑换 2抽奖
	private String type;
	
	// 商品ID
	@Indexed
	private String productId;
	// 商品名称
	private String productName;
	// 抽奖次数
	private int lotteryNum;
	// 中奖单数
	private int lotterySuccessNum;
	// 商品单价
	private double productCoin;
	// 回收金币总数
	private double productTotalCoin;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getLotteryNum() {
		return lotteryNum;
	}

	public void setLotteryNum(int lotteryNum) {
		this.lotteryNum = lotteryNum;
	}

	public int getLotterySuccessNum() {
		return lotterySuccessNum;
	}

	public void setLotterySuccessNum(int lotterySuccessNum) {
		this.lotterySuccessNum = lotterySuccessNum;
	}

	public double getProductCoin() {
		return productCoin;
	}

	public void setProductCoin(double productCoin) {
		this.productCoin = productCoin;
	}

	public double getProductTotalCoin() {
		return productTotalCoin;
	}

	public void setProductTotalCoin(double productTotalCoin) {
		this.productTotalCoin = productTotalCoin;
	}

	@Transient
	private String orderParam;

	public String getOrderParam() {
		return orderParam;
	}

	public void setOrderParam(String orderParam) {
		this.orderParam = orderParam;
	}

	public int compareTo(Object arg0) {
		int result = 0;
		ScoreLotteryProductAnalyse spa = (ScoreLotteryProductAnalyse) arg0;
		// 设定默认排序
		if (spa.getOrderParam() == null)
			spa.setOrderParam("-lotteryNum");

		if (spa.getOrderParam().equals("-lotteryNum")) {
			if (spa.lotteryNum < this.lotteryNum)
				result = -1;
			else if (spa.lotteryNum == this.lotteryNum)
				result = 0;
			else
				result = 1;
		} else if (spa.getOrderParam().equals("-lotterySuccessNum")) {

			if (spa.lotterySuccessNum < this.lotterySuccessNum)
				result = -1;
			else if (spa.lotterySuccessNum == this.lotterySuccessNum)
				result = 0;
			else
				result = 1;

		} else if (spa.getOrderParam().equals("-productTotalCoin")) {

			if (spa.productTotalCoin < this.productTotalCoin)
				result = -1;
			else if (spa.productTotalCoin == this.productTotalCoin)
				result = 0;
			else
				result = 1;

		}
		else {
			//时间默认
			if (spa.getCdate().before( this.cdate ))
				result = -1;
			else if (spa.getCdate().equals(this.cdate))
				result = 0;
			else
				result = 1;

		}
		return result;
	}

}
