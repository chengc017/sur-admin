package com.sohu.sur.model;

import java.util.Date;
import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
/**
 * 抽奖总统计
 * @author xuewuhao
 *
 */
@Entity("score_lottery_analyse")
public class ScoreLotteryAnalyse extends ManagedEntity<ObjectId> {
	@Id
	private ObjectId id;
	//1兑换 2抽奖
	private String type;
	//总用户数
	private int lotteryUserCount;
	//成功用户数
	private int lotterySuccessCount;
	//总次数
	private int lotteryTotalCount;
	//用户平均动作数
	private double averageUserLotteryCount;
	//消耗金币数
	private int consumeCoinCount;
	//用户平均消耗金币数
	private double averageUserConsumeCoinCount;
	//抽奖 兑换成功比率
	private double lotteryAverage;
	
	//优质用户抽奖参与人数 默认0
	private int lotteryGoodUserCount=0;
	
	
	@Indexed(unique = true)
	private Date cdate;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public int getLotteryUserCount() {
		return lotteryUserCount;
	}

	public void setLotteryUserCount(int lotteryUserCount) {
		this.lotteryUserCount = lotteryUserCount;
	}

	public int getLotterySuccessCount() {
		return lotterySuccessCount;
	}

	public void setLotterySuccessCount(int lotterySuccessCount) {
		this.lotterySuccessCount = lotterySuccessCount;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public int getLotteryTotalCount() {
		return lotteryTotalCount;
	}

	public void setLotteryTotalCount(int lotteryTotalCount) {
		this.lotteryTotalCount = lotteryTotalCount;
	}

	public double getAverageUserLotteryCount() {
		return averageUserLotteryCount;
	}

	public void setAverageUserLotteryCount(double averageUserLotteryCount) {
		this.averageUserLotteryCount = averageUserLotteryCount;
	}

	public int getConsumeCoinCount() {
		return consumeCoinCount;
	}

	public void setConsumeCoinCount(int consumeCoinCount) {
		this.consumeCoinCount = consumeCoinCount;
	}

	public double getAverageUserConsumeCoinCount() {
		return averageUserConsumeCoinCount;
	}

	public void setAverageUserConsumeCoinCount(double averageUserConsumeCoinCount) {
		this.averageUserConsumeCoinCount = averageUserConsumeCoinCount;
	}

	public int getLotteryGoodUserCount() {
		return lotteryGoodUserCount;
	}
	public void setLotteryGoodUserCount(int lotteryGoodUserCount) {
		this.lotteryGoodUserCount = lotteryGoodUserCount;
	}
	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	public double getLotteryAverage() {
		return lotteryAverage;
	}

	public void setLotteryAverage(double lotteryAverage) {
		this.lotteryAverage = lotteryAverage;
	}

}
