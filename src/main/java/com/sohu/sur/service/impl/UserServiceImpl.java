package com.sohu.sur.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.ehcache.annotations.Cacheable;
import com.sohu.blog.profile.client.ProfileViewHelper;
import com.sohu.blog.profile.core.model.ProfileView;
import com.sohu.sur.model.User;
import com.sohu.sur.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	@Cacheable(cacheName="users")
	public User getUser(String passport) {
		User user = null;
		try {
			ProfileView profileView = ProfileViewHelper
					.getProfileByPassport(passport);
			if (profileView != null) {
				user = new User(profileView);
			}else {
                logger.warn("Get user profile null, passport:{}.",passport);
            }
		} catch (Exception e) {
			logger.error("ProfileViewHelper.getProfileByPassport Exception:{}",
					e);
		}
		return user;
	}

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);
}
