package com.sohu.sur.model;

import java.io.Serializable;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;

/**
 * @author 韩孝冰
 *	勋章名人
 */
public class MedalStar  implements Serializable{
    @Property("no")
	private int number;
	@Embedded
	private User user;

    @Property("acount")
	private int activityCount;

	private int bonus;
    @Property("cmcode")
	private String curMedalCode;
    @Property("cmname")
	private String curMedalName;
    @Property("nmcode")
	private String nextMedalCode;
    @Property("nmname")
	private String nextMedalName;
    @Property("nacount")
	private int nextMedalMinActiveDays;

	@Embedded("earns")
	private List<List<String>> earnedMedals;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getActivityCount() {
		return activityCount;
	}
	public void setActivityCount(int activityCount) {
		this.activityCount = activityCount;
	}
	public String getCurMedalCode() {
		return curMedalCode;
	}
	public void setCurMedalCode(String curMedalCode) {
		this.curMedalCode = curMedalCode;
	}
	public String getCurMedalName() {
		return curMedalName;
	}
	public void setCurMedalName(String curMedalName) {
		this.curMedalName = curMedalName;
	}
	public String getNextMedalCode() {
		return nextMedalCode;
	}
	public void setNextMedalCode(String nextMedalCode) {
		this.nextMedalCode = nextMedalCode;
	}
	public String getNextMedalName() {
		return nextMedalName;
	}
	public void setNextMedalName(String nextMedalName) {
		this.nextMedalName = nextMedalName;
	}
	public int getNextMedalMinActiveDays() {
		return nextMedalMinActiveDays;
	}
	public void setNextMedalMinActiveDays(int nextMedalMinActiveDays) {
		this.nextMedalMinActiveDays = nextMedalMinActiveDays;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public List<List<String>> getEarnedMedals() {
		return earnedMedals;
	}
	public void setEarnedMedals(List<List<String>> earnedMedals) {
		this.earnedMedals = earnedMedals;
	}
	
}
