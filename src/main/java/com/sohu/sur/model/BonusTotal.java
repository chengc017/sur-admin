package com.sohu.sur.model;

import java.io.Serializable;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;

/**
 * User: guoyong
 * Date: 11-3-18 上午11:45
 */
@Embedded
public class BonusTotal  implements Serializable{

    @Indexed
    @Property("type")
    private int changeType = 0;

    private int total = 0;

    public BonusTotal() {
    }

    public BonusTotal(int changeType) {
        this.changeType = changeType;
    }

    public BonusTotal(int changeType, int total) {
        this.changeType = changeType;
        this.total = total;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getChangeType() {
        return changeType;
    }

    public int getTotal() {
        return total;
    }

    public void incTotal(int value) {
        this.total += value;
    }
}
