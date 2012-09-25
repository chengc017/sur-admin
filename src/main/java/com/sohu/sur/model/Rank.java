package com.sohu.sur.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * 用户等级
 * 
 * User: guoyong
 * Date: 2011-2-25 11:35:59
 */
@Entity("ranks")
public class Rank extends ManagedEntity<ObjectId>{

    @Id
    private ObjectId id;

    private String code;
    
    private String name;

    @Property("icon_id")
    private String iconId;

    @Indexed
    @Property("min_exp")
    private int minQualifiedExp;

    @Property("max_exp")
    private int maxQualifiedExp;

    @Property("create_time")
    private Date createTime;

    public Rank() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public int getMinQualifiedExp() {
        return minQualifiedExp;
    }

    public void setMinQualifiedExp(int minQualifiedExp) {
        this.minQualifiedExp = minQualifiedExp;
    }

    public int getMaxQualifiedExp() {
        return maxQualifiedExp;
    }

    public void setMaxQualifiedExp(int maxQualifiedExp) {
        this.maxQualifiedExp = maxQualifiedExp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
