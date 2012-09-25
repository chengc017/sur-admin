package com.sohu.sur.model;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

/**
 * 权限功能实体
 * 
 * @author xuewuhao
 * 
 */
@Entity("score_func")
public class ScoreFunc extends ManagedEntity<ObjectId> {

	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String name;
	private String url;

	private String desc;

	private Date cdate;
	private ObjectId pid;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public ObjectId getPid() {
		return pid;
	}

	public void setPid(ObjectId pid) {
		this.pid = pid;
	}

}
