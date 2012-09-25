package com.sohu.sur.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * 搜狐线上产品或频道
 *
 * User: 郭勇
 * Date: 2011-2-25 9:30:43
 *
 */
@Entity("products")
public class Product extends ManagedEntity<ObjectId>{

    @Id
    private ObjectId id;

    private String name;

    private String description;

    @Indexed(unique=true)
    private String code;

    @Indexed
    @Property("ena")
    private boolean enabled;

    @Property("vcode")
    private String validationCode;

    @Property("create_time")
    private Date createTime;

    @Indexed
    @Property("s_order")
    private int showOrder;

    public Product() {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }
}
