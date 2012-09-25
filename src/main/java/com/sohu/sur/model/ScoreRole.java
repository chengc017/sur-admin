package com.sohu.sur.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Reference;
/**
 * 权限角色实体
 * @author xuewuhao
 *
 */
@Entity("score_role")
public class ScoreRole extends ManagedEntity<ObjectId> {

	@Id
	private ObjectId id;
	@Indexed(unique=true)
	private String name;
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
	public List<ScoreFunc> getScoreFuncList() {
		return scoreFuncList;
	}
	public void setScoreFuncList(List<ScoreFunc> scoreFuncList) {
		this.scoreFuncList = scoreFuncList;
	}
	private Date cdate;
	@Reference
	private List<ScoreFunc> scoreFuncList;
}
