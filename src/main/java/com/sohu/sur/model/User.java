package com.sohu.sur.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.annotations.Embedded;
import com.sohu.blog.profile.core.model.ProfileView;
import com.sohu.suc.platform.utils.PassportUtils;

/**
 * @author 韩孝冰
 * 
 */
@Embedded
public class User extends ProfileView implements  Serializable {


	public User() {
	}

	public User(ProfileView profileView) {
		if (profileView != null) {
			super.setCity(profileView.getCity());
			super.setDesc(profileView.getDesc());
			super.setIcon(profileView.getIcon());// 个人头像
			super.setImage(profileView.getImage());// 大头像
			super.setNick(profileView.getNick());
			super.setPassport(profileView.getPassport());
			super.setProvince(profileView.getProvince());
			super.setSex(profileView.getSex());
			super.setStatus(profileView.getStatus());
			super.setUid(profileView.getUid());
			super.setDomain(profileView.getDomain());
			setUrl(profileView);

		}
	}
	
	/**
	 * 设置用户链接积分概览的Url
	 * @param profileView
	 */
	public void setUrl(ProfileView profileView) {
		String url = null;
		if (StringUtils.isNotEmpty(profileView.getDomain())) {
			url = getUrl(profileView.getDomain());
		} else {
			url = getStandbyUrl(profileView.getPassport());
		}
		super.setUrl(url);// 个人url
	}
	
	@Override
	public String getUrl(){
		if(StringUtils.isEmpty(super.getUrl())){
			if (StringUtils.isNotEmpty(super.getDomain())) {
				setUrl(getUrl(super.getDomain()));
			} else {
				setUrl(getStandbyUrl(super.getPassport()));
			}
		}
		return super.getUrl();
	}
	
	/**
	 * 获得个性域名方式的Url
	 * @param domain
	 * @return
	 */
	private String getUrl(String domain){
		StringBuffer sb = new StringBuffer();
		sb.append("http://").append(domain).append(".i.sohu.com/app/score/");
		return sb.toString();
	}
	
	/**
	 * 获得备选方式的Url
	 * @param passport
	 * @return
	 */
	private String getStandbyUrl(String passport){
		StringBuffer sb = new StringBuffer();
		sb.append("http://i.sohu.com/p/").append(PassportUtils.powerfulEncrypt(passport)).append("/app/score/");
		return sb.toString();
	}
}
