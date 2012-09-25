package com.sohu.sur.model;

import com.google.code.morphia.annotations.*;
import com.google.code.morphia.utils.IndexDirection;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 用户在某个产品/频道的活跃程度
 * contActiveDays - 连续活跃天数
 * latestActiveDate - 最后一次活跃日期
 * 
 * User: 郭勇
 * Date: 2011-2-28 18:04:17
 */
@Entity("activities")
public class AccountActivity  implements Serializable {

    @Id
    private ObjectId id;

    @Indexed
    private String uid;

    @Indexed
    @Property("user_id")
    private ObjectId userId;

    @Property("in_product")
    @Indexed
    private ObjectId productId;

    @Property("p_code")
    @Indexed
    private String productCode;

    /**
     *  连续登录天数
     */
    @Property("cont_active_days")
    @Indexed(IndexDirection.DESC)
    private int contActiveDays;

    /**
     * 最后一次活跃日期
     */
    @Property("latest_active_date")
    private Date latestActiveDate;

    @Version("ver")
    Long version;

    public AccountActivity() {
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

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getContActiveDays() {
        return contActiveDays;
    }

    public void setContActiveDays(int contActiveDays) {
        this.contActiveDays = contActiveDays;
    }

    public Date getLatestActiveDate() {
        return latestActiveDate;
    }

    public void setLatestActiveDate(Date latestActiveDate) {
        this.latestActiveDate = latestActiveDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public static AccountActivity create(Account acct, Product product) {

        AccountActivity ret = new AccountActivity();
        
        ret.setUserId(acct.getId());
        ret.setUid(acct.getUid());
        ret.setProductId(product.getId());

        // 增加productCode字段 2011-04-25
        ret.setProductCode(product.getCode());
        ret.setContActiveDays(0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        ret.setLatestActiveDate(yesterday.getTime());

        return ret;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userId", userId)
                .append("uid", uid)
                .append("productId", productId)
                .append("productCode", productCode)
                .append("contActiveDays", contActiveDays)
                .append("latestActiveDate", latestActiveDate)
                .append("version", version)
                .toString();
    }
}
