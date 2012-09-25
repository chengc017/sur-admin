package com.sohu.sur.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 某积分变更动作的每日积分累计
 * User: 郭勇
 * Date: 2011-3-9 15:42:19
 */
@Embedded
public class ActionExp  implements Serializable{

    @Indexed
    @Property("code")
    private String actionCode;

    @Property("value")
    private int currentValue = 0;

    @Property("last_update")
    private Date updateTime;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static ActionExp create(String actionCode) {
        ActionExp ret = new ActionExp();
        ret.setActionCode(actionCode);
        ret.setCurrentValue(0);
        ret.setUpdateTime(new Date());
        return ret;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(actionCode)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ActionExp == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ActionExp rhs = (ActionExp) obj;
        return new EqualsBuilder()
                .append(actionCode, rhs.actionCode)
                .isEquals();
    }

    public void incCurrentValue(int changedExp) {
        setCurrentValue(currentValue + changedExp);
        this.setUpdateTime(new Date());
    }
}
