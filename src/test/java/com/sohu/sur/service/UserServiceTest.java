package com.sohu.sur.service;

import org.junit.Before;
import org.junit.Test;

import com.sohu.blog.profile.client.ProfileViewHelper;
import com.sohu.blog.profile.core.model.ProfileView;
import com.sohu.sur.service.impl.UserServiceImpl;

public class UserServiceTest {
	UserService userService = new UserServiceImpl();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetUser() {
		try {
			ProfileView profileView = ProfileViewHelper
			.getProfileByPassport("zhaiyi_73@sohu.com");
			System.out.println(profileView.getNick());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
