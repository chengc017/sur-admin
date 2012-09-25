package com.sohu.sur.model;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bson.types.ObjectId;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.utils.IndexDirection;

/**
 * 用户勋章 字典数据
 * <p/>
 * User: 郭勇 Date: 2011-2-25 11:10:59
 */
@Entity("medals")
public class Medal extends ManagedEntity<ObjectId> {

	@Id
	private ObjectId id;

	@Property("for_prod")
	private ObjectId forProduct;

	@Property("prod_name")
	private String productName;

	@Property("prod_code")
	private String productCode;

	@Indexed(unique = true)
	private String code;

	private String name;

	@Property("desc")
	private String description;

	/**
	 * 兑换折扣
	 */
	private double discount;

	/**
	 * 保持该勋章的最低连续活跃天数
	 */
	@Indexed(value = IndexDirection.DESC)
	@Property("min_days")
	private int minActiveDays;

	/**
	 * 获得下一级勋章的连续活跃天数
	 */
	@Indexed(value = IndexDirection.DESC)
	@Property("max_days")
	private int maxActiveDays;

	@Property("create_time")
	private Date createTime;

	public Medal() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getForProduct() {
		return forProduct;
	}

	public void setForProduct(ObjectId forProduct) {
		this.forProduct = forProduct;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getMinActiveDays() {
		return minActiveDays;
	}

	public void setMinActiveDays(int minActiveDays) {
		this.minActiveDays = minActiveDays;
	}

	public int getMaxActiveDays() {
		return maxActiveDays;
	}

	public void setMaxActiveDays(int maxActiveDays) {
		this.maxActiveDays = maxActiveDays;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Medal == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Medal rhs = (Medal) obj;
		return new EqualsBuilder().append(id, rhs.id).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("name", name)
				.append("forProduct", forProduct).toString();
	}

	public static String toJSON(Medal medal) {

		JSONSerializerMap mapping = new JSONSerializerMap();
		Map<String, String> aliasMap = new HashMap<String, String>();
		aliasMap.put("code", "id");
		aliasMap.put("discount", "discount");
		mapping.put(Medal.class, new JavaBeanSerializer(Medal.class, aliasMap));

		JSONSerializer serializer = new JSONSerializer(mapping);

		serializer.write(medal);

		return serializer.toString();
	}
}
