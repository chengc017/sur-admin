package com.sohu.sur.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;

/**
 * 权限用户实体
 * @author xuewuhao
 *
 */
@Entity("score_user")
public class ScoreUser extends ManagedEntity<ObjectId>{

	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String name;

	private String pwd;
	
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private String desc;

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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<ScoreRole> getScoreRoleList() {
		return scoreRoleList;
	}

	public void setScoreRoleList(List<ScoreRole> scoreRoleList) {
		this.scoreRoleList = scoreRoleList;
	}

	private Date cdate;
	@Indexed
	private int state;
	@Reference
	private List<ScoreRole> scoreRoleList;
}
