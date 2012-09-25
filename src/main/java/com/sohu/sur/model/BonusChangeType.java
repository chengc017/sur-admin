package com.sohu.sur.model;

/**
 * 积分变更的类型
 * User: 郭勇
 * Date: 2011-3-11 15:36:47
 */
public enum BonusChangeType {

    UNKNOWN(0),

    /**
     * 奖励
     */
    REWARD(1),

    /**
     * 扣除
     */
    PUNISHMENT(2),

    /**
     * 充值
     */
    RECHARGE(3),

    /**
     * 兑换消费
     */
    PAYMENT(4),
    
    /**
     * 返还
     */
    PAYBACK(5);

    private final int value;

	BonusChangeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

    public static BonusChangeType valueOf(int value) {
        for (BonusChangeType type : BonusChangeType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
