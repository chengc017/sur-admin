package com.sohu.sur.model;

import com.google.code.morphia.annotations.Property;

import java.io.Serializable;
import java.util.Date;

/**
 * User: guoyong
 * Date: 11-4-7 下午4:40
 */
public class DailyEarnedBonus  implements Serializable{

    @Property("value")
    private int currentValue;

    @Property("last_update")
    private Date updateTime;


    //haoxw 增加blog浏览行为数
    private int viewNum=0;
    
    public int getViewNum() {
		return viewNum;
	}

	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}

	public DailyEarnedBonus() {
        this(0);
    }

    public DailyEarnedBonus(int currentValue) {
        this.currentValue = currentValue;
        updateTime = new Date();
    }

    public DailyEarnedBonus(int currentValue, Date updateTime) {
        this.currentValue = currentValue;
        this.updateTime = updateTime;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void incCurrentValue(int changedExp) {
        currentValue += changedExp;
        updateTime = new Date();
    }

    public void reset() {
        currentValue = 0;
        updateTime = new Date();
    }
}
