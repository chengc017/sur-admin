package com.sohu.sur.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * 用户经验值/积分变更动作
 *
 * User: 郭勇
 * Date: 2011-2-25 14:32:16
 */
@Entity("actions")
public class Action extends ManagedEntity<ObjectId> {

    @Id
    private ObjectId id;

    private String name;

    @Indexed(unique=true)
    private String code;

    @Indexed
    @Property("desc")
    private String description;

    @Indexed
    @Property("ena")
    private boolean enabled;

    @Property("max_exp")
    private int maxExpPerDay;

    /**
     * 0 - no change
     * 1 - change to targetValue
     * 2 - oldValue + defaultValue
     * 3 - oldValue + inputValue
     */
    @Property("exp_cm")
    private int expCalcMethod;

    @Property("exp_dvalue")
    private int expDefaultValue;

    @Property("exp_tvalue")
    private int expTargetValue;

    /**
     * 0 - no change
     * 1 - change to targetValue
     * 2 - oldValue + defaultValue
     * 3 - oldValue + inputValue
     */
    @Property("bonus_cm")
    private int bonusCalcMethod;

    @Property("bonus_dvalue")
    private int bonusDefaultValue;

    @Property("bonus_tvalue")
    private int bonusTargetValue;

    @Property("bonus_ctype")
    private int bonusChangeType;

    @Property("vcode")
    private String validationCode;

    @Property("ctime")
    private Date createTime;

    @Property("show_type")
    private int showType;

    /**
     * 一次变更积分数上限
     * 默认400
     */
    @Property("bonus_max_value")
    private int bonusMaxValue = 400;
    
    /**
     * 获得本次动作所改变的金币数。 如果是输入值取：inputValue
     */
	public int getChangeValue(int inputValue) {
		BonusCalculationMethod calculationMethod = BonusCalculationMethod
				.valueOf(getBonusCalcMethod());
		switch (calculationMethod) {
		case TARGET:
			return getBonusTargetValue();
		case DEFAULT:
			return getBonusDefaultValue();
		case INPUT:
			return inputValue;
		}
		return 0;//都不符合前面规则，则返回0
	}
    
    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getBonusMaxValue() {
		return bonusMaxValue;
	}

	public void setBonusMaxValue(int bonusMaxValue) {
		this.bonusMaxValue = bonusMaxValue;
	}

	public Action() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxExpPerDay() {
        return maxExpPerDay;
    }

    public void setMaxExpPerDay(int maxExpPerDay) {
        this.maxExpPerDay = maxExpPerDay;
    }

    public int getExpCalcMethod() {
        return expCalcMethod;
    }

    public void setExpCalcMethod(int expCalcMethod) {
        this.expCalcMethod = expCalcMethod;
    }

    public int getExpTargetValue() {
        return expTargetValue;
    }

    public void setExpTargetValue(int expTargetValue) {
        this.expTargetValue = expTargetValue;
    }

    public int getExpDefaultValue() {
        return expDefaultValue;
    }

    public void setExpDefaultValue(int expDefaultValue) {
        this.expDefaultValue = expDefaultValue;
    }

    public int getBonusCalcMethod() {
        return bonusCalcMethod;
    }

    public void setBonusCalcMethod(int bonusCalcMethod) {
        this.bonusCalcMethod = bonusCalcMethod;
    }

    public int getBonusTargetValue() {
        return bonusTargetValue;
    }

    public void setBonusTargetValue(int bonusTargetValue) {
        this.bonusTargetValue = bonusTargetValue;
    }

    public int getBonusDefaultValue() {
        return bonusDefaultValue;
    }

    public void setBonusDefaultValue(int bonusDefaultValue) {
        this.bonusDefaultValue = bonusDefaultValue;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getBonusChangeType() {
        return bonusChangeType;
    }

    public void setBonusChangeType(int bonusChangeType) {
        this.bonusChangeType = bonusChangeType;
    }
}
