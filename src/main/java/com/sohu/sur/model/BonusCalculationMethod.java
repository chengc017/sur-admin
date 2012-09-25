package com.sohu.sur.model;

/**
 * 积分的计算方法
 *
 * User: 郭勇
 * Date: 2011-3-9 13:56:14
 */
public enum BonusCalculationMethod {

    /**
     * 不变更
     */
    NOCHANGE(0),

    /**
     * 变更为固定值
     */
    TARGET(1),

    /**
     * 依据预设值变更
     */
    DEFAULT(2),

    /**
     * 依据输入值变更
     */
    INPUT(3);    

    private final int value;

	BonusCalculationMethod(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

    public static BonusCalculationMethod valueOf(int value) {
        for (BonusCalculationMethod type : BonusCalculationMethod.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return NOCHANGE;
    }
}
