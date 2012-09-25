package com.sohu.sur.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;
import com.sohu.sur.model.exception.InsufficientBonusException;

/**
 * User: 郭勇 Date: 2011-3-9 14:33:58
 */
@Entity("accounts")
public class Account implements Serializable {

	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String uid;

	/**
	 * 用户经验值
	 */
	@Indexed(IndexDirection.DESC)
	private int exp = 0;

	@Embedded("daily_exp")
	private List<DailyExp> dailyExps = new ArrayList<DailyExp>();

	@Embedded("action_exp")
	private List<ActionExp> actionExps = new ArrayList<ActionExp>();
	/**
	 * 用户积分
	 */
	@Indexed(IndexDirection.DESC)
	private int bonus = 0;

	@Embedded("bonus_total")
	private List<BonusTotal> bonusTotalList = new ArrayList<BonusTotal>(4);

	@Embedded
	private DailyEarnedBonus bonusEarnedToday;

	@Property("create_time")
	private Date createTime;

	@Property("last_update")
	private Date lastUpdateTime;

	@Indexed
	private boolean banned;

	@Indexed
	@Property("is_space")
	private boolean isSpace;

	public Account() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		if (exp < 0) {
			exp = 0;
		}
		this.exp = exp;
	}

	public List<DailyExp> getDailyExps() {
		return dailyExps;
	}

	public void setDailyExps(List<DailyExp> dailyExps) {
		this.dailyExps = dailyExps;
	}

	public List<ActionExp> getActionExps() {
		return actionExps;
	}

	public void setActionExps(List<ActionExp> actionExps) {
		this.actionExps = actionExps;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		if (bonus < 0) {
			bonus = 0;
		}
		this.bonus = bonus;
	}

	public List<BonusTotal> getBonusTotalList() {
		return bonusTotalList;
	}

	public void setBonusTotalList(List<BonusTotal> bonusTotalList) {
		this.bonusTotalList = bonusTotalList;
	}

	public DailyEarnedBonus getBonusEarnedToday() {
		return bonusEarnedToday;
	}

	public void setBonusEarnedToday(DailyEarnedBonus bonusEarnedToday) {
		this.bonusEarnedToday = bonusEarnedToday;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public boolean isSpace() {
		return isSpace;
	}

	public void setSpace(boolean space) {
		isSpace = space;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public static Account create(String uid, String actionCode) {
		Account newAccount = new Account();
		newAccount.setUid(uid);

		Date now = new Date();
		newAccount.setCreateTime(now);
		newAccount.setLastUpdateTime(now);
		newAccount.setBanned(false);

		newAccount.addTodayExp(DailyExp.create(actionCode));
		newAccount.incActionExp(actionCode, 0);
		newAccount.setBonusEarnedToday(new DailyEarnedBonus());

		return newAccount;
	}

	public static Account create(String uid) {
		Account newAccount = new Account();
		newAccount.setUid(uid);

		Date now = new Date();
		newAccount.setCreateTime(now);
		newAccount.setLastUpdateTime(now);
		newAccount.setBanned(false);

		newAccount.setBonusEarnedToday(new DailyEarnedBonus());

		return newAccount;
	}

	public DailyExp findDailyExpByActionCode(String actionCode) {

		for (DailyExp dailyExp : this.dailyExps) {
			if (dailyExp.getActionCode().equals(actionCode)) {
				return dailyExp;
			}
		}
		DailyExp newDailyExp = DailyExp.create(actionCode);
		addTodayExp(newDailyExp);
		return newDailyExp;
	}

	public void addTodayExp(DailyExp todayExp) {
		if (!this.hasDailyExp(todayExp)) {
			this.dailyExps.add(todayExp);
		}
	}

	public ActionExp loadActionExp(String actionCode) {
		ActionExp actionExp = ActionExp.create(actionCode);
		int index = this.actionExps.indexOf(actionExp);
		if (index < 0) {
			this.actionExps.add(actionExp);
		} else {
			actionExp = this.actionExps.get(index);
		}
		return actionExp;
	}

	public void incActionExp(String actionCode, int value) {
		this.loadActionExp(actionCode).incCurrentValue(value);
	}

	public int getDailyExpIndex(DailyExp todayExp) {
		return this.dailyExps.indexOf(todayExp);
	}

	public boolean hasDailyExp(DailyExp todayExp) {
		return this.dailyExps.contains(todayExp);
	}

	public int incExp(Action action, int inputValue) {
		int changedExp = 0;

		BonusCalculationMethod cType = BonusCalculationMethod.valueOf(action.getExpCalcMethod());
		switch (cType) {
		case NOCHANGE:
			break;
		case TARGET:
			changedExp = action.getExpTargetValue() - exp;
			break;
		case DEFAULT:
			changedExp = action.getExpDefaultValue();
			break;
		case INPUT:
			changedExp = inputValue;
			break;
		}

		setExp(exp + changedExp);
		findDailyExpByActionCode(action.getCode()).incCurrentValue(changedExp);
		incActionExp(action.getCode(), changedExp);

		return changedExp;
	}

	public int incBonus(Action action, int inputValue) throws InsufficientBonusException {
		int changedBonus = 0;

		BonusCalculationMethod calculationMethod = BonusCalculationMethod.valueOf(action.getBonusCalcMethod());
		switch (calculationMethod) {
		case NOCHANGE:
			break;
		case TARGET:
			changedBonus = action.getBonusTargetValue() - bonus;
			break;
		case DEFAULT:
			changedBonus = action.getBonusDefaultValue();
			break;
		case INPUT:
			changedBonus = inputValue;
			break;
		}

		if (calculationMethod.equals(BonusCalculationMethod.INPUT)
				&& action.getBonusChangeType() == BonusChangeType.PAYMENT.getValue() && changedBonus <= 0) {

			// 商城兑换消费
			if (bonus + changedBonus < 0) {
				// 积分不够，不扣分
				throw new InsufficientBonusException("积分不足");
			}

		}

		// 累计每日奖励积分
		if (action.getBonusChangeType() == BonusChangeType.REWARD.ordinal()) {
			bonusEarnedToday.incCurrentValue(changedBonus);
		}

		setBonus(bonus + changedBonus);

		BonusTotal total = findBonusTotal(BonusChangeType.valueOf(action.getBonusChangeType()));

		if (total == null) {
			total = new BonusTotal(action.getBonusChangeType());
			bonusTotalList.add(total);
		}

		total.incTotal(changedBonus);

		return changedBonus;
	}

	public BonusTotal findBonusTotal(BonusChangeType changeType) {

		for (BonusTotal total : bonusTotalList) {
			if (total.getChangeType() == changeType.getValue()) {
				return total;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(uid).append(exp).append(bonus).append(createTime)
				.append(lastUpdateTime).toString();
	}

	@Override
	public int hashCode() {

		return new HashCodeBuilder().append(id).append(uid).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Account == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Account rhs = (Account) obj;
		return new EqualsBuilder().append(id, rhs.id).append(uid, rhs.uid).isEquals();
	}

	public void resetBonusEarnedToday() {
		bonusEarnedToday.reset();
	}
}
